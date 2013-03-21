package client;

import client.GUI.*;
import data.*;
import data.communication.*;
import data.communication.Action.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Program {
	private Client client;
	public User currentUser;
	private CurrentVersion version = new CurrentVersion(1L);
	private HashMap<String, HashMap<Integer, Event>> eventList; // HashMap<username, HashMap<eventID, Event>>
	private HashMap<String, User> userList; // HashMap<username, User>

	public Program() {
		client = new Client();
		showLogin();
	}
	
	private void initData() {
		User[] users = getAllUsers();
		userList = new HashMap<String, User>();
		eventList = new HashMap<String, HashMap<Integer, Event>>();
		for (User user : users) {
			userList.put(user.getUsername(), user);
			ArrayList<Event> events = getEventsForUser(user);
			HashMap<Integer, Event> eventMap = new HashMap<Integer, Event>();
			for (Event event : events) {
				eventMap.put(event.getId(), event);
			}
			eventList.put(user.getUsername(), eventMap);
		}
	}
	
	public void showLogin() {
		new LoginWindow(this);
	}
	
	public void showMainWindow() {
		if (currentUser != null) {
			initData();
			new MainWindow(this, currentUser);
		} else {
			new LoginWindow(this);
		}
	}
	public void quit() {
		System.exit(0);
	}
	
	
	public boolean login(Authentication auth) {
		Response res = client.send(new Request(MiscAction.LOGIN, auth));
		currentUser = (User) res.getData();
		return res.status == Response.Status.OK;
	}
	public void updateStatus(MeetingReply meetingReply) {
		Response res = client.send(new Request(MiscAction.UPDATE_STATUS, meetingReply));
	}
	public ChangeData requestChanges() {
		Response res = client.send(new Request(MiscAction.REQUEST_CHANGES, version));
		ChangeData change = null;
		if (res.getData() != null && res.getData() instanceof ChangeData) {
			change = (ChangeData) res.getData();
		}
		return change;
	}

	
	public void addUser(User user){
		// TODO remember to make sure username is available!
		Response res = client.send(new Request(InsertAction.ADD_USER, user));
	}
	public int registerEvent(Event event) {
		Response res = client.send(new Request(InsertAction.ADD_EVENT, event));
		int id = (Integer) res.getData();
		return id;
	}
	public int registerAlarm(Alarm alarm) { 
		Response res = client.send(new Request(InsertAction.ADD_ALARM, alarm));
		int result = (Integer) res.getData();
		return result;
	}
	
	
	public void editEvent(Event event/*, TODO */) {
		// TODO (remember database!)
	}
	public void removeEvent(Event event) { 
		Response res = client.send(new Request(UpdateAction.REMOVE_EVENT, event));
	}
	
	
	public ArrayList<Event> getEventsForUser(User user){
		System.out.println("Fetching events");
		Response res = client.send(new Request(QueryAction.LIST_EVENTS_FOR_USER, user));
		ArrayList<Event> result = new ArrayList<Event>();
		if (res.getData() != null && res.getData() instanceof DataList) {
			DataList dl = (DataList) res.getData();
			for (Serializable data : dl.getData()) {
				result.add((Event) data);
			}
		}
		return result;
	}
	public Group[] getAllGroups(){
		System.out.println("Fetching groups");
		Response res = client.send(new Request(QueryAction.LIST_GROUPS, null));
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
	public ArrayList<Notification> getAllNotifications(){
		System.out.println("Fetching notifications");
		Response res = client.send(new Request(QueryAction.LIST_NOTIFICATIONS, currentUser));
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
	public ArrayList<Room> getAllRooms() {
		System.out.println("Fetching rooms");
		Response res = client.send(new Request(QueryAction.LIST_ROOMS, null));
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
	public User[] getAllUsers() {
		System.out.println("Fetching users");
		Response res = client.send(new Request(QueryAction.LIST_USERS, null));
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
	
	
	public static void main(String[] args) {
		new Program();
	}
	
	public HashMap<String, HashMap<Integer, Event>> getEventList() {
		return eventList;
	}
	
	public HashMap<String, User> getUserList() {
		return userList;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}

}
