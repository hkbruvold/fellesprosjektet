package client;

public class Appointment extends AbstractCalendarEvent {
	private User owner;

	public Appointment(Calendar calendar, String startDateTime, String endDateTime, String description, String location, Alarm alarm, User owner) { // TODO temp
		super(calendar, startDateTime, endDateTime, description, location, alarm);
		this.owner = owner;
	}
	
	// TODO
	
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
		// TODO update database!
	}
	
}
