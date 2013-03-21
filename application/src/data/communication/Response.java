package data.communication;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("serial")
@Root
public class Response implements Serializable {
	@Element
	public Status status;
	@Element(required=false)
	private Serializable data;

	public Response() {
	}
	public Response (Status status, Serializable data) {
		this.status = status;
		this.data = data;
	}

	public Serializable getData () {
		return data;
	}
	public void setData(Serializable data) {
		this.data = data;
	}
	public Status getStatus () {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		OK, 
		FAILED, 
		CONNECTION_PROBLEM;
	}
}