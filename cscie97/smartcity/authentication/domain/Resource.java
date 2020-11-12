package cscie97.smartcity.authentication.domain;

public class Resource {

    private String id;
    private String description;

    public Resource(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
