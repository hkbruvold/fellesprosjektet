package client;

import client.GUI.*;
import data.*;

import java.io.Serializable;
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

	public int registerAlarm(Alarm alarm) { 
		Response res = client.send(new Request("addAlarm", alarm));
		int result = (Integer) res.getData();
		return result;
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
	public Group[] getAllGroups(){
		System.out.println("Fetching groups");
		Response res = client.send(new Request("listGroups", null));
		ArrayList<Group> result = new ArrayList<Group>();
		if (res.getData() != null && res.getData() instanceof DataList) {
			DataList dl = (DataList) res.getData();
			for (Serializable data : dl.getData()) {
				result.add((Group) data);
			}
		}
		Group[] groupArray = new Group[result.size()];
		int i = 0;
		for (Group group : result){
			groupArray[i++] = group;
		}
		return groupArray;
	}
	public ArrayList<Room> getAllRooms() {
		Response res = client.send(new Request("listRooms", null));
		if (res.getData() != null && res.getData() instanceof DataList) {
			ArrayList<Room> result = new ArrayList<Room>();
			DataList dl = (DataList) res.getData();
			for (Serializable data : dl.getData()) {
				result.add((Room) data);
			}
			return result;
		} else {
			return null;
		}
	}
	
	public void addUser(User user){
		// TODO remember to make sure username is available!
		Response res = client.send(new Request("newUser", user));
	}

	public static void main(String[] args) {
		new Program();
	}

}
