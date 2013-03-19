package temp;

import java.util.Arrays;

import data.*;

public class TestObjects {
	private static final String LOGIN = "login";
	private static final String DATE_0 = "2013-10-10";
	private static final String DATE_1 = "2013-10-12";
	private static final String TIME_0 = "20:00";
	private static final String TIME_1 = "22:00";
	private static final String DATE_TIME_0 = DATE_0 + " " + TIME_0;
	private static final String DATE_TIME_1 = DATE_1 + " " + TIME_1;
	private static final String DESCRIPTION = "Description";
	private static final String LOCATION = "Location";

	private static final int DEFAULT_ID = 0;

	private static final User ALICE = new User("alice", "Alice", "Employer");
	private static final User BOB = new User("bob", "Bob", "Employer");
	private static final User CHARLIE = new User("charlie", "Charlie", "Employer");
	private static final Authentication AUTHENTICATION = new Authentication("ALICE", "123", LOGIN );
	private static final User[] USER_ARRAY_00 = new User[]{getUser00(), getUser02()};
	private static final User[] USER_ARRAY_01 = new User[]{getUser00(), getUser01(), getUser02()};
	private static final Group GROUP_00 = new Group(DEFAULT_ID, "Group00", "");
	private static final Group GROUP_01 = new Group(DEFAULT_ID, "Group01", "");
	private static final Group GROUP_02 = new Group(DEFAULT_ID, "Group02", "All test-users");
	private static final Room ROOM_02 = new Room(DEFAULT_ID, 100, "R3");
	private static final Room ROOM_01 = new Room(DEFAULT_ID, 7, "R2");
	private static final Room ROOM_00 = new Room(DEFAULT_ID, 6, "R1");
	private static final Appointment APPOINTMENT_00 = new Appointment(DEFAULT_ID, DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getUser00());
	private static final Appointment APPOINTMENT_01 = new Appointment(DEFAULT_ID, DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getUser01());
	private static final Meeting MEETING_00 = new Meeting(DEFAULT_ID, DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getUser00());
	private static final Meeting MEETING_01 = new Meeting(DEFAULT_ID, DATE_TIME_0, DATE_TIME_1, DESCRIPTION, LOCATION, getUser01());
	private static final Notification NOTIFICATION_00 = new Notification(DEFAULT_ID, DESCRIPTION, getUser02());
	private static final Notification NOTIFICATION_01 = new Notification(DEFAULT_ID, DESCRIPTION + " : " + LOCATION, getUser02());
	private static final Alarm ALARM_01 = new Alarm(TIME_1, DESCRIPTION, getUser01(), getMeeting01());
	private static final Alarm ALARM_00 = new Alarm(TIME_0, "", getUser00(), getAppointment00());

	public static Authentication getAuthentication00(){
		return AUTHENTICATION;
	}
	public static Alarm getAlarm00() {
		return ALARM_00;
	}
	public static Alarm getAlarm01() {
		return ALARM_01;
	}

	public static Appointment getAppointment00() {
		return APPOINTMENT_00;
	}
	public static Appointment getAppointment01() {
		return APPOINTMENT_01;
	}

	public static Group getGroup00() {
		Group group = GROUP_00;
		group.addMember(getUser00());
		return group;
	}
	public static Group getGroup01() {
		Group group = GROUP_01;
		group.addMember(getUser01());
		group.addMember(getUser02());
		return group;
	}
	public static Group getGroup02() {
		Group group = GROUP_02;
		group.addMembers(Arrays.asList(USER_ARRAY_01));
		return group;
	}

	public static User[] getUserArray00() {
		return USER_ARRAY_00;
	}
	public static User[] getUserArray01() {
		return USER_ARRAY_01;
	}

	public static Meeting getMeeting00() {
		Meeting meeting = MEETING_00;
		meeting.acceptInvite(getUser01());
		meeting.declineInvite(getUser02());
		return meeting;
	}
	public static Meeting getMeeting01() {
		Meeting meeting = MEETING_01;
		meeting.inviteParticipant(getUser00());
		meeting.inviteParticipant(getUser02());
		return meeting;
	}

	public static Notification getNotification00() {
		Notification notification = NOTIFICATION_00;
		notification.setEvent(getMeeting00());
		return notification;
	}
	public static Notification getNotification01() {
		Notification notification = NOTIFICATION_01;
		return notification;
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
		return ROOM_00;
	}

	public static Room getRoom01() {
		return ROOM_01;
	}

	public static Room getRoom02() {
		return ROOM_02;
	}
}
