
package data;

import java.io.Serializable;
import java.util.HashMap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Request implements Serializable {
	@Element
	public String action;
	@Element(required=false)
	private Serializable data;

	public Request() {
	}
	public Request(String action, Serializable data) {
		this.action = action;
		this.data = data;
	}
	
	public String toString() {
		return action + " : " + data;
	}

	public Serializable getData () {
		return data;
	}
	public void setData(Serializable data) {
		this.data = data;
	}
	public String getAction () {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
