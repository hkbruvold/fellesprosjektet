package client;

import client.GUI.*;
import data.*;
import data.communication.*;
import data.communication.Request.Action;

import java.io.Serializable;
import java.util.ArrayList;

public class Program {
	private Client client;
	public User currentUser;
	private CurrentVersion version = new CurrentVersion(1L);

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
	
	public ChangeData requestChanges() {
		Response res = client.send(new Request(Action.REQUEST_CHANGES, version));
		ChangeData change = null;
		if (res.getData() != null && res.getData() instanceof ChangeData) {
			change = (ChangeData) res.getData();
		}
		return change;
	}

	public boolean login (Authentication auth) {
		Response res = client.send(new Request(Action.LOGIN, auth));
		currentUser = (User) res.getData();
		return res.status == Response.Status.OK;
	}
	// TODO

	public int registerEvent(Event event) {
		Response res = client.send(new Request(Action.ADD_EVENT, event));
		int id = (Integer) res.getData();
		return id;
	}
	public void editEvent(Event event/*, TODO */) {
		// TODO (remember database!)
	}
	public void removeEvent(Event event) { 
		Response res = client.send(new Request(Action.REMOVE_EVENT, event));
	}
	public void updateStatus(MeetingReply meetingReply){
		Response res = client.send(new Request(Action.UPDATE_STATUS, meetingReply));
		
	}
	public ArrayList<Event> getAllEvents(){
		System.out.println("Fetching events");
		Response res = client.send(new Request(Action.GET_ALL_EVENTS, currentUser));
		ArrayList<Event> result = new ArrayList<Event>();
		if (res.getData() != null && res.getData() instanceof DataList) {
			DataList dl = (DataList) res.getData();
			for (Serializable data : dl.getData()) {
				result.add((Event) data);
			}
		}
		return result;
	}

	public int registerAlarm(Alarm alarm) { 
		Response res = client.send(new Request(Action.ADD_ALARM, alarm));
		int result = (Integer) res.getData();
		return result;
	}
	public User[] getAllUsers(){
		System.out.println("Fetching users");
		Response res = client.send(new Request(Action.LIST_USERS, null));
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
		Response res = client.send(new Request(Action.LIST_GROUPS, null));
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
		System.out.println("Fetching rooms");
		Response res = client.send(new Request(Action.LIST_ROOMS, null));
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
	public ArrayList<Notification> getAllNotifications(){
		System.out.println("Fetching notifications");
		Response res = client.send(new Request(Action.LIST_NOTIFICATIONS, currentUser));
		if (res.getData() != null && res.getData() instanceof DataList) {
			ArrayList<Notification> result = new ArrayList<Notification>();
			DataList dl = (DataList) res.getData();
			for (Serializable data : dl.getData()) {
				result.add((Notification) data);
			}
			return result;
		} else {
			return null;
		}
	}
	
	public void addUser(User user){
		// TODO remember to make sure username is available!
		Response res = client.send(new Request(Action.NEW_USER, user));
	}

	public static void main(String[] args) {
		new Program();
	}

}
