package cscie97.smartcity.authentication.domain;

import java.util.ArrayList;
import java.util.List;

public class ResourceRole extends Role {

    private List<Resource> resources;

    public ResourceRole(String id, String name, String description) {
        super(id, name, description);
        this.resources = new ArrayList<>();
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return " ResourceRole{ " + super.toString() +
                " resources=" + resources +
                "} ";
    }


}
