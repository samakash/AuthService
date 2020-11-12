package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.User;

public interface Visitor {

    void visit(User user);
    void visit(Entitlement entitlement);
}
