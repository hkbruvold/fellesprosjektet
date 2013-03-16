package client;

public class Notification implements Serializeable {
	private String message;
	// TODO private 'something' options;
	public Notification(){
		
	}
	public Notification(String message) {
		this.message = message;
		// TODO options
	}
	
	// TODO
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	// TODO getter and setter for options
}
