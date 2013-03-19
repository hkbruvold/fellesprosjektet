package client;

import client.GUI.*;
import data.*;

public class Program {
	private Client client;
	private User currentUser;

	public Program() {
		client = new Client();
		showLogin();
	}
	public void showLogin() {
		new LoginWindow(this);
	}
	public void showMainWindow() {
		if (currentUser != null) {
			new MainWindow(this);
		} else {
			new LoginWindow(this);
		}
	}
	public void quit() {
		System.exit(0);
	}

	public boolean login (String username, String password) {
		Authentication data = new Authentication(username, password);
		Response res = client.send(new Request("login", data));
		currentUser = (User) res.getData();
		return res.status == Response.Status.OK;
	}
	// TODO

	public void registerEvent(Event event) {
		Response res = client.send(new Request("addEvent", event));
	}
	public void editEvent(Event event/*, TODO */) {
		// TODO (remember database!)
	}
	public void removeEvent(Event event) { 
		Response res = client.send(new Request("removeEvent", event));
	}

	public void registerAlarm(Alarm alarm) { 
		Response res = client.send(new Request("addAlarm", alarm));
	}
	public User[] getAllUsers(){
		System.out.println("Fetching users");
		Response res = client.send(new Request("listUsers", null));
		return (User[]) res.getData();
	}
	
	public void addUser(User user){
		Response res = client.send(new Request("newUser", user));
		//return res.status == Response.Status.OK;
	}

	public static void main(String[] args) {
		new Program();
	}

}
