package data.communication;

public interface Action {

	public enum MiscAction implements Action {
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
		REMOVE_EVENT,
	}
	public enum QueryAction implements Action {
		LIST_ALL_EVENTS,
		LIST_GROUPS,
		LIST_NOTIFICATIONS,
		LIST_ROOMS,
		LIST_USERS,
	}
}
