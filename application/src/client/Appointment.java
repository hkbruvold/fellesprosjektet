package client;

public class Appointment extends AbstractCalendarEvent {
	private User owner;

	// TODO
	
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
		// TODO update database!
	}
	
}
