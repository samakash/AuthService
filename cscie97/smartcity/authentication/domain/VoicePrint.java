package cscie97.smartcity.authentication.domain;

public class VoicePrint extends Credential {

    private String voicePrintValue;

    public VoicePrint(String id, String voicePrintValue) {
        super(id);
        this.voicePrintValue = voicePrintValue;
    }


    public String getVoicePrintValue() {
        return voicePrintValue;
    }

    public void setVoicePrintValue(String voicePrintValue) {
        this.voicePrintValue = voicePrintValue;
    }

    @Override
    public String toString() {
        return "VoicePrint{" +
                "voicePrintValue='" + voicePrintValue + '\'' +
                "} " + super.toString();
    }
}
