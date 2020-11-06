package cscie97.smartcity.ledger;

public class Transaction {

    private String transactionId;
    private int amount;
    private int fee;
    private String note;
    private Account receiver;
    private Account payer;

    /**
     * Constructor for transaction. used to create transactions for processing
     * @param id
     * @param amount
     * @param fee
     * @param note
     * @param payer
     * @param receiver
     */
    public Transaction (String id, int amount,int fee, String note, Account payer, Account receiver) {
        this.transactionId = id;
        this.amount = amount;
        this.note = note;
        this.receiver = receiver;
        this.payer = payer;
        this.fee = fee;
    }

    /**
     * Transaction Id getter
     * @return
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Transaction Id setter
     * @param transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * transaction amount getter
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * transaaction amount setter
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * transaction fee getter
     * @return
     */
    public int getFee() {
        return fee;
    }

    /**
     * transaction fee setter
     * @param fee
     */
    public void setFee(int fee) {
        this.fee = fee;
    }

    /**
     * Transaction notes getter
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * Transaction notes setter
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Transaction receiver getter
     * @return  Receiver Account object
     */
    public Account getReceiver() {
        return receiver;
    }

    /**
     * Transaction receiver setter
     * @param receiver
     */
    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    /**
     * transaction payer getter
     * @return account of payer
     */
    public Account getPayer() {
        return payer;
    }

    /**
     * Transaction payer setter
     * @param payer
     */
    public void setPayer(Account payer) {
        this.payer = payer;
    }

    /**
     * Overridden method to return transaction contents formatted
     * @return
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", fee=" + fee +
                ", note='" + note + '\'' +
                ", receiver=" + receiver +
                ", payer=" + payer +
                '}';
    }
}
