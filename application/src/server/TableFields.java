package server;

public enum TableFields {
	ALARM ("alarm",
			new String[] {
			"alarmID",
			"time",
			"message",
			"username",
			"eventID"
	}),
	EVENT ("event",
			new String[] {
			"eventID",
			"startDateTime",
			"endDateTime",
			"location",
			"description",
			"owner",
			"isMeeting"
	}),
	GROUPS ("groups",
			new String[] {
			"groupID",
			"groupname",
			"description"
	}),
	NOTIFICATION("notification",
			new String[] {
			"notificationID",
			"description",
			"eventID"
	}),
	ROOM("room",
			new String[] {
			"roomID",
			"size",
			"description"
	}),
	USER("user",
			new String[] {
			"username",
			"password",
			"name",
			"type"
	}),
	IS_MEMBER_OF("isMemberOf",
			new String[] {
			"username",
			"groupID"
	}),
	IS_PARTICIPANT("isParticipant",
			new String[] {
			"username",
			"eventID",
			"status"
	}),
	NOTIFICATION_TO("notificationTo",
			new String[] {
			"username",
			"notification"
	}),
	RESERVED_ROOM("reservedRoom",
			new String[] {
			"eventID",
			"roomID"
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
