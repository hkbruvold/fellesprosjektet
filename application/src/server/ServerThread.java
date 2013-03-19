package server;

import data.Alarm;
import data.Event;
import data.Request;
import data.Response;
import data.User;
import data.XMLTranslator;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

import server.database.DatabaseCommunication;
import server.database.DatabaseConnection;
import server.database.Query;
import server.database.Update;

public class ServerThread extends Thread {
	private Socket socket = null;
	private OutputStream out;
	private InputStream in;
	private DatabaseConnection dbConn;
	private DatabaseCommunication dbComm;
	private String URI = "localhost";
	private String port = "3306";
	private String databaseName = "calendarDatabase";
	private String serverUrl = "jdbc:mysql://" + URI + ":" + port + "/" + databaseName;
	private String username = "root";
	private char[] password = "skip".toCharArray();

	public ServerThread (Socket socket) {
		super();
		this.socket = socket;
		dbConn = new DatabaseConnection(serverUrl, username, password);
		dbComm = new DatabaseCommunication(dbConn);
	}

	@Override
	public void run() {
		try {
			out = socket.getOutputStream();
			in = socket.getInputStream();
			Request req = null;
			while (req == null) {
				req = (Request) XMLTranslator.receive(in);
			}
			parseRequest(req);
			closeSocket();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void parseRequest (Request req) {
		String action = req.getAction();
		Update update = new Update(null, dbComm);
		Query query = new Query(null, dbComm);
		System.out.println(action);

		switch (action) {
		case "login":
			User clientUser = (User) req.getData();
			User fetchedUser = query.queryUser(username);
			boolean correctUsername = fetchedUser.getUsername().equals(clientUser.getUsername());
			boolean correctPassword = fetchedUser.getPassword().equals(clientUser.getPassword());
			if (correctUsername && correctPassword) {
				send(new Response(Response.Status.OK, fetchedUser));
			} else{
				send(new Response(Response.Status.FAILED, null));
			}
			break;
//		case "addEvent":
//			System.out.println(action);
//			Event event = (Event) req.getData().get("event");
//			update.insertEvent(event); 
//			break;
//		case "addAlarm":
//			Alarm alarm = (Alarm) req.getData().get("alarm");
//			update.insertAlarm(alarm);
//			break;
//		case "listUsers":
//			HashMap<String, ArrayList<User>> userHashMap = new HashMap<String, ArrayList<User>>();
//			ArrayList<User> userList = query.queryUsers();
//			userHashMap.put("users", userList);
//			new Response(Response.Status.OK, userHashMap);
//			break;
//		case "newUser":
//			User user = (User) req.getData().get("user");
//			update.insertUser(user);
//			new Response(Response.Status.OK, null);
//			break;
		default:
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
