package cscie97.smartcity.authentication;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String name;
    private AuthToken authToken;
    private List<Credential> credentials;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.authToken = null;
        this.credentials = new ArrayList<>();

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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", authToken=" + authToken +
                ", credentials=" + credentials +
                '}';
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}
