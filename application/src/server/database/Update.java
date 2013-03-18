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
	
	private static final String INSERT_INTO_VALUES = "INSERT INTO %s (%s) VALUES %s"; // "tableName", "fields", "values"
	private static final String DELETE_FROM_WHERE = "DELETE FROM %s WHERE %s"; // "tableName", "condition"
	private static final String UPDATE_SET_WHERE = "UPDATE %s SET %s WHERE %s"; // "tableName", "field1=value1, field2=value2 ...", "condition"
	
	private static final String BIT_FALSE = "0";
	private static final String BIT_TRUE = "1";
	
	private static final String DEFAULT_NEW_ID = "0";
	
	private static final String PARTICIPANT_STATUS_INVITED = "0";
	private static final String PARTICIPANT_STATUS_ACCEPTED = "1";
	private static final String PARTICIPANT_STATUS_DECLINED = "2";
	
// 	Please ignore! I'm using this as a shortcut to open classes. Query TableFields
	
	private User currentUser;
	private DatabaseCommunication dbComm;

	public Update(User currentUser, DatabaseCommunication dbComm) {
		this.currentUser = currentUser;
		this.dbComm = dbComm;
	}

	public void insertAlarm(Alarm alarm) {
		StringBuilder values = new StringBuilder();
		values.append(alarm.getTimeBefore()).append(", ");
		values.append(alarm.getMessage()).append(", ");
		values.append(alarm.getOwner().getUsername()).append(", ");
		values.append(alarm.getEvent().getId()).append(" ");
		dbComm.update(String.format(INSERT_INTO_VALUES, TABLE_ALARM, FIELDS_ALARM, values.toString()));
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
		dbComm.update(String.format(INSERT_INTO_VALUES, TABLE_EVENT, FIELDS_EVENT, values.toString()));
		if (event instanceof Meeting) {
			// TODO add relations!
		}
	}
	
	
	// TODO Add insert, update and delete methods
	// NB! Remember relations!
	
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Update update = new Update(TestObjects.getUser00(), dbComm);
	}

}
