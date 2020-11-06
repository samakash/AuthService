package cscie97.smartcity.controller;

/**
 * Controller Service exception class
 */
public class ControllerException extends Exception{

	private String action;
	private String reason;

	/**
	 * Controller Service exception constructor
	 * @param action
	 * @param reason
	 */
	public ControllerException(String action, String reason) {
		this.action = action;
		this.reason = reason;
	}

	/**
	 * Overridden toString method to return the content of the exception as formatted string
	 * @return
	 */
	@Override
	public String toString() {
		return "ControllerServiceException{" +
				"action='" + action + '\'' +
				", reason='" + reason + '\'' +
				'}';
	}

}
