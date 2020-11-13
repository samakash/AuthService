package cscie97.smartcity.authentication.domain;

import java.util.ArrayList;
import java.util.List;

public class Role extends Entitlement {

    private List<Entitlement> entitlementsList;

    public Role(){}

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
    public List<Resource> getResources() {
        return null;
    }

    @Override
    public String toString() {
        return "Role{" + super.toString()+
                " LinkEntitlementsList=" + entitlementsList +
                "} "
                ;
    }

    @Override
    public List<String> extractComposite(List<Entitlement> entitlements) {
        return super.extractComposite(entitlements);
    }
}
