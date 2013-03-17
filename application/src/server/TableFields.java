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
			
	}),
	NOTIFICATION("notification",
			new String[] {
			
	}),
	ROOM("room",
			new String[] {
			
	}),
	USER("user",
			new String[] {
			
	}),
	IS_MEMBER_OF("isMemberOf",
			new String[] {
			
	}),
	IS_PARTICIPANT("isParticipant",
			new String[] {
			
	}),
	NOTIFICATION_TO("notificationTo",
			new String[] {
			
	}),
	RESERVED_ROOM("reservedRoom",
			new String[] {
			
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
