package data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public abstract class Event implements Serializeable {
	@Element
	protected int id;
	@Element
	protected Calendar calendar;
	@Element
	protected String startDateTime;
	@Element
	protected String endDateTime;
	@Element
	protected String description;
	@Element
	protected String location; //Room? Both?
	@Element(required=false)
	protected Alarm alarm;
	
	public Event(){
	}
	/**
	 * Use id = -1 when creating new objects. Actual ID should come from database
	 */
	public Event(int id, Calendar calendar, String startDateTime, String endDateTime, String description, String location, Alarm alarm) {
		this.id = id;
		this.calendar = calendar;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.description = description;
		this.location = location;
		this.alarm = alarm;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
