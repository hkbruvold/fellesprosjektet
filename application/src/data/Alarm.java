package data;

public class Alarm implements Serializeable {
	private int id;
	private String date;
	private String time;
	private String message;
	private User owner;
	private Event event;
	
	public Alarm(){
	}
	/**
	 * Use id = -1 when creating new objects. Actual ID should come from database
	 */
	public Alarm(int id, String date, String time, String message) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.message = message;
	}
	public Alarm(String date, String time) {
		this(-1, date, time, null);
	}

	public String toString() {
		return String.format("Alarm; ID: %s, Date: %s, Time: %s, Message: %s", id, date, time, message);
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date, boolean updateDatabase) {
		this.date = date;
		// TODO update database?
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time, boolean updateDatabase) {
		this.time = time;
		// TODO update database!
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message, boolean updateDatabase) {
		this.message = message;
		// TODO update database?
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner, boolean updateDatabase) {
		this.owner = owner;
		// TODO update database?
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event, boolean updateDatabase) {
		this.event = event;
		// TODO update database?
	}
	
}
