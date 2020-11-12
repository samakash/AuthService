package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.Visitor;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.User;

public class InventoryAdd implements Visitor {

    @Override
    public void visit(User user) {
        System.out.println("im add user");
    }

    @Override
    public void visit(Entitlement entitlement) {
        System.out.println("im add entitlement");
    }
}
