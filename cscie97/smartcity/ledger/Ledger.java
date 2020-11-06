package cscie97.smartcity.ledger;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Ledger {

    private String name;
    private String description;
    private String seed;
    private Block genesisBlock;
    private Map<Integer,Block> blockMap;
    public Map<String, Account> accountsList = new HashMap<>();

    /**
     * Ledger Constructor.  Acts as an initializer for the ledger
     * @param name
     * @param description
     * @param seed
     */
    public Ledger(String name, String description, String seed) {
        this.name = name;
        this.description = description;
        this.seed = seed;
        blockMap = new LinkedHashMap<>(); // using LinkedHashMap to keep the order of blocks

        //create master account
        Account master = new Account("master");
        master.setBalance(Integer.MAX_VALUE);
        accountsList.put(master.getAddress(),master);

        //create genesis block and add master account to it
        this.genesisBlock = new Block(1,"",null,null);
        this.genesisBlock.getAccountBalanceMap().put(master, master.getBalance());

        //add genesis block to mapblock as the first block
        blockMap.put(genesisBlock.getBlockNumber(), genesisBlock);

        System.out.println("New Ledger is Created.");
    }

    /**
     * Ledger name getter
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Ledger name setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ledger description getter
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ledger description setter
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * ledger seed getter
     * @return
     */
    public String getSeed() {
        return seed;
    }

    /**
     * Genesis block getter
     * @return
     */
    public Block getGenesisBlock() {
        return genesisBlock;
    }

    /**
     * Ledger blocks map getter
     * @return
     */
    public Map<Integer, Block> getBlockMap() {
        return blockMap;
    }


    /**
     * This methos used to create accounts and save them in the accountList
     * @param address
     * @return Account
     */
    public Account createAccount(String address) {
        //search if account already exist in the blockchain. throw an error if found.
        boolean found = false;
        if (accountsList.containsKey(address)) {
            found =true;
        }
        //if account not found then allow creating it.
        try {
            if(!found){
                Account account = new Account(address);
                accountsList.put(address,account);
                return account;
            } else {
                 throw new LedgerException("Create New Account Failed","Account address already exists");
            }
        } catch (LedgerException e){
            System.out.println(e);
            return null; // this null will be used in the CMD interface to throw an exception from there.
        }

    }


    /**
     * This method is used to create a transaction
     * @param id
     * @param amount
     * @param fee
     * @param note
     * @param payer
     * @param receiver
     * @return a transaction object ready for processing
     */
    public Transaction createTransaction(String id, int amount,int fee, String note,Account payer,Account receiver){  //extra
        return new Transaction(id,amount,fee,note,payer,receiver);
    }

    /**
     * This method used to process a transaction. It will take a transaction and validate it then submit it to the active block if passed validation
     * @param transaction
     * @return Transaction Id
     * @note the logic of creating a new block also exists in this method. if active block reached 10 transaction it will create a new block and chain it.
     */
    public String processTransaction (Transaction transaction) {
        //get latest active block
        int index = blockMap.size();
        Block activeBlock = blockMap.get(index);

        //if active block size reached the limit then add Hash to active block and create next block.
        if(activeBlock.getTransactionList().size() == 10 && validateBlockBeforeChaining(activeBlock)){
            activeBlock.setHash(calculateBlockHash(seed, activeBlock.toString()));

            Block newBlock = new Block(index+1, activeBlock.getHash(), null, activeBlock);

            newBlock.setAccountBalanceMap( activeBlock.getAccountBalanceMap());
            blockMap.put(newBlock.getBlockNumber(), newBlock);
            activeBlock = newBlock;
        }

        //add transaction to the block transaction list after passing the transaction validation
            try {
                if (validateTransaction(transaction)){
                    //withdraw units from the payer
                    //payer existence will be checked in balanceMap in the block
                    Account payer = accountsList.get((transaction.getPayer().getAddress()));
                    payer.setBalance(
                            payer.getBalance() - (transaction.getAmount() + transaction.getFee())
                    );

                    //transfer units to receiver
                    transaction.getReceiver().setBalance(
                            transaction.getReceiver().getBalance() + transaction.getAmount()
                    );

                    //transfer fees to master account
                    Account master = accountsList.get("master");
                    master.setBalance(
                            master.getBalance() + transaction.getFee()
                    );

                    //add transaction to the transaction list
                    activeBlock.getTransactionList().add(transaction);

                    //update accounts in accountsBalanceMap
                    activeBlock.getAccountBalanceMap().put(
                            payer, payer.getBalance());
                    activeBlock.getAccountBalanceMap().put(
                            transaction.getReceiver(), transaction.getReceiver().getBalance());
                    activeBlock.getAccountBalanceMap().put(
                            master, master.getBalance());

                    System.out.println("Transaction is accepted - ID: "+transaction.getTransactionId()+ " Payer: "+payer.getAddress()+
                            " Receiver: "+transaction.getReceiver().getAddress()+" Amount: "+transaction.getAmount() +" notes: "+transaction.getNote());
                } else{
                    throw new LedgerException("Process Transaction Failed. ",
                            "Validation Failed: Transaction is not Accepted");
                }

            } catch (LedgerException e){
                System.out.println(e);
            }

        //return transactionId
        return transaction.getTransactionId();
    }

    /**
     * Thid method is used to retrieve account balance for an account using address
     * @param address address of the account
     * @return balance if found or will return -1 and throw an exception if address is not found
     */
    public int getAccountBalance (String address){
        boolean found = false;
        int balance=0;
        try{
            Block activeBlock = blockMap.get(blockMap.size());
            Map<Account,Integer> accountBalanceMap = activeBlock.getAccountBalanceMap();
            for (Map.Entry<Account,Integer> entry : accountBalanceMap.entrySet()){
                if (entry.getKey().getAddress().equals(address)){
                    found = true;
                    balance = entry.getValue();
                }
            }
            if (found){
                return balance;
            } else {
                balance = -1;
                throw new LedgerException("Get Account Balance Failed. ","Account is not found");
            }
        } catch (LedgerException e){
            System.out.println(e);
        }
        return balance;
    }

    /**
     * Method to return a map of all account balances from the ledger accounts map
     * @return
     */
    public Map<Account,Integer> getAccountBalances (){
        int index = blockMap.size();
        Block activeBlock = blockMap.get(index);
        return activeBlock.getAccountBalanceMap();
    }

    /**
     * This method is used to get a block using block number. if block number is not found, method will return null an exception will be thrown.
     * @param blockNumber
     * @return
     */
    public Block getBlock(int blockNumber){
        boolean found = false;
        try{
            for(Block block: getBlockMap().values()){
                if(block.getBlockNumber()==blockNumber) {
                    found=true;
                    break;
                }
            }
            if (found){
                return blockMap.get(blockNumber);
            } else {
                throw new LedgerException("Get block failed","block number doesnt exist");
            }
        } catch (LedgerException ex){
            System.out.println(ex);
        }
        return null;
    }

    /**
     * Method to get transaction by transaction ID. if not found method will return null and throw an exception
     * @param transactionId
     * @return transaction object
     */
    public Transaction getTransaction(String transactionId){
        Transaction transaction = null;
        try {
            for(Map.Entry<Integer,Block> entry: blockMap.entrySet()){
                Block block = entry.getValue();
                for (int i = 0; i < block.getTransactionList().size(); i++) {
                    if(block.getTransactionList().get(i).getTransactionId().equals(transactionId)){
                        transaction = block.getTransactionList().get(i);
                    }
                }
            }
            if(transaction == null){
                throw new LedgerException("Get Transaction By Id Failed.", "Transaction Id is not found in the blockchain");
            }
        } catch (LedgerException e){
            System.out.println(e);
        }
        return transaction;
    }

    /**
     * This method will validate the blockchain by recalculating the hash of each previous block in the ledger
     */
    public void validate(){
        Boolean valid=false;
        try {
            if(blockMap.size()==1){
                System.out.println("Blockchain cannot be validated. There is only one block available.");
            } else {
                for(int i = 2; i<=blockMap.size(); i++){
                    Block block = getBlock(i);
                    if(block.getPreviousHash().equals( calculateBlockHash(seed, block.getPreviousBlock().toString())) ){
                        valid = true;
                    } else{
                        valid = false;
                        break;
                    }
                }
                if(valid){
                    System.out.println("Blockchain is VALID");
                } else {
                    throw new LedgerException("Blockchain validation failed","One of the blocks previous hash is not valid");
                }
            }

        } catch (LedgerException e){
            System.out.println(e);
        }

    }

 //extrazzz down here

    /**
     * method is used to validate a transaction if its ready for processing.
     * This will validate accounts, balances, fee and verify that transaction id is unique. exceptions will be thrown based on validations
     * @param transaction
     * @return
     */
    public boolean validateTransaction(Transaction transaction){
        Account payer;
        boolean validPayer = false;
        boolean validBalance = false;
        boolean validReceiver = false;
        boolean validFee = false;
        boolean validTxnId = true;
        //verify payer exists in the block accountBalance
        try {
            for (Map.Entry<Account, Integer> entry : getAccountBalances().entrySet()) {
                if (entry.getKey().getAddress()==(transaction.getPayer().getAddress())) {
                    validPayer = true;
                }
            }
            //verify payer has sufficient balance (also will verify balance >0 logically)
            validBalance = transaction.getPayer().getBalance() >= (transaction.getAmount()+transaction.getFee());
            //verify receiver exist in AccountsList and balance > 0
            validReceiver = (accountsList.containsKey(transaction.getReceiver().getAddress()) && transaction.getReceiver().getBalance()>=0);
            //verify fee is valid
            validFee = transaction.getFee() >= 10;
            //verify transaction id is unique by searching for a transaction in the blockmap with the same id
            for(Map.Entry<Integer,Block> entry: blockMap.entrySet()){
                Block block = entry.getValue();
                for (int i = 0; i < block.getTransactionList().size(); i++) {
                    if(block.getTransactionList().get(i).getTransactionId().equals(transaction.getTransactionId())){
                        validTxnId = false;
                        break;
                    }
                }
            }
            //throw exceptions for failed parameters
            if (!validPayer){
                throw new LedgerException("Transaction Validation Failed","Payer address doesn't exist in the block-chain accounts");
            }
            if(!validBalance){
                throw new LedgerException("Transaction Validation Failed","Payer Doesn't have sufficient balance.");
            }
            if(!validReceiver){
                throw new LedgerException("Transaction Validation Failed","Receiver address is not found.");
            }
            if(!validFee){
                throw new LedgerException("Transaction Validation Failed","Fee is not valid");
            }
            if(!validTxnId){
                throw new LedgerException("Transaction Validation Failed","Transaction ID already exists in the block-chain.");
            }
        } catch (LedgerException e){
            System.out.println(e);
        }

        if( validPayer && validBalance && validReceiver && validFee && validTxnId){
                return true;
            } else{
                return false;
            }
        }


    /**
     * This method is used to calculate the block SHA-256 hash.
     * @param seed seed for the hashing
     * @param blockString a block converted to string using toString()
     * @return hash string
     */
    private String calculateBlockHash(String seed, String blockString) {
        String dataToHash = blockString;
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(seed.getBytes());
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println((ex.getMessage()));
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    /**
     * This method is used to validate block integrity before chaining. it will double check the total of all balances and the count of transactions in block
     * @param block
     * @return
     */
    private boolean validateBlockBeforeChaining(Block block){
        boolean validBalances = false;
        boolean validTransactioncount = false;
        try{
            //verify block has 10 TXNx
            if(block.getTransactionList().size() == 10){
                validTransactioncount = true;
            }
            //verify balances total
            int total = 0;
            for(Account account: block.getAccountBalanceMap().keySet()){
                total+=account.getBalance();
            }
            if (total == Integer.MAX_VALUE);{
                validBalances = true;
            }

            //throw proper exceptions
            if(!validBalances) throw new LedgerException("Block data integrity issue"," Block total balances is invalid");
            if(!validTransactioncount) throw new LedgerException("Block data integrity issue"," Block transactions count is invalid");

        } catch (LedgerException ex){
            System.out.println(ex);
        }

        if(validBalances && validTransactioncount){
            return true;
        } else {
            return false;
        }

    }


}
