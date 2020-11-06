package cscie97.smartcity.model.domain;

public class Location {

	private float latitude;
	private float longitude;

	/**
	 * constructor for location
	 * @param latitude
	 * @param longitude
	 */
	public Location(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * location lat getter
	 * @return
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * location lat setter
	 * @param latitude
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * location long getter
	 * @return
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * location long setter
	 * @param longitude
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * ToString method to show object content
	 * @return
	 */
	@Override
	public String toString() {
		return "Location{" +
				"latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}

}
