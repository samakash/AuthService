package cscie97.smartcity.authentication.domain;

/**
 * abstract class for Credentials
 */
public abstract class Credential {

    private String id;

    /**
     * constructor for a credential. will be used in inherited classes
     * @param id
     */
    public Credential(String id) {
        this.id = id;
    }

    /**
     * abstract default constructor
     */
    public Credential() {
    }

    /**
     * getter for id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * setter for id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "Credential{" +
                "id='" + id + '\'' +
                '}';
    }
}
