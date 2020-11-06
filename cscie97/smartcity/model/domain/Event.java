package cscie97.smartcity.model.domain;

public class Event {

	private String action;
	private Sensor type;
	private Person subject;

	/**
	 * constructor to create a new event object
	 * @param action
	 * @param type
	 * @param subject
	 */
	public Event(String action, Sensor type, Person subject) {
		this.action = action;
		this.type = type;
		this.subject = subject;
	}

	/**
	 * constructor to create a new event object without subject
	 * @param action
	 * @param type
	 */
	public Event(String action, Sensor type) {
		this.action = action;
		this.type = type;
	}

	/**
	 * event action getter
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * event action setter
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * event sensor type getter
	 * @return
	 */
	public Sensor getType() {
		return type;
	}

	/**
	 * event sesnor type setter
	 * @param type
	 */
	public void setType(Sensor type) {
		this.type = type;
	}

	/**
	 * event subject getter
	 * @return
	 */
	public Person getSubject() {
		return subject;
	}

	/**
	 * event subject setter
	 * @param subject
	 */
	public void setSubject(Person subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Event{" +
				"action='" + action + '\'' +
				", type=" + type +
				", subject=" + subject +
				'}';
	}
}
