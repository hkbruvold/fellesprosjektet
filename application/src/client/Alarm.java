package client;

public class Alarm {
	private String date; // Should we use something other
	private String time; // than String for these?
	private String message;
	
	public Alarm(String date, String time, String message) {
		this.date = date;
		this.time = time;
		this.message = message;
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
	
}