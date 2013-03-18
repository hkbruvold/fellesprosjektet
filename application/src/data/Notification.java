package data;

public class Notification implements Serializeable {
	private int id;
	private String message;
	private Event event;
	// TODO private 'something' options;
	
	public Notification(){
	}
	/**
	 * Use id = 0 when creating new objects. Actual ID should come from database
	 */
	public Notification(int id, String message) {
		this(id, message, null);
	}
	public Notification(int id, String message, Event event) {
		this.id = id;
		this.message = message;
		this.event = event;
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
	// TODO getter and setter for options
}
