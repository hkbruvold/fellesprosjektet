package server.database;

public enum TableFields {
	ALARM ("alarm",
			new String[] {
			"time",
			"message",
			"username", //p
			"eventID" //p
	}),
	EVENT ("event",
			new String[] {
			"eventID", //p
			"startDateTime",
			"endDateTime",
			"location",
			"description",
			"isMeeting"
	}),
	GROUPS ("groups",
			new String[] {
			"groupID", //p
			"groupname",
			"description"
	}),
	NOTIFICATION("notification",
			new String[] {
			"notificationID", //p
			"description"
	}),
	ROOM("room",
			new String[] {
			"roomID", //p
			"size",
			"description"
	}),
	USER("user",
			new String[] {
			"username", //p
			"password",
			"name",
			"type"
	}),
	IS_MEMBER_OF("isMemberOf",
			new String[] {
			"username", //p
			"groupID" //p
	}),
	IS_OWNER("isOwner",
			new String[] {
			"username", //p
			"eventID" //p
	}),
	IS_PARTICIPANT("isParticipant",
			new String[] {
			"username", //p
			"eventID", //p
			"status"
	}),
	NOTIFICATION_TO("notificationTo",
			new String[] {
			"username", //p
			"notificationID" //p
	}),
	NOTIFICATION_FOR_EVENT("notificationForEvent",
			new String[] {
			"notificationID", //p
			"eventID" //p
	}),
	RESERVED_ROOM("reservedRoom",
			new String[] {
			"eventID", //p
			"roomID" //p
	});
	
	
	private final String tableName;
	private final String[] fields;
	
	TableFields(String tableName, String[] fields) {
		this.tableName = tableName;
		this.fields = fields;
	}
	
	public String getTableName() {
		return tableName;
	}
	public String[] getFields() {
		return fields;
	}
	public String getFieldsString() {
		StringBuilder result = new StringBuilder();
		for (String s : fields) {
			result.append(s).append(", ");
		}
		String resultString = result.toString();
		return resultString.substring(0, resultString.length()-2);
	}
}
