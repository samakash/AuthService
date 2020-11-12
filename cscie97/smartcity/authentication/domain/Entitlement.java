package cscie97.smartcity.authentication.domain;

import cscie97.smartcity.authentication.Visitor;

import java.util.List;

public abstract class Entitlement {

    private String id;
    private String name;
    private String description;

    public Entitlement(){}

    public Entitlement(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{ " +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public abstract List<Entitlement> getEntitlementsList();
    public abstract List<Resource> getResources();

    public void accept(Visitor visitor){
        visitor.visit(this);
    }


}
