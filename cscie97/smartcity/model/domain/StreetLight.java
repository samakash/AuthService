package cscie97.smartcity.model.domain;

public class StreetLight extends Device {

	private int brightness;

	/**
	 * Street light constructor
	 * @param id
	 * @param accountAddress
	 * @param location
	 * @param enabled
	 * @param currentStatus
	 * @param brightness
	 */
	public StreetLight(String id, String accountAddress, Location location, boolean enabled, Status currentStatus, int brightness) {
		super(id, enabled, accountAddress, location, currentStatus);
		this.brightness = brightness;
	}

	/**
	 * street light brightness getter
	 * @return
	 */
	public int getBrightness() {
		return brightness;
	}

	/**
	 * street light brightness setter
	 * @param brightness
	 */
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	/**
	 * toString method to show object contents
	 * @return
	 */
	@Override
	public String toString() {
		return "StreetLight{" +
				"brightness=" + brightness +
				"} " + super.toString();
	}
}
