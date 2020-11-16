package cscie97.smartcity.authentication.domain;

import cscie97.smartcity.authentication.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * avbstract class to represent roles and permission
 */
public abstract class Entitlement {

    private String id;
    private String name;
    private String description;

    /**
     * abstract default constructor
     */
    public Entitlement(){}

    /**
     * constructor for Entitlement
     * @param id entitlement id
     * @param name entitlement name
     * @param description entitlement description
     */
    public Entitlement(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * getter for id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * setter for id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter for name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * seteer for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "{ " +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * abstract getter method to return associated entitlement list that will be used in Role class
     * @return
     */
    public abstract List<Entitlement> getEntitlementsList();

    /**
     * abstract getter method to return associated resources that will be sued in ResourceRole Class
     * @return
     */
    public abstract List<Resource> getResources();

    /**
     * Visitor pattern accept method
     * @param visitor
     */
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    /**
     * this method is used to extract all roles and permission from the entitlement list. used in check access to simplify the search for permission in a user.
     * @param entitlements
     * @return list of string of all permissions ids found
     */
    public List<String> extractComposite(List<Entitlement> entitlements) {
        List<String> extracted = new ArrayList<>();
//        extracted.add(this.getId());
        for (Object obj : entitlements) {
            // Recover the type of this object
            if (obj instanceof Role) {
                extracted.add(((Role) obj).getId());
                Entitlement role = (Role) obj;
                for(Entitlement e: role.getEntitlementsList()){
                    if(!extracted.contains(e.id)){
                        extracted.add(e.getId());
                    }
                }
                role.extractComposite((role.getEntitlementsList()));
            } else if (obj instanceof ResourceRole) {
                extracted.add(((ResourceRole) obj).getId());
                Entitlement role = (ResourceRole) obj;
                for(Entitlement e: role.getEntitlementsList()){
                    if(!extracted.contains(e.id)){
                        extracted.add(e.getId());
                    }
                }
                ((ResourceRole)obj).extractComposite(((ResourceRole) obj).getEntitlementsList());
            }else {
                if(!extracted.contains(((Permission) obj).getId())){
                    extracted.add(((Permission) obj).getId());
                }
            }
        }
        return extracted;
    }


}
