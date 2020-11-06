package cscie97.smartcity.model.service;


public class ModelServiceException extends Exception{

	private String action;
	private String reason;

	/**
	 * Model Service exception constructor
	 * @param action
	 * @param reason
	 */
	public ModelServiceException(String action, String reason) {
		this.action = action;
		this.reason = reason;
	}

	/**
	 * Overridden toString method to return the content of the exception as formatted string
	 * @return
	 */
	@Override
	public String toString() {
		return "ModelServiceException{" +
				"action='" + action + '\'' +
				", reason='" + reason + '\'' +
				'}';
	}

}
