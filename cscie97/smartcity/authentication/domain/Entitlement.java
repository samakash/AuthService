package cscie97.smartcity.authentication.domain;

import cscie97.smartcity.authentication.Visitor;

import java.util.ArrayList;
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

    public List<String> extractComposite(List<Entitlement> entitlements) {
        List<String> extracted = new ArrayList<>();
        StringBuilder compositeBuilder = new StringBuilder();
        compositeBuilder.append("   ");
        for (Object obj : entitlements) {
            // Recover the type of this object
            if (obj instanceof Role) {
                extracted.add(((Role) obj).getId());
                Entitlement role = (Role) obj;
                for(Entitlement e: role.getEntitlementsList()){
                    extracted.add(e.getId());
                }
                role.extractComposite((role.getEntitlementsList()));
            } else if (obj instanceof ResourceRole) {
                extracted.add(((ResourceRole) obj).getId());
                Entitlement role = (ResourceRole) obj;
                for(Entitlement e: role.getEntitlementsList()){
                    extracted.add(e.getId());
                }
                ((ResourceRole)obj).extractComposite(((ResourceRole) obj).getEntitlementsList());
            }else {
                extracted.add(((Permission) obj).getId());
            }
        }
        return extracted;
    }


}
