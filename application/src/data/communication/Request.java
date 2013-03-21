
package data.communication;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("serial")
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
//		return action.name() + " : " + data;
		return action + " : " + data;
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

}
