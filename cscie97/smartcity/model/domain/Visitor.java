package cscie97.smartcity.model.domain;

public class Visitor extends Person {
    /**
     * contructor for Visitor
     * @param id
     * @param biometricId
     * @param location
     */
    public Visitor(String id, String biometricId, Location location) {
        super(id, biometricId,location);
    }

    /**
     * toString method to display object contents
     * @return
     */
    @Override
    public String toString() {
        return "Visitor{} " + super.toString();
    }
}
