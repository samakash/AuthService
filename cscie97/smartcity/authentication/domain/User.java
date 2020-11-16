package cscie97.smartcity.authentication.domain;

import cscie97.smartcity.authentication.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * user class represents the actual user of the authentication service
 */
public class User {

    private String id;
    private String name;
    private AuthToken authToken;
    private List<Credential> credentials;
    private List<Entitlement> entitlements;

    /**
     * constructor for user
     * @param id
     * @param name
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.authToken = null;
        this.credentials = new ArrayList<>();
        this.entitlements = new ArrayList<>();

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
     * getter fro name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * seteer for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter fro AuthToken
     * @return
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

    /**
     * setter fro AuthToken
     * @param authToken
     */
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    /**
     * getter for user credentials
     * @return
     */
    public List<Credential> getCredentials() {
        return credentials;
    }

    /**
     * getter for assigned roles and permission for this user
     * @return
     */
    public List<Entitlement> getEntitlements() {
        return entitlements;
    }


    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", authToken=" + authToken +
                ", credentials=" + credentials +
                ", entitlements=" + entitlements +
                '}';
    }

    /**
     * design patter accept method
     * @param visitor
     */
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}
