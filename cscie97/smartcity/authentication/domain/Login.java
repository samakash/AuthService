package cscie97.smartcity.authentication.domain;

/**
 * credential class for username & password login
 */
public class Login extends Credential{

    private String username;
    private String password;

    /**
     * constructor for login class
     * @param id
     * @param username
     * @param password
     */
    public Login(String id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
    }

    /**
     * getter for username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter for password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }
}
