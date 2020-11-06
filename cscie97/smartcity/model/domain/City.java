package cscie97.smartcity.model.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class City {

	private String id;
	private String name;
	private String accountAddress;
	private float radius;
	private Map<String,Person> peopleMap;
	private Map<String, Device> devicesMap;
	private Location location;

	/**
	 * Constructor for City
	 * @param id city id
	 * @param name name of the city
	 * @param accountAddress blockchian account address of this city
	 * @param radius radius of the city
	 * @param location location of the city center
	 */
	public City(String id, String name, String accountAddress, float radius, Location location) {
		this.id = id;
		this.name = name;
		this.accountAddress = accountAddress;
		this.radius = radius;
		this.location = location;
		this.devicesMap = new HashMap<>();
		this.peopleMap = new HashMap<>();
	}

	/**
	 * City Id getter
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * city Id setter
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * city name getter
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * city name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * city blockchian account address getter
	 * @return
	 */
	public String getAccountAddress() {
		return accountAddress;
	}

	/**
	 * city blockhain account address setter
	 * @param accountAddress
	 */
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	/**
	 * city radius getter
	 * @return
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * city radius setter
	 * @param radius
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * city devices map getter
	 * @return
	 */
	public Map<String, Person> getPeopleMap() {
		return peopleMap;
	}

	/**
	 * city people (Person) map getter
	 * @return
	 */
	public Map<String, Device> getDevicesMap() {
		return devicesMap;
	}

	/**
	 * city location gettter
	 * @return
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * city location setter
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "City{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", accountAddress='" + accountAddress + '\'' +
				", radius=" + radius +
				", peopleMap=" + peopleMap +
				", devicesMap=" + devicesMap +
				", location=" + location +
				'}';
	}


	/**
	 * Method used to calculate if device or person is within city limits. method will calculate distance between city
	 * location and the parameters location
	 * @param lat1 location lat
	 * @param lon1 location long
	 * @return
	 * citation: https://www.geodatasource.com/developers/java
	 */
	public boolean validateLocation(float lat1, float lon1){
		float lat2 = getLocation().getLatitude();
		float lon2 = getLocation().getLongitude();
		float cityRadius = getRadius();

		if ((lat1 == lat2) && (lon1 == lon2)) {
			return false;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
					* Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if(dist <= cityRadius){
				return true;
			} else
				return false;

		}
	}

}
