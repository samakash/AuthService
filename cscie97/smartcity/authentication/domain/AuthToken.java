package cscie97.smartcity.authentication.domain;

import cscie97.smartcity.authentication.Visitor;

/**
 * This class represents the authentication token and its properties
 */
public class AuthToken {
    private String id;
    private String authValue;
    private String expirationTime;
    private TokenState state;
    private User user;

    /**
     * constructor for the AuthToken
     * @param id authToken id
     * @param authValue the actual value of authToken
     * @param expirationTime
     * @param state
     */
    public AuthToken(String id, String authValue, String expirationTime, TokenState state) {
        this.id = id;
        this.authValue = authValue;
        this.expirationTime = expirationTime;
        this.state = state;
        this.user = null;
    }

    /**
     * getter for authToken id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * setter for the authToken id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter for the authToken value
     * @return
     */
    public String getAuthValue() {
        return authValue;
    }

    /**
     * setter for the authToken value
     * @param authValue
     */
    public void setAuthValue(String authValue) {
        this.authValue = authValue;
    }

    /**
     * getter for expiration time
     * @return
     */
    public String getExpirationTime() {
        return expirationTime;
    }

    /**
     * setter for expiration time
     * @param expirationTime
     */
    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * getter for the authToken state
     * @return
     */
    public TokenState getState() {
        return state;
    }

    /**
     * setter for the authToken state
     * @param state
     */
    public void setState(TokenState state) {
        this.state = state;
    }

    /**
     * getter for the user associated with the authToken
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * setter for the user of this authToken
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "AuthToken{" +
                "id='" + id + '\'' +
                ", authValue='" + authValue + '\'' +
                ", expirationTime='" + expirationTime + '\'' +
                ", state=" + state +
                '}';
    }

    /**
     * Visitor pattern accept method
     * @param visitor
     */
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
