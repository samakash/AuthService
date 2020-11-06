package cscie97.smartcity.model.domain;

public class Vehicle extends Device {

	private String activity;
	private int maxRiderCapacity;
	private double fee;
	private VehicleType vehicleType;

	/**
	 * Constructor for vehicle
	 * @param id
	 * @param accountAddress
	 * @param location
	 * @param currentStatus
	 * @param enabled
	 * @param maxRiderCapacity
	 * @param fee
	 * @param vehicleType
	 */
	public Vehicle(String id, String accountAddress, Location location, Status currentStatus, boolean enabled,
				   int maxRiderCapacity, double fee, VehicleType vehicleType) {
		super(id, enabled, accountAddress, location, currentStatus);
		this.activity = "New Vehicle";
		this.maxRiderCapacity = maxRiderCapacity;
		this.fee = fee;
		this.vehicleType = vehicleType;
	}

	/**
	 * vehicle activity getter
	 * @return
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * vehicle activity setter
	 * @param activity
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * vehicle max capacity getter
	 * @return
	 */
	public int getMaxRiderCapacity() {
		return maxRiderCapacity;
	}

	/**
	 * vehicle max capacity ssetter
	 * @param maxRiderCapacity
	 */
	public void setMaxRiderCapacity(int maxRiderCapacity) {
		this.maxRiderCapacity = maxRiderCapacity;
	}

	/**
	 * vehicle fee getter
	 * @return
	 */
	public double getFee() {
		return fee;
	}

	/**
	 * vehicle fee setter
	 * @param fee
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}

	/**
	 * vehicle type getter
	 * @return
	 */
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	/**
	 * vehicle type setter
	 * @param vehicleType
	 */
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * toString method to display object contents
	 * @return
	 */
	@Override
	public String toString() {
		return "Vehicle{" +
				"activity='" + activity + '\'' +
				", maxRiderCapacity=" + maxRiderCapacity +
				", fee=" + fee +
				", vehicleType=" + vehicleType +
				"} " + super.toString();
	}
}
