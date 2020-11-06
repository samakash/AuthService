package cscie97.smartcity.model.cmdProcessor;

/**
 * The Service Exception is returned from the Model Service API methods in response to an error
 * condition. The Exception captures the action that was attempted and the reason for the
 * failure.
 */
public class CommandProcessorException extends Exception{

	private String command;
	private String reason;
	private int lineNumber;

	/**
	 * CommandProcessorException constructor
	 * @param command
	 * @param reason
	 * @param lineNumber
	 */
	public CommandProcessorException(String command, String reason, int lineNumber) {
		this.command = command;
		this.reason = reason;
		this.lineNumber = lineNumber;
	}

	/**
	 * Getter for command
	 * @return
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * setter for command
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * getter for reason
	 * @return
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * setter for reason
	 * @param reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * getter for line number
	 * @return
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * setter for line number
	 * @param lineNumber
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * Overridden method used to return string of the exception contents as String
	 * @return string of the exception
	 */
	@Override
	public String toString() {
		return "CommandProcessorException{" +
				"command='" + command + '\'' +
				", reason='" + reason + '\'' +
				", lineNumber=" + lineNumber +
				'}';
	}
}
