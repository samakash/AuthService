package cscie97.smartcity.authentication.domain;

import java.util.List;

/**
 * class represents a permission to be assigned for a user
 */
public class Permission extends Entitlement{

    /**
     * permssion constructor
     * @param id
     * @param name
     * @param description
     */
    public Permission(String id, String name, String description) {
        super(id, name, description);
    }

    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "Permission{ "+ super.toString()+" }";
    }

    /**
     * this method has no function. required for compiling only.
     * @return
     */
    @Override
    public List<Resource> getResources() {
        return null;
    }

    /**
     * this method has no function. required for compiling only.
     * @return
     */
    @Override
    public List<Entitlement> getEntitlementsList() {
        return null;
    }
}
