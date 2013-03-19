package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Calendar implements Serializable {
	private String name;
	private User owner;
	private ArrayList<Event> events;

	public Calendar(){
	}
	public Calendar(String name, User owner) {
		this.name = name;
		this.owner = owner;
	}

	public String toString() {
		return String.format("Calendar; Name: %s, User: %s, Number of events: %s", name, owner.getUsername(), events.size());
	}

	public String getName() {
		return name;
	}
	public User getOwner() {
		return owner;
	}
	public ArrayList<Event> getEvents() {
		return events;
	}
	public void addEvent(Event event) {
		events.add(event);
	}
	public void addEvents(ArrayList<Event> events) {
		events.addAll(events);
	}

}
