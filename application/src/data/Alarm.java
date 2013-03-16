package data;

public class Alarm implements Serializeable {
	private String date; // Should we use something other
	private String time; // than String for these?
	private String message;
	private int id;
	
	public Alarm(){
	}
	
	public Alarm(String date, String time, String message) {
		this.date = date;
		this.time = time;
		this.message = message;
		// TODO write to database
	}
	public Alarm(String date, String time) {
		this(date, time, null);
	}

	// TODO
	
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
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	
}
