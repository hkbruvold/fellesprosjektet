
package data;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
	public String action;
	private Serializable data;

	public Request (String action, Serializable data) {
		this.action = action;
		this.data = data;
	}

	public Serializable getData () {
		return data;
	}

	public String getAction () {
		return action;
	}
}
