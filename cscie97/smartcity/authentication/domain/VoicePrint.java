package cscie97.smartcity.authentication.domain;

/**
 *  Vocieprint credentials class
 */
public class VoicePrint extends Credential {

    private String voicePrintValue;

    /**
     * constructor for Vocieprint
     * @param id
     * @param voicePrintValue
     */
    public VoicePrint(String id, String voicePrintValue) {
        super(id);
        this.voicePrintValue = voicePrintValue;
    }

    /**
     * getter for voicePrint value
     * @return
     */
    public String getVoicePrintValue() {
        return voicePrintValue;
    }

    /**
     * formatted toString method
     * @return
     */
    @Override
    public String toString() {
        return "VoicePrint{" +
                "voicePrintValue='" + voicePrintValue + '\'' +
                "} " + super.toString();
    }
}
