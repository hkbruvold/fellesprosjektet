package temp;

import java.util.Arrays;

import client.*;
import data.Alarm;
import data.Appointment;
import data.Authentication;
import data.Calendar;
import data.Group;
import data.Meeting;
import data.Notification;
import data.Room;
import data.User;

public class TestObjects {
	private static final User CHARLIE = new User("charlie", "Charlie", "Employer");
	private static final User BOB = new User("bob", "Bob", "Employer");
	private static final User ALICE = new User("alice", "Alice", "Employer");
	
	private static final String LOGIN = "login";
	private static final String LOGOUT = "logout";
	private static final String DATE_0 = "2013-10-10";
	private static final String DATE_1 = "2013-10-12";
	private static final String TIME_0 = "20:00";
	private static final String TIME_1 = "22:00";
	private static final String DATE_TIME_0 = DATE_0 + " " + TIME_0;
	private static final String DATE_TIME_1 = DATE_1 + " " + TIME_1;
	private static final String DESCRIPTION = "Description";
	private static final String LOCATION = "Location";
	
	private static final int DEFAULT_ID = 0;
	
	public static Authentication getAuthentication00(){
		return new Authentication("ALICE", "123", LOGIN );
	}
	public static Alarm getAlarm00() {
		return new Alarm(TIME_0, "", getUser00(), getAppointment00());
	}
	public static Alarm getAlarm01() {
		return new Alarm(TIME_1, DESCRIPTION, getUser01(), getMeeting01());
	}
	
	public static Appointment getAppointment00() {
		return new Appointment(2, getCalendar00(), DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getUser00());
	}
	public static Appointment getAppointment01() {
		return new Appointment(2, getCalendar01(), DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getUser01());
	}
	
	public static Calendar getCalendar00() {
		return new Calendar(getUser00().getName() + "'s calendar", getUser00());
	}
	public static Calendar getCalendar01() {
		return new Calendar(getUser01().getName() + "'s calendar", getUser01());
	}
	
	public static Group getGroup00() {
		Group group = new Group(DEFAULT_ID, "Group00", "");
		group.addMember(getUser00());
		return group;
	}
	public static Group getGroup01() {
		Group group = new Group(DEFAULT_ID, "Group01", "");
		group.addMember(getUser01());
		group.addMember(getUser02());
		return group;
	}
	public static Group getGroup02() {
		Group group = new Group(DEFAULT_ID, "Group02", "All test-users");
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
		Meeting meeting = new Meeting(DEFAULT_ID, getCalendar00(), DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getUser00());
		meeting.acceptInvite(getUser01());
		meeting.declineInvite(getUser02());
		return meeting;
	}
	public static Meeting getMeeting01() {
		Meeting meeting = new Meeting(DEFAULT_ID, getCalendar01(), DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getUser01());
		meeting.inviteParticipant(getUser00());
		meeting.inviteParticipant(getUser02());
		return meeting;
	}
	
	public static Notification getNotification00() {
		return new Notification(DEFAULT_ID, DESCRIPTION);
	}
	public static Notification getNotification01() {
		return new Notification(DEFAULT_ID, DESCRIPTION + " : " + LOCATION);
	}
	
	public static User getUser00() {
		ALICE.setPassword("123");
		return ALICE;
	}
	public static User getUser01() {
		BOB.setPassword("123");
		return BOB;
	}
	public static User getUser02() {
		CHARLIE.setPassword("123");
		return CHARLIE;
	}
	
	public static Room getRoom00() {
		return new Room(1, 6, "R1");
	}
	
	public static Room getRoom01() {
		return new Room(2, 7, "R2");
	}
	
	public static Room getRoom02() {
		return new Room(3, 100, "R3");
	}
}
