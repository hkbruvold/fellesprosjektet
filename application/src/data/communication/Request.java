
package data.communication;

import java.io.Serializable;
import java.util.HashMap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Request implements Serializable {
	@Element
	public Action action;
	@Element(required=false)
	private Serializable data;

	public Request() {
	}
	public Request(Action action, Serializable data) {
		this.action = action;
		this.data = data;
	}
	
	public String toString() {
		return action.name() + " : " + data;
	}

	public Serializable getData () {
		return data;
	}
	public void setData(Serializable data) {
		this.data = data;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	
	public enum Action {
		LOGIN,
		ADD_EVENT,
		ADD_ALARM,
		LIST_USERS,
		LIST_ROOMS,
		GET_ALL_EVENTS,
		LIST_NOTIFICATIONS,
		LIST_GROUPS,
		NEW_USER,
		UPDATE_STATUS,
		REMOVE_EVENT,
		REQUEST_CHANGES;
	}
}
