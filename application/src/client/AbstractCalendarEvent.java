package client;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public abstract class AbstractCalendarEvent implements Serializeable {
	@Element
	private Calendar calendar;
	@Element
	private String startDateTime;
	@Element
	private String endDateTime;
	@Element
	private String description;
	@Element
	private String location; //Room? Both?
	@Element
	private Alarm alarm;
	
	public AbstractCalendarEvent(Calendar calendar, String startDateTime, String endDateTime, String description, String location, Alarm alarm) {
		this.calendar = calendar;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.description = description;
		this.location = location;
		this.alarm = alarm;
	}
	
	// TODO
	
	public Calendar getCalendar() {
		return calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		// TODO update database!
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
		// TODO update database!
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
		// TODO update database!
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		// TODO update database!
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
		// TODO update database!
	}
	public Alarm getAlarm() {
		return alarm;
	}
	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
		// TODO update database!
	}
	
}
