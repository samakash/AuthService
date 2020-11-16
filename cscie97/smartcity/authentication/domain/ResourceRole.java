package cscie97.smartcity.authentication.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Resource Role class extends Role and can be linked to Resources. Used to combine permission for a certain resource(s)
 */
public class ResourceRole extends Role {

    private List<Resource> resources;

    /**
     * constrcutor for Resource Role
     * @param id
     * @param name
     * @param description
     */
    public ResourceRole(String id, String name, String description) {
        super(id, name, description);
        this.resources = new ArrayList<>();
    }

    /**
     * getter of linked resources to this role
     * @return
     */
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * seteeer for resources
     * @param resources
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    /**
     * formatted toString meethod;
     * @return
     */
    @Override
    public String toString() {
        return " ResourceRole{ " + super.toString() +
                " resources=" + resources +
                "} ";
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
