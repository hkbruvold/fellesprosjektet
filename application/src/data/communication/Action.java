package data.communication;

public interface Action {

	public enum MiscAction implements Action {
		HANDSHAKE,
		LOGIN,
		UPDATE_STATUS,
		REQUEST_CHANGES,
	}
	public enum InsertAction implements Action {
		ADD_USER,
		ADD_EVENT,
		ADD_ALARM,
	}
	public enum UpdateAction implements Action {
		UPDATE_EVENT,
		REMOVE_EVENT, 
	}
	public enum QueryAction implements Action {
		LIST_EVENTS_FOR_USER,
		LIST_GROUPS,
		LIST_NOTIFICATIONS,
		LIST_ROOMS,
		LIST_USERS,
	}
}
