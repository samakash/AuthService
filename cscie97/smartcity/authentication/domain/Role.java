package cscie97.smartcity.authentication.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Role class is used to combine a set of permissions based on the Composite pattern.
 */
public class Role extends Entitlement {

    private List<Entitlement> entitlementsList;

    /**
     * default constructor used in inherited classes
     */
    public Role(){}

    /**
     * constructor for Role
     * @param id
     * @param name
     * @param description
     */
    public Role(String id, String name, String description) {
        super(id, name, description);
        this.entitlementsList = new ArrayList<>();
    }

    /**
     * getter for linked entitlements
     * @return
     */
    public List<Entitlement> getEntitlementsList() {
        return entitlementsList;
    }

    /**this method has no function. used in compiling only.
     *
     * @return
     */
    @Override
    public List<Resource> getResources() {
        return null;
    }

    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "Role{" + super.toString()+
                " LinkEntitlementsList=" + entitlementsList +
                "} "
                ;
    }

    /**
     * this method used to extract linked entitlements from a role
     * @param entitlements
     * @return
     */
    @Override
    public List<String> extractComposite(List<Entitlement> entitlements) {
        return super.extractComposite(entitlements);
    }
}
