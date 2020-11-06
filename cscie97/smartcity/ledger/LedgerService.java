package cscie97.smartcity.ledger;

public interface LedgerService {

    static LedgerServiceImpl getInstance(){
        return LedgerServiceImpl.getInstance();
    }

    void createLedger(String name, String description, String seed);

    void createAccount(String address);

    int getAccountBalance(String address);

    void processTransaction(String txnId, int amount, int fee, String note, String payerAddress, String receiverAddress);

    void getAccountBalances();

    void getBlock(int blockNumber);

    void getTransaction(String txnId);

    void validateLedger();


}
