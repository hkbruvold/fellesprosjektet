package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import server.database.*;
import data.*;
import data.communication.Action;
import data.communication.Action.*;
import data.communication.*;

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
		Update update = new Update(dbComm);
		Query query = new Query(currentUser, dbComm);
		System.out.println(action);

		if (action instanceof MiscAction) {
			MiscAction miscAction = (MiscAction) action;
			performMisc(data, update, query, miscAction); 
		} else if (action instanceof InsertAction) {
			InsertAction insertAction = (InsertAction) action;
			performInsert(data, update, insertAction);
		} else if (action instanceof UpdateAction) {
			UpdateAction updateAction = (UpdateAction) action;
			performUpdate(data, currentUser, updateAction);
		} else if (action instanceof QueryAction) {
			QueryAction queryAction = (QueryAction) action;
			performQuery(data, query, queryAction);
		}
	}

	private void performMisc(Serializable data, Update update, Query query, MiscAction miscAction) {
		switch (miscAction) {
		case HANDSHAKE:
			send(new Response(Response.Status.OK, null));
			break;
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
		case UPDATE_STATUS:
			MeetingReply meetingReply = (MeetingReply) data;
			update.updateIsParticipant(meetingReply.getUser(), meetingReply.getMeeting(), meetingReply.getStatus());
			break;
		case REQUEST_CHANGES:
			CurrentVersion version = (CurrentVersion) data;
			long clientVersion = version.getVersionNumber();
			long serverVersion = update.getVersion();
			if (clientVersion == serverVersion) {
				send(new Response(Response.Status.OK, new ChangeData(serverVersion, new String[]{""}, new String[]{""})));
			} else {
				send(new Response(Response.Status.OK, update.getChanges(clientVersion)));
				// todo what if it fails?
			}
			break;
		default:
			System.out.println("case default");
			send(new Response(Response.Status.FAILED, null));
			break;
		}
	}

	private void performInsert(Serializable data, Update update, InsertAction insertAction) {
		switch (insertAction) {
		case ADD_EVENT:
			Event event = (Event) data;
			int eventId = update.insertEvent(event);
			if (eventId == FAILED_INSERT) {
				send(new Response(Response.Status.FAILED, null));
			} else {
				send(new Response(Response.Status.OK, eventId)); 
			}
			// todo what if relations fails?
			break;
		case ADD_ALARM:
			Alarm alarm = (Alarm) data;
			int alarmResult = update.insertAlarm(alarm);
			if (alarmResult == FAILED_INSERT) {
				send(new Response(Response.Status.FAILED, alarmResult));
			} else {
				send(new Response(Response.Status.OK, alarmResult)); 
			}
			// todo what if relations fails?
			break;
		case ADD_USER:
			User user = (User) data;
			update.insertUser(user);
			send(new Response(Response.Status.OK, null));
			// todo what if it fails?
			break;
		default:
			System.out.println("case default");
			send(new Response(Response.Status.FAILED, null));
			break;
		}
	}

	private void performUpdate(Serializable data, User currentUser, UpdateAction updateAction) {
		Update update;
		switch (updateAction) {
		case UPDATE_EVENT:
			Event eventToUpdate = (Event) data;
			update = new Update(dbComm);
			update.updateEvent(eventToUpdate);
			send(new Response(Response.Status.OK, null));
			break;
		case REMOVE_EVENT:
			Event eventToRemove = (Event) data;
			update = new Update(dbComm);
			update.deleteEvent(eventToRemove);
			send(new Response(Response.Status.OK, null));
			break;
		default:
			System.out.println("case default");
			send(new Response(Response.Status.FAILED, null));
			break;
		}
	}

	private void performQuery(Serializable data, Query query, QueryAction queryAction) {
		switch (queryAction) {
		case LIST_USERS:
			ArrayList<User> userList = query.queryUsers();
			Group group = new Group(0, "result", "dummy");
			group.addMembers(userList);
			send(new Response(Response.Status.OK, group));
			// todo what if it fails?
			break;
		case LIST_ROOMS:
			ArrayList<Room> roomList = query.queryRooms();
			DataList roomDL = new DataList();
			for (Room room : roomList) {
				roomDL.add(room);
			}
			send(new Response(Response.Status.OK, roomDL));
			// todo what if it fails?
			break;
		case LIST_EVENTS_FOR_USER:
			query = new Query((User)data, dbComm); // Needed to get fetch alarms to the user
			ArrayList<Event> eventList = query.queryEvents((User) data);
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
