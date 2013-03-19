package data;

import java.io.Serializable;
import java.util.Calendar;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public abstract class Event implements Serializable {
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
	protected String location;
	@Element
	protected Room room;
	@Element(required=false)
	protected Alarm alarm;

	public Event(){
	}
	/**
	 * Use id = 0 when creating new objects. Actual ID should come from database
	 */
	public Event(int id, Calendar calendar, String startDateTime, String endDateTime, String description, String location) {
		this.id = id;
		this.calendar = calendar;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.description = description;
		this.location = location;
		this.room = null;
	}
	public Event(int id, Calendar calendar, String startDateTime, String endDateTime, String description, Room room) {
		this.id = id;
		this.calendar = calendar;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.description = description;
		this.location = null;
		this.room = room;
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
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Alarm getAlarm() {
		return alarm;
	}
	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
	
	public Calendar getStartDate() {
		String date = this.startDateTime.split(" ")[0];
		String time = this.startDateTime.split(" ")[1];
		
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(date.split("-")[0]),
				Integer.parseInt(date.split("-")[1]),
				Integer.parseInt(date.split("-")[2]), 
				Integer.parseInt(time.split(":")[0]),
				Integer.parseInt(time.split(":")[1]));
		
		return cal;
	}
	
	public Calendar getEndDate() {
		String date = this.endDateTime.split(" ")[0];
		String time = this.endDateTime.split(" ")[1];
		
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(date.split("-")[0]),
				Integer.parseInt(date.split("-")[1]),
				Integer.parseInt(date.split("-")[2]), 
				Integer.parseInt(time.split(":")[0]),
				Integer.parseInt(time.split(":")[1]));
		
		return cal;
	}
}
