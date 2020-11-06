package cscie97.smartcity.ledger;

public class LedgerServiceImpl implements LedgerService{

    private Ledger ledger;

    public Ledger getLedger() {
        return ledger;
    }

    private static LedgerServiceImpl instance;

    /**
     * LedgerService instance getter
     */
    public static LedgerServiceImpl getInstance() {
        if (instance == null){
            instance = new LedgerServiceImpl();
        }
        return instance;
    }

    /*
     create-ledger
    create-account
    get-account-balance
    process-transaction
    get-account-balances
    get-block
    get-transaction
    validate
     */

    public void createLedger(String name, String description, String seed){
        ledger = new Ledger(name,description,seed);
    }

    public void createAccount(String address){
        Account account =ledger.createAccount(address);
        System.out.println("New account in LedgerService is created - Address: " + account.getAddress());
    }

    public int getAccountBalance(String address){
        int balance = ledger.getAccountBalance(address);
        System.out.println("Account address "+address +" balance is "+balance);
        return balance;
    }

    public void processTransaction(String txnId, int amount, int fee, String note, String payerAddress, String receiverAddress){
        Account payer = ledger.accountsList.get(payerAddress);
        Account receiver = ledger.accountsList.get(receiverAddress);
        //create a new transaction
        Transaction transaction = ledger.createTransaction(txnId, amount, fee, note, payer, receiver);
        //submit transaction for processing
        ledger.processTransaction(transaction);
    }

    public void getAccountBalances(){
        System.out.println(ledger.getAccountBalances());
    }

    public void getBlock(int blockNumber){
        System.out.println("Getting Block # ");
        if (! ledger.getBlock(blockNumber).asString().equals(null) ){
            System.out.println(ledger.getBlock(blockNumber).asString());
        }
    }

    public void getTransaction(String txnId){
        if(! ledger.getTransaction(txnId).equals(null)){
            System.out.println(ledger.getTransaction(txnId));
        }
    }

    public void validateLedger(){
        ledger.validate();
    }


}
