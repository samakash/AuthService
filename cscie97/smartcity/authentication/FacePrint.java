package cscie97.smartcity.authentication;

public class FacePrint extends Credential{

    private String facePrintValue;


    public FacePrint(String id, String facePrintValue) {
        super(id);
        this.facePrintValue = facePrintValue;
    }

    public String getFacePrintValue() {
        return facePrintValue;
    }

    public void setFacePrintValue(String facePrintValue) {
        this.facePrintValue = facePrintValue;
    }

    @Override
    public String toString() {
        return "FacePrint{" +
                "facePrintValue='" + facePrintValue + '\'' +
                "} " + super.toString();
    }
}
