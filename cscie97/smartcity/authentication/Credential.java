package cscie97.smartcity.authentication;

public abstract class Credential {

    private String id;

    public Credential(String id) {
        this.id = id;
    }

    public Credential() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id='" + id + '\'' +
                '}';
    }
}
