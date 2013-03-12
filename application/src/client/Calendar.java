package client;

import java.util.ArrayList;

public class Calendar {
	private String name;
	private User owner;
	private ArrayList<AbstractCalendarEvent> events;
	
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
