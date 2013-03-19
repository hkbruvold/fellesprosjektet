package client;

import client.GUI.*;
import data.*;

import java.util.ArrayList;
import java.util.HashMap;

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
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("username", username);
		values.put("password", password);
		Response res = client.send(new Request("login", values));
		return res.status == Response.Status.OK;
	}
	// TODO

	public void registerEvent(Event event) {
		HashMap<String, Event> values = new HashMap<String, Event>();
		values.put("event", event);
		Response res = client.send(new Request("addEvent", values));
	}
	public void editEvent(Event event/*, TODO */) {
		// TODO (remember database!)
	}
	public void removeEvent(Event event) { 
		HashMap<String, Event> values = new HashMap<String, Event>();
		values.put("event", event);
		Response res = client.send(new Request("removeEvent", values));
	}

	public void registerAlarm(Alarm alarm) { 
		HashMap<String, Alarm> values = new HashMap<String, Alarm>();
		values.put("alarm", alarm);
		Response res = client.send(new Request("addAlarm", values));
	}
	public User[] getAllUsers(){
		System.out.println("Fetching users");
		Response res = client.send(new Request("listUsers", null));
		return (User[]) res.getData();
	}
	
	public void addUser(User user){
		HashMap<String,User> values = new HashMap<String, User>();
		values.put("user", user);
		Response res = client.send(new Request("newUser", values));
		//return res.status == Response.Status.OK;
	}

	public static void main(String[] args) {
		new Program();
	}

}
