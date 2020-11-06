package cscie97.smartcity.model.domain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Device {

	private String id;
	private boolean enabled;
	private String accountAddress;
	private Event lastEvent;
	private List<Event> eventsList;
	private Location location;
	private Status currentStatus;
	private List<Sensor> sensorsList;

	/**
	 * abstrct contrcutor
	 */
	public Device() {
	}

	/**
	 * Device constructor for creating a new device object
	 * @param id device id
	 * @param enabled enabled vale
	 * @param accountAddress account addresss
	 * @param location device location
	 * @param currentStatus device status
	 */
	public Device(String id, boolean enabled, String accountAddress, Location location, Status currentStatus) {
		this.id = id;
		this.enabled = enabled;
		this.accountAddress = accountAddress;
		this.location = location;
		this.currentStatus = currentStatus;
		this.eventsList = new LinkedList<>();
		this.sensorsList = Arrays.asList(Sensor.values());
	}

	/**
	 * device id getter
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * device id setter
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * device enabled value getter
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * device enabled value setter
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * device account address getter
	 * @return
	 */
	public String getAccountAddress() {
		return accountAddress;
	}

	/**
	 * device account address setter
	 * @param accountAddress
	 */
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	/**
	 * devie last event getter
	 * @return
	 */
	public Event getLastEvent() {
		return lastEvent;
	}

	/**
	 * device last event setter
	 * @param lastEvent
	 */
	public void setLastEvent(Event lastEvent) {
		this.lastEvent = lastEvent;
	}

	/**
	 * device location getter
	 * @return
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * device location setter
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * device status getter
	 * @return
	 */
	public Status getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * device status setter
	 * @param currentStatus
	 */
	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * device events list getter
	 * @return
	 */
	public List<Event> getEventsList() {
		return eventsList;
	}

	/**
	 * device sensor list getter
	 * @return
	 */
	public List<Sensor> getSensorsList() {
		return sensorsList;
	}

	/**
	 * device sensor list setter
	 * @param sensorsList
	 */
	public void setSensorsList(List<Sensor> sensorsList) {
		this.sensorsList = sensorsList;
	}

	/**
	 * ToString method to retutn the content of the object as a string
	 * @return String
	 */
	@Override
	public String toString() {
		return "Device{" +
				"id='" + id + '\'' +
				", enabled=" + enabled +
				", accountAddress='" + accountAddress + '\'' +
				", lastEvent=" + lastEvent +
				", location=" + location +
				", currentStatus=" + currentStatus +
				", sensorsList=" + sensorsList +
				'}';
	}
}
