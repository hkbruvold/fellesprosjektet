package data;

public class Alarm implements Serializeable {
	private int id; // TODO
	private String date; // Should we use something other
	private String time; // than String for these?
	private String message; // TODO
	
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
	public void setDate(String date) {
		this.date = date;
		// TODO update database!
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
		// TODO update database!
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
		// TODO update database!
	}
	
}
