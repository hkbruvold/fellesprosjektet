package server.database;

import data.*;

import temp.TestObjects;

public class Update {
	private static final String TABLE_ALARM = TableFields.ALARM.getTableName();
	private static final String TABLE_EVENT = TableFields.EVENT.getTableName();
	private static final String TABLE_GROUPS = TableFields.GROUPS.getTableName();
	private static final String TABLE_IS_MEMBER_OF = TableFields.IS_MEMBER_OF.getTableName();
	private static final String TABLE_IS_OWNER = TableFields.IS_OWNER.getTableName();
	private static final String TABLE_IS_PARTICIPANT = TableFields.IS_PARTICIPANT.getTableName();
	private static final String TABLE_NOTIFICATION = TableFields.NOTIFICATION.getTableName();
	private static final String TABLE_NOTIFICATION_FOR_EVENT = TableFields.NOTIFICATION_FOR_EVENT.getTableName();
	private static final String TABLE_NOTIFICATION_TO = TableFields.NOTIFICATION_TO.getTableName();
	private static final String TABLE_RESERVED_ROOM = TableFields.RESERVED_ROOM.getTableName();
	private static final String TABLE_ROOM = TableFields.ROOM.getTableName();
	private static final String TABLE_USER = TableFields.USER.getTableName();

	private static final String FIELDS_ALARM = TableFields.ALARM.getFieldsString();
	private static final String FIELDS_EVENT = TableFields.EVENT.getFieldsString();
	private static final String FIELDS_GROUPS = TableFields.GROUPS.getFieldsString();
	private static final String FIELDS_IS_MEMBER_OF = TableFields.IS_MEMBER_OF.getFieldsString();
	private static final String FIELDS_IS_OWNER = TableFields.IS_OWNER.getFieldsString();
	private static final String FIELDS_IS_PARTICIPANT = TableFields.IS_PARTICIPANT.getFieldsString();
	private static final String FIELDS_NOTIFICATION = TableFields.NOTIFICATION.getFieldsString();
	private static final String FIELDS_NOTIFICATION_FOR_EVENT = TableFields.NOTIFICATION_FOR_EVENT.getFieldsString();
	private static final String FIELDS_NOTIFICATION_TO = TableFields.NOTIFICATION_TO.getFieldsString();
	private static final String FIELDS_RESERVED_ROOM = TableFields.RESERVED_ROOM.getFieldsString();
	private static final String FIELDS_ROOM = TableFields.ROOM.getFieldsString();
	private static final String FIELDS_USER = TableFields.USER.getFieldsString();
	
	private static final String INSERT_INTO_VALUES = "INSERT INTO %s (%s) VALUES (%s)"; // "tableName", "fields", "values"
	private static final String DELETE_FROM_WHERE = "DELETE FROM %s WHERE %s"; // "tableName", "condition"
	private static final String UPDATE_SET_WHERE = "UPDATE %s SET %s WHERE %s"; // "tableName", "field1=value1, field2=value2 ...", "condition"
	
	private static final String BIT_FALSE = "0";
	private static final String BIT_TRUE = "1";
	
	private static final String DEFAULT_NEW_ID = "0";
	
	private static final String PARTICIPANT_STATUS_INVITED = "0";
	private static final String PARTICIPANT_STATUS_ACCEPTED = "1";
	private static final String PARTICIPANT_STATUS_DECLINED = "2";
	
// 	Please ignore! I'm using this as a shortcut to open classes. Query TableFields Event
	
	private User currentUser;
	private DatabaseCommunication dbComm;

	public Update(User currentUser, DatabaseCommunication dbComm) {
		this.currentUser = currentUser;
		this.dbComm = dbComm;
	}

	public void insertAlarm(Alarm alarm) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(alarm.getTimeBefore()).append("'").append(", ");
		values.append("'").append(alarm.getMessage()).append("'").append(", ");
		values.append("'").append(alarm.getOwner().getUsername()).append("'").append(", ");
		values.append(alarm.getEvent().getId()).append(" ");
		String updateString = String.format(INSERT_INTO_VALUES, TABLE_ALARM, FIELDS_ALARM, values.toString());
		System.out.println(updateString);
//		dbComm.update(updateString);
	}
	
	public void insertEvent(Event event) {
		StringBuilder values = new StringBuilder();
		values.append(DEFAULT_NEW_ID).append(", ");
		values.append(event.getStartDateTime()).append(", ");
		values.append(event.getEndDateTime()).append(", ");
		values.append(event.getLocation()).append(", ");
		values.append(event.getDescription()).append(", ");
		if (event instanceof Appointment) {
			values.append(BIT_FALSE).append(" ");
		} else if (event instanceof Meeting) {
			values.append(BIT_TRUE).append(" ");
		}
		String updateString = String.format(INSERT_INTO_VALUES, TABLE_EVENT, FIELDS_EVENT, values.toString());
		System.out.println(updateString);
//		dbComm.update(updateString);
		if (event instanceof Appointment) {
			insertIsOwner(((Appointment)event).getOwner(), event);
		} else if (event instanceof Meeting) {
			insertIsOwner(((Meeting)event).getLeader(), event);
			// TODO add relations!
		}
	}
	
	public void insertGroup(Group group) {
		StringBuilder values = new StringBuilder();
		values.append(group.getId()).append(", ");
		values.append("'").append(group.getName()).append("'").append(", ");
		values.append("'").append(group.getDescription()).append("'").append(" ");
		String updateString = String.format(INSERT_INTO_VALUES, TABLE_GROUPS, FIELDS_GROUPS, values.toString());
		System.out.println(updateString);
//		dbComm.update(updateString);
		// TODO group.setID( get id from database );
		for (User user : group.getMembers()) {
			insertIsMemberOf(user, group);
		}
	}
	
	public void insertNotification(Notification notification) {
		StringBuilder values = new StringBuilder();
		values.append(notification.getId()).append(", ");
		values.append("'").append(notification.getMessage()).append("'").append(" ");
		String updateString = String.format(INSERT_INTO_VALUES, TABLE_NOTIFICATION, FIELDS_NOTIFICATION, values.toString());
		System.out.println(updateString);
//		dbComm.update(updateString);
		// TODO relations
	}
	
	public void insertRoom(Room room) {
		StringBuilder values = new StringBuilder();
		values.append(room.getId()).append(", ");
		values.append(room.getSize()).append(", ");
		values.append("'").append(room.getDescription()).append("'").append(" ");
		String updateString = String.format(INSERT_INTO_VALUES, TABLE_ROOM, FIELDS_ROOM, values.toString());
		System.out.println(updateString);
//		dbComm.update(updateString);
	}
	
	public void insertUser(User user) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append("'").append(user.getPassword()).append("'").append(", ");
		values.append("'").append(user.getName()).append("'").append(", ");
		values.append("'").append(user.getType()).append("'").append(" ");
		String updateString = String.format(INSERT_INTO_VALUES, TABLE_USER, FIELDS_USER, values.toString());
		System.out.println(updateString);
//		dbComm.update(updateString);
	}
	
	public void insertIsMemberOf(User user, Group group) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(group.getId()).append(" ");
		String updateString = String.format(INSERT_INTO_VALUES, TABLE_IS_MEMBER_OF, FIELDS_IS_MEMBER_OF, values.toString());
		System.out.println(updateString);
//		dbComm.update(updateString);
	}
	
	public void insertIsOwner(User user, Event event) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(event.getId()).append(" ");
		String updateString = String.format(INSERT_INTO_VALUES, TABLE_IS_OWNER, FIELDS_IS_OWNER, values.toString());
		System.out.println(updateString);
//		dbComm.update(updateString);
	}
	
	// TODO Add insert, update and delete methods
	// NB! Remember relations!
	
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Update update = new Update(TestObjects.getUser00(), dbComm);
		
		System.out.println();
		update.insertAlarm(TestObjects.getAlarm00());
		System.out.println();
		update.insertEvent(TestObjects.getAppointment00());
		System.out.println();
		update.insertGroup(TestObjects.getGroup02());
		System.out.println();
		update.insertNotification(TestObjects.getNotification01());
		System.out.println();
		update.insertRoom(TestObjects.getRoom01());
		System.out.println();
		update.insertUser(TestObjects.getUser01());
		System.out.println();
	}

}
