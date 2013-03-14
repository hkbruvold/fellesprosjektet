package client;

import java.util.ArrayList;

public class Calendar implements Serializeable {
	private String name;
	private User owner;
	private ArrayList<AbstractCalendarEvent> events;
	
	public Calendar(String name, User owner) {
		this.name = name;
		this.owner = owner;
	}
	
	// TODO
	
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
