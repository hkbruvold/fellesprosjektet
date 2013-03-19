
package data;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
	public String action;
	private HashMap data;

	public Request (String action, HashMap data) {
		this.action = action;
		this.data = data;
	}

	public HashMap getData () {
		return data;
	}

	public String getAction () {
		return action;
	}
}
