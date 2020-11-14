package cscie97.smartcity.authentication.domain;

import java.util.ArrayList;
import java.util.List;

public class Permission extends Entitlement{

    public Permission(String id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public String toString() {
        return "Permission{ "+ super.toString()+" }";
    }

    @Override
    public List<Resource> getResources() {
        return null;
    }

    @Override
    public List<Entitlement> getEntitlementsList() {
        return null;
    }
}
