package cscie97.smartcity.authentication;

public interface Visitor {

    void visit(User user);
    void visit(Entitlement entitlement);
}
