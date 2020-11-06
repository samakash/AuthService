package cscie97.smartcity.model.domain;

public class Robot extends Device {

	private String activity;

	/**
	 * Robot constructor
	 * @param id
	 * @param accountAddress
	 * @param location
	 * @param currentStatus
	 * @param enabled
	 * @param activity
	 */
	public Robot(String id,  String accountAddress, Location location, Status currentStatus, boolean enabled, String activity) {
		super(id, enabled, accountAddress, location, currentStatus);
		this.activity = activity;
	}

	/**
	 * robot activity getter
	 * @return
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * robot activity setter
	 * @param activity
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * toString to show object contents
	 * @return
	 */
	@Override
	public String toString() {
		return "Robot{" +
				"activity='" + activity + '\'' +
				"} " + super.toString();
	}
}
