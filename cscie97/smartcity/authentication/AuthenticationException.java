package cscie97.smartcity.authentication;

/**
 * Authentication Service exception class
 */
public class AuthenticationException extends Exception{

	private String action;
	private String reason;

	/**
	 * Authentication Service exception constructor
	 * @param action
	 * @param reason
	 */
	public AuthenticationException(String action, String reason) {
		this.action = action;
		this.reason = reason;
	}

	/**
	 * Overridden toString method to return the content of the exception as formatted string
	 * @return
	 */
	@Override
	public String toString() {
		return "AuthenticationException{" +
				"action='" + action + '\'' +
				", reason='" + reason + '\'' +
				'}';
	}

}
