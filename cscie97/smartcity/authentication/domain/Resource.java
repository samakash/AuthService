package cscie97.smartcity.authentication.domain;

import cscie97.smartcity.authentication.Visitor;

/**
 * resource represent a physical entity like device or a city. can be linked to a ResourceRole class
 */
public class Resource {

    private String id;
    private String description;

    /**
     * constructor for resource
     * @param id
     * @param description
     */
    public Resource(String id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * getter for resource id
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
        return "Resource{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * visitor patter accept method
     * @param visitor
     */
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}
