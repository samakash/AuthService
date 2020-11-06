package cscie97.smartcity.ledger;


public class Account {

    private String address;
    private int balance;


    /**
     * Constructor for Account. balance will always start from 0
     * @param address : takes address as String
     */
    public Account(String address) {
        this.address = address;
        this.balance = 0;
    }

    /**
     * Account address Getter
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Account address setter
     * @param address: address as String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Account balance getter
     * @return Account balance as an Int
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Account balance setter
     * @param balance Accepts an integer for the balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Overridden method to return Account object as formatted string
     * @return string of the Account object contents
     */
    @Override
    public String toString() {
        return "Account{" +
                "address='" + address + "'}";
    }

}
