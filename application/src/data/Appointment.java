package data;

public class Appointment extends AbstractCalendarEvent implements Serializeable {
	private int id; // TODO
	private User owner;
	
	public Appointment(){
	}
	public Appointment(Calendar calendar, String startDateTime, String endDateTime, String description, String location, Alarm alarm, User owner) { // TODO temp
		super(calendar, startDateTime, endDateTime, description, location, alarm);
		this.owner = owner;
	}
	
	// TODO
	
	public String toString() {
		return String.format("ID: %s, Description: %s, Start: %s, End: %s, Location: %s, Owner: %s", id, description, startDateTime, endDateTime, location, owner.getUsername());
	}
	
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
		// TODO update database!
	}
	
}
