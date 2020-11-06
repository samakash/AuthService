package cscie97.smartcity.ledger;

public class LedgerException extends Exception {

    private String action;
    private String reason;

    /**
     * Ledger exception constructor
     * @param action
     * @param reason
     */
    public LedgerException(String action, String reason) {
        super();
        this.action = action;
        this.reason = reason;
    }

    /**
     * Overridden toString method to return the content of the exception as formatted string
     * @return
     */
    @Override
    public String toString() {
        return "LedgerException{" +
                "action='" + action + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

}
