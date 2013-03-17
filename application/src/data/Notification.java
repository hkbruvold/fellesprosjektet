package data;

public class Notification implements Serializeable {
	private int id;
	private String message;
	// TODO private 'something' options;
	
	public Notification(){
	}
	/**
	 * Use id = -1 when creating new objects. Actual ID should come from database
	 */
	public Notification(int id, String message) {
		this.id = id;
		this.message = message;
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
	// TODO getter and setter for options
}
