package data;

public class Notification implements java.io.Serializable {
	private int id;
	private String message;
	private Event event;
	private User recipient;
	// TODO private 'something' options;
	
	public Notification(){
	}
	/**
	 * Use id = 0 when creating new objects. Actual ID should come from database
	 */
	public Notification(int id, String message, User recipient) {
		this(id, message, null, recipient);
	}
	public Notification(int id, String message, Event event, User recipient) {
		this.id = id;
		this.message = message;
		this.event = event;
		this.recipient = recipient;
		// TODO options
	}
	
	public String toString() {
		return String.format("Notification; ID: %s, Message: %s", id, message);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public User getRecipient() {
		return recipient;
	}
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	// TODO getter and setter for options
}
