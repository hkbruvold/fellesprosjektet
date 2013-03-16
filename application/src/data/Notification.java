package data;

public class Notification implements Serializeable {
	private int id; // TODO
	private String message;
	// TODO private 'something' options;
	
	public Notification(){
	}
	public Notification(String message) {
		this.message = message;
		// TODO options
	}
	
	// TODO
	
	public String toString() {
		return String.format("ID: %s, Message: %s", id, message);
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	// TODO getter and setter for options
}
