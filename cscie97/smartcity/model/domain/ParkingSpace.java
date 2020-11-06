package cscie97.smartcity.model.domain;

public class ParkingSpace extends Device {

	private double hourlyRate;

	/**
	 * Constructor for creating new parking space object
	 * @param id
	 * @param accountAddress
	 * @param location
	 * @param currentStatus
	 * @param enabled
	 * @param hourlyRate
	 */
	public ParkingSpace(String id, String accountAddress, Location location, Status currentStatus, boolean enabled, double hourlyRate) {
		super(id, enabled, accountAddress, location, currentStatus);
		this.hourlyRate = hourlyRate;
	}

	/**
	 * parking space hourly rate getter
	 * @return
	 */
	public double getHourlyRate() {
		return hourlyRate;
	}

	/**
	 * parking space hourly rate setter
	 * @param hourlyRate
	 */
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	/**
	 * method for display object contents
	 * @return
	 */
	@Override
	public String toString() {
		return "ParkingSpace{" +
				"hourlyRate=" + hourlyRate +
				"} " + super.toString();
	}
}
