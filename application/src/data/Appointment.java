package data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Appointment extends Event implements Serializable {

	public Appointment(){
	}
	/**
	 * Use id = 0 when creating new objects. Actual ID should come from database
	 */
	public Appointment(int id, String startDateTime, String endDateTime, String description, String location, User owner) {
		super(id, startDateTime, endDateTime, description, location, owner);
	}
	public Appointment(int id, String startDateTime, String endDateTime, String description, Room room, User owner) {
		super(id, startDateTime, endDateTime, description, room, owner);
	}

	public String toString() {
		if (room != null) {
			return String.format("Appointment; ID: %s, Description: %s, Start: %s, End: %s, Room: %s, Owner: %s", id, description, startDateTime, endDateTime, room, user.getUsername());
		} else {
			return String.format("Appointment; ID: %s, Description: %s, Start: %s, End: %s, Location: %s, Owner: %s", id, description, startDateTime, endDateTime, location, user.getUsername());
		}
	}

	public User getOwner() {
		return user;
	}
	public void setOwner(User owner) {
		this.user = owner;
	}

}
