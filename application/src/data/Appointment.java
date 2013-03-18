package data;

public class Appointment extends Event implements Serializeable {
	private User owner;
	
	public Appointment(){
	}
	/**
	 * Use id = 0 when creating new objects. Actual ID should come from database
	 */
	public Appointment(int id, Calendar calendar, String startDateTime, String endDateTime, String description, String location, User owner) { // TODO temp
		super(id, calendar, startDateTime, endDateTime, description, location);
		this.owner = owner;
	}
	
	public String toString() {
		return String.format("Appointment; ID: %s, Description: %s, Start: %s, End: %s, Location: %s, Owner: %s", id, description, startDateTime, endDateTime, location, owner.getUsername());
	}
	
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
}
