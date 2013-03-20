package server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import data.*;
import data.communication.*;
import data.communication.Request.Action;
import client.GUI.DataList;
import server.database.*;

public class ServerThread extends Thread {
	private static final String HOST = "localhost";
	private static final String PORT = "3306";
	private static final String DATABASE = "calendarDatabase";
	private static final String URI = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "skip";

	private static final int FAILED_INSERT = -1;
	
	private Socket socket = null;
	private OutputStream out;
	private InputStream in;
	private DatabaseConnection dbConn;
	private DatabaseCommunication dbComm;

	public ServerThread (Socket socket) {
		super();
		this.socket = socket;
		dbConn = new DatabaseConnection(URI, USERNAME, PASSWORD.toCharArray());
		dbComm = new DatabaseCommunication(dbConn);
	}

	@Override
	public void run() {
		try {
			out = socket.getOutputStream();
			in = socket.getInputStream();
			Request req = null;
			while (req == null) {
				req = (Request) XMLTranslator.receiveRequest(in);
			}
			parseRequest(req);
			closeSocket();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void parseRequest (Request req) {
		Action action = req.getAction();
		Serializable data = req.getData();
		User currentUser;
		if (data instanceof User) {
			currentUser = (User) data;
		} else {
			currentUser = null;
		}
		Update update = new Update(currentUser, dbComm);
		Query query = new Query(currentUser, dbComm);
		System.out.println(action);

		switch (action) {
		case LOGIN:
			Authentication auth = (Authentication) data;
			User fetchedUser = query.queryUser(auth.getUsername());
			if (fetchedUser == null) {
				send(new Response(Response.Status.FAILED, null));
				break;
			}
			boolean correctUsername = fetchedUser.getUsername().equals(auth.getUsername());
			boolean correctPassword = fetchedUser.getPassword().equals(auth.getPassword());
			if (correctUsername && correctPassword) {
				send(new Response(Response.Status.OK, fetchedUser));
			} else{
				send(new Response(Response.Status.FAILED, null));
			}
			break;
		case ADD_EVENT:
			Event event = (Event) data;
			int eventId = update.insertEvent(event);
			if (eventId == FAILED_INSERT) {
				send(new Response(Response.Status.FAILED, null));
			} else {
				send(new Response(Response.Status.OK, eventId)); 
			}
			// TODO what if relations fails?
			break;
		case ADD_ALARM:
			Alarm alarm = (Alarm) data;
			int alarmResult = update.insertAlarm(alarm);
			if (alarmResult == FAILED_INSERT) {
				send(new Response(Response.Status.FAILED, alarmResult));
			} else {
				send(new Response(Response.Status.OK, alarmResult)); 
			}
			// TODO what if relations fails?
			break;
		case LIST_USERS:
			ArrayList<User> userList = query.queryUsers();
			Group group = new Group(0, "result", "dummy");
			group.addMembers(userList);
			send(new Response(Response.Status.OK, group));
			// TODO what if it fails?
			break;
		case LIST_ROOMS:
			ArrayList<Room> roomList = query.queryRooms();
			DataList roomDL = new DataList();
			for (Room room : roomList) {
				roomDL.add(room);
			}
			send(new Response(Response.Status.OK, roomDL));
			// TODO what if it fails?
			break;
		case GET_ALL_EVENTS:
			query = new Query((User)data, dbComm); // Needed to get fetch alarms to the user
			ArrayList<Event> eventList = query.queryEvents();
			DataList eventDL = new DataList();
			for (Event event2 : eventList) {
				eventDL.add(event2);
			}
			send(new Response(Response.Status.OK, eventDL));
			break;
		case LIST_NOTIFICATIONS:
			ArrayList<Notification> notificationList = query.queryNotificationsTo(((User) data).getUsername());
			DataList notificationDL = new DataList();
			for (Notification notification: notificationList) {
				notificationDL.add(notification);
			}
			send(new Response(Response.Status.OK, notificationDL));
			break;
		case LIST_GROUPS:
			ArrayList<Group> groupList = query.queryGroups();
			DataList groupDL = new DataList();
			for (Group group1 : groupList) {
				groupDL.add(group1);
			}
			send(new Response(Response.Status.OK, groupDL));
			break;
		case NEW_USER:
			User user = (User) data;
			update.insertUser(user);
			send(new Response(Response.Status.OK, null));
			// TODO what if it fails?
			break;
		case UPDATE_STATUS:
			MeetingReply meetingReply = (MeetingReply) data;
			update.updateIsParticipant(meetingReply.getUser(), meetingReply.getMeeting(), meetingReply.getStatus());
			break;
		case REQUEST_CHANGES:
			CurrentVersion version = (CurrentVersion) data;
			System.out.println(version);
			String[] tableNames = new String[]{"User", "Event"}; // TODO request database
			String[] identifiers = new String[]{"alice, bob", "6, 496"}; // TODO request database
			send(new Response(Response.Status.OK, new ChangeData(2L, tableNames, identifiers)));
			// TODO what if it fails?
			break;
		case "removeEvent":
			Event eventToRemove = (Event) data;
			update = new Update(currentUser, dbComm);
			update.deleteEvent(eventToRemove);
		default:
			System.out.println("case default");
			send(new Response(Response.Status.FAILED, null));
			break;
		}
	}
	public void send(Response res) {
		XMLTranslator.send(res, out);
	}

	public void closeSocket () {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
