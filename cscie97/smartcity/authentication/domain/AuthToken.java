package cscie97.smartcity.authentication.domain;

import cscie97.smartcity.authentication.Visitor;

public class AuthToken {
    private String id;
    private String authValue;
    private String expirationTime;
    private boolean sufficientPermission;
    private TokenState state;
    private User user;

    public AuthToken(String id, String authValue, String expirationTime, TokenState state) {
        this.id = id;
        this.authValue = authValue;
        this.expirationTime = expirationTime;
        this.sufficientPermission = false;
        this.state = state;
        this.user = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthValue() {
        return authValue;
    }

    public void setAuthValue(String authValue) {
        this.authValue = authValue;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isSufficientPermission() {
        return sufficientPermission;
    }

    public void setSufficientPermission(boolean sufficientPermission) {
        this.sufficientPermission = sufficientPermission;
    }

    public TokenState getState() {
        return state;
    }

    public void setState(TokenState state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "id='" + id + '\'' +
                ", authValue='" + authValue + '\'' +
                ", expirationTime='" + expirationTime + '\'' +
                ", state=" + state +
                '}';
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
