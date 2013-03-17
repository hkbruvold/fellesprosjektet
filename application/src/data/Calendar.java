package data;

import java.util.ArrayList;

public class Calendar implements Serializeable {
	private String name;
	private User owner;
	private ArrayList<AbstractCalendarEvent> events;
	
	public Calendar(){
	}
	public Calendar(String name, User owner) {
		this.name = name;
		this.owner = owner;
	}
	
	// TODO
	
	public String toString() {
		return String.format("Calendar; Name: %s, User: %s, Number of events: %s", name, owner.getUsername(), events.size());
	}
	
	public String getName() {
		return name;
	}
	public User getOwner() {
		return owner;
	}
	public ArrayList<AbstractCalendarEvent> getEvents() {
		return events;
	}
	public void addEvent(AbstractCalendarEvent event) {
		events.add(event);
		// TODO update database!
	}
}
