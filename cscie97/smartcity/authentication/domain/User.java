package cscie97.smartcity.authentication.domain;

import cscie97.smartcity.authentication.Visitor;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String name;
    private AuthToken authToken;
    private List<Credential> credentials;
    private List<Entitlement> entitlements;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.authToken = null;
        this.credentials = new ArrayList<>();
        this.entitlements = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public List<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
    }

    public List<Entitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<Entitlement> entitlements) {
        this.entitlements = entitlements;
    }

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

    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}
