package data;

public class Alarm implements Serializeable {
	private int id;
	private String timeBefore;
	private String message;
	private User owner;
	private Event event;
	
	public Alarm(){
	}
	/**
	 * Use id = -1 when creating new objects. Actual ID should come from database
	 */
	public Alarm(int id, String timeBefore, String message) {
		this.id = id;
		this.timeBefore = timeBefore;
		this.message = message;
	}
	
	public String toString() {
		return String.format("Alarm; ID: %s, Time: %s, Message: %s", id, timeBefore, message);
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getTimeBefore() {
		return timeBefore;
	}
	public void setTimeBefore(String timeBefore) {
		this.timeBefore = timeBefore;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
}
