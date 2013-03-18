package server.database;

import data.*;

import temp.TestObjects;

public class Update {
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
	
	private static final String INSERT_INTO_VALUES = "INSERT INTO %s (%s) VALUES %s"; // "tableName", "fields", "values"
	private static final String DELETE_FROM_WHERE = "DELETE FROM %s WHERE %s"; // "tableName", "condition"
	private static final String UPDATE_SET_WHERE = "UPDATE %s SET %s WHERE %s"; // "tableName", "field1=value1, field2=value2 ...", "condition"
	
	private User currentUser;
	private DatabaseCommunication dbComm;

	public Update(User currentUser, DatabaseCommunication dbComm) {
		this.currentUser = currentUser;
		this.dbComm = dbComm;
	}

	// TODO add insert, update and delete methods
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Update update = new Update(TestObjects.getUser00(), dbComm);
	}

}
