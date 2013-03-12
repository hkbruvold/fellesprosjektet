package client;

public abstract class AbstractCalendarEvent {
	private Calendar calendar;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String description;
	private String location; //Room? Both?
	private Alarm alarm;
	
	// TODO
	
	public Calendar getCalendar() {
		return calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		// TODO update database!
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
		// TODO update database!
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
		// TODO update database!
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
		// TODO update database!
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
