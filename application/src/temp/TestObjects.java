package temp;

import java.util.Arrays;

import client.*;

public class TestObjects {
	private static final String DATE_0 = "2013-10-10";
	private static final String DATE_1 = "2013-10-12";
	private static final String TIME_0 = "20.00";
	private static final String TIME_1 = "22.00";
	private static final String DATE_TIME_0 = DATE_0 + " " + TIME_0;
	private static final String DATE_TIME_1 = DATE_1 + " " + TIME_1;
	private static final String DESCRIPTION = "Description";
	private static final String LOCATION = "Location";
	
	public static Alarm getAlarm00() {
		return new Alarm(DATE_0, TIME_0);
	}
	public static Alarm getAlarm01() {
		return new Alarm(DATE_1, TIME_1, DESCRIPTION);
	}
	
	public static Appointment getAppointment00() {
		return new Appointment(getCalendar00(), DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getAlarm00(), getUser00());
	}
	public static Appointment getAppointment01() {
		return new Appointment(getCalendar01(), DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getAlarm01(), getUser01());
	}
	
	public static Calendar getCalendar00() {
		return new Calendar(getUser00().getName() + "'s calendar", getUser00());
	}
	public static Calendar getCalendar01() {
		return new Calendar(getUser01().getName() + "'s calendar", getUser01());
	}
	
	public static Group getGroup00() {
		Group group = new Group("Group00");
		group.addMember(getUser00());
		return group;
	}
	public static Group getGroup01() {
		Group group = new Group("Group01");
		group.addMember(getUser01());
		group.addMember(getUser02());
		return group;
	}
	public static Group getGroup02() {
		Group group = new Group("Group02");
		group.addMembers(Arrays.asList(new User[]{getUser00(), getUser01(), getUser02()}));
		return group;
	}
	
	public static User[] getUserArray00() {
		return new User[]{getUser00(), getUser02()};
	}
	public static User[] getUserArray01() {
		return new User[]{getUser00(), getUser01(), getUser02()};
	}
	
	public static Meeting getMeeting00() {
		return new Meeting(getCalendar00(), DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getAlarm00(), getUser00());
	}
	public static Meeting getMeeting01() {
		Meeting meeting = new Meeting(getCalendar01(), DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getAlarm01(), getUser01());
		meeting.inviteParticipant(getUser00());
		meeting.inviteParticipant(getUser01());
		return meeting;
	}
	
	public static Notification getNotification00() {
		return new Notification(DESCRIPTION);
	}
	public static Notification getNotification01() {
		return new Notification(DESCRIPTION + "\n" + LOCATION);
	}
	
	public static User getUser00() {
		return new User("Alice");
	}
	public static User getUser01() {
		return new User("Bob");
	}
	public static User getUser02() {
		return new User("Charlie");
	}
	
	
}
