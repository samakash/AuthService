package cscie97.smartcity.model.domain;

public abstract class Person {

	private String id;
	private String biometricId;
	private Location location;

	/**
	 * construct for Person
	 * @param id
	 * @param biometricId
	 * @param location
	 */
	public Person(String id, String biometricId, Location location) {
		this.id = id;
		this.biometricId = biometricId;
		this.location = location;
	}

	/**
	 * person id getter
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * person id setter
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * person bio-metric id getter
	 * @return
	 */
	public String getBiometricId() {
		return biometricId;
	}

	/**
	 * person bio-metric id  setter
	 * @param biometricId
	 */
	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}

	/**
	 * person location getter
	 * @return
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * person location setter
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * ToString method to display object contents
	 * @return
	 */
	@Override
	public String toString() {
		return "Person{" +
				"id='" + id + '\'' +
				", biometricId='" + biometricId + '\'' +
				", location=" + location +
				'}';
	}
}
