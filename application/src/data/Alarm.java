package data;

import java.io.Serializable;

public class Alarm implements Serializable {
	private String timeBefore;
	private String message;
	private User owner;
	private Event event;

	public Alarm(){
	}
	public Alarm(String timeBefore, String message, User owner, Event event) {
		this.timeBefore = timeBefore;
		this.message = message;
		this.owner = owner;
		this.event = event;
	}

	public String toString() {
		return String.format("Alarm; Time: %s, Message: %s, Owner: %s, Event: %s", timeBefore, message, owner.getUsername(), event.getId());
	}

	public String getTimeBefore() {
		return timeBefore;
	}
	public void setTimeBefore(String timeBefore) {
		this.timeBefore = timeBefore;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

}
