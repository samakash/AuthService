package cscie97.smartcity.authentication;

import java.util.ArrayList;
import java.util.List;

public class Role extends Entitlement {

    private List<Entitlement> entitlementsList;

    public Role(String id, String name, String description) {
        super(id, name, description);
        this.entitlementsList = new ArrayList<>();
    }

    public List<Entitlement> getEntitlementsList() {
        return entitlementsList;
    }

    public void setEntitlementsList(List<Entitlement> entitlementsList) {
        this.entitlementsList = entitlementsList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "entitlementsList=" + entitlementsList +
                "} " + super.toString();
    }

}
