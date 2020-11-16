package cscie97.smartcity.authentication.domain;

/**
 * Faceprint credential class
 */
public class FacePrint extends Credential{

    private String facePrintValue;

    /**
     * constructor for faceprint crednetial
     * @param id
     * @param facePrintValue
     */
    public FacePrint(String id, String facePrintValue) {
        super(id);
        this.facePrintValue = facePrintValue;
    }

    /**
     * getter for the faceprint vlaue
     * @return
     */
    public String getFacePrintValue() {
        return facePrintValue;
    }

    /**
     * setter for the faceprint value
     * @param facePrintValue
     */
    public void setFacePrintValue(String facePrintValue) {
        this.facePrintValue = facePrintValue;
    }

    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "FacePrint{" +
                "facePrintValue='" + facePrintValue + '\'' +
                "} " + super.toString();
    }
}
