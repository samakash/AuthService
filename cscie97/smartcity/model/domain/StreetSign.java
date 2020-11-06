package cscie97.smartcity.model.domain;

public class StreetSign extends Device {

	private String displayText;

	/**
	 * street sign constructor
	 * @param id
	 * @param enabled
	 * @param accountAddress
	 * @param location
	 * @param currentStatus
	 * @param displayText
	 */
	public StreetSign(String id, boolean enabled, String accountAddress, Location location, Status currentStatus, String displayText) {
		super(id, enabled, accountAddress, location, currentStatus);
		this.displayText = displayText;
	}

	/**
	 * street sign display text getter
	 * @return
	 */
	public String getDisplayText() {
		return displayText;
	}

	/**
	 * street sign display text setter
	 * @param displayText
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	/**
	 * toString function to show object contents
	 * @return
	 */
	@Override
	public String toString() {
		return "StreetSign{" +
				"displayText='" + displayText + '\'' +
				"} " + super.toString();
	}
}
