package data;

import java.io.Serializable;
import java.util.HashMap;

public class Response implements Serializable {
	public Status status;
	private Serializable data;

	public Response (Status status, Serializable data) {
		this.status = status;
		this.data = data;
	}

	public Serializable getData () {
		return data;
	}

	public Status getStatus () {
		return status;
	}

	public enum Status {
		OK, FAILED
	}
}