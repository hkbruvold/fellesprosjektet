package data;

import java.io.Serializable;

public class Authentication implements Serializable {
	private String user;
	private String password;
	private String action;

	public Authentication(String user, String password, String action) {
		this.user = user;
		this.password = password;
		this.action = action;
	}
	public Authentication(){

	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

}
