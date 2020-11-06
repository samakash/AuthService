package cscie97.smartcity.ledger;


import java.util.*;



public class Block {

    private int blockNumber;
    private String previousHash;
    private String hash;
    private List<Transaction> transactionList;
    private Map<Account, Integer> accountBalanceMap;
    private Block previousBlock;

    /**
     * Constructor of Block object
     * @param blockNumber the number of block as integer
     * @param previousHash the hash of the previous linked block as string
     * @param hash the hash of this block that will get calculated before chaining the block
     * @param previousBlock The previous block
     */
    public Block(int blockNumber, String previousHash, String hash, Block previousBlock) {
        this.blockNumber = blockNumber;
        this.previousHash = previousHash;
        this.hash = hash;
        this.previousBlock = previousBlock;
        this.transactionList = new ArrayList<>();
        this.accountBalanceMap = new LinkedHashMap<>(); //to keep the order of the accounts
    }

    /**
     * Block number getter
     * @return block number
     */
    public int getBlockNumber() {
        return blockNumber;
    }

    /**
     * Block number setter
     * @param blockNumber
     */
    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    /**
     * Block previous hash getter
     * @return hash of previous block
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     * previous hash setter
     * @param previousHash string for the setter
     */
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    /**
     * block hash getter
     * @return string hash of the block
     */
    public String getHash() {
        return hash;
    }

    /**
     * block hash setter
     * @param hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * block transaction list getter
     * @return list of transactions
     */
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    /**
     * Accounts balance map getter
     * @return returns a map of accounts and balances
     */
    public Map<Account,Integer> getAccountBalanceMap() {
        return accountBalanceMap;
    }

    /**
     * Account balance map setter
     * @param accountBalanceMap
     */
    public void setAccountBalanceMap(Map<Account,Integer> accountBalanceMap) {
        this.accountBalanceMap = accountBalanceMap;
    }

    /**
     * Block previous block getter
     * @return the actual previous block object
     */
    public Block getPreviousBlock() {
        return previousBlock;
    }


    /**
     * Method to return the contents of a block formatted
     * @return string of block contents
     */
    public String asString() {
        return "Block{" +
                "blockNumber=" + blockNumber +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                ", transactionList=" + transactionList +
                ", accountBalanceMap=" + accountBalanceMap +
                '}';
    }
}
