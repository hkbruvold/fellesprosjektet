package client;

import client.GUI.*;
import data.*;

import java.util.ArrayList;

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
			new MainWindow(this, currentUser);
		} else {
			new LoginWindow(this);
		}
	}
	public void quit() {
		System.exit(0);
	}

	public boolean login (Authentication auth) {
		Response res = client.send(new Request("login", auth));
		currentUser = (User) res.getData();
		return res.status == Response.Status.OK;
	}
	// TODO

	public int registerEvent(Event event) {
		Response res = client.send(new Request("addEvent", event));
		int id = (Integer) res.getData();
		return id;
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
		ArrayList<User> users = null;
		if (res.status == Response.Status.OK) {
			users = ((Group)res.getData()).getMembers();
		}
		User[] userArray = new User[users.size()];
		int i = 0;
		for (User user : users) {
			userArray[i++] = user;
		}
		return userArray;
	}
	
	public void addUser(User user){
		Response res = client.send(new Request("newUser", user));
	}

	public static void main(String[] args) {
		new Program();
	}

}
