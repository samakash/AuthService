package cscie97.smartcity.model.domain;

public class Resident extends Person {

	private String name;
	private String phone;
	private String accountAddress;
	private Role role;

	/**
	 * contructor for person
	 * @param id
	 * @param biometricId
	 * @param location
	 * @param name
	 * @param phone
	 * @param accountAddress
	 * @param role
	 */
	public Resident(String id, String biometricId, Location location, String name, String phone, String accountAddress, Role role) {
		super(id, biometricId,location);
		this.name = name;
		this.phone = phone;
		this.accountAddress = accountAddress;
		this.role = role;
	}

	/**
	 * person name getter
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * person name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * person phone getter
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * person phone setter
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * person account address getter
	 * @return
	 */
	public String getAccountAddress() {
		return accountAddress;
	}

	/**
	 * person account address setter
	 * @param accountAddress
	 */
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	/**
	 * person role getter
	 * @return
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * person role setter
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * toString method to show object contents
	 * @return
	 */
	@Override
	public String toString() {
		return "Resident{" +
				"id='" + super.getId() + '\'' +
				", biometricId='" + super.getBiometricId() + '\'' +
				", location=" + super.getLocation() +
				" name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", accountAddress='" + accountAddress + '\'' +
				", role=" + role +
				'}';
	}
}
