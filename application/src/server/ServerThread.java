package server;

import data.Alarm;
import data.Event;
import data.Group;
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
	private String dbUsername = "root";
	private char[] dbPassword = "skip".toCharArray();

	public ServerThread (Socket socket) {
		super();
		this.socket = socket;
		dbConn = new DatabaseConnection(serverUrl, dbUsername, dbPassword);
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
		String action = req.getAction();
		Serializable data = req.getData();
		Update update = new Update(null, dbComm);
		Query query = new Query(null, dbComm);
		System.out.println(action);

		switch (action) {
		case "login":
			User clientUser = (User) data;
			User fetchedUser = query.queryUser(clientUser.getUsername());
			boolean correctUsername = fetchedUser.getUsername().equals(clientUser.getUsername());
			boolean correctPassword = fetchedUser.getPassword().equals(clientUser.getPassword());
			if (correctUsername && correctPassword) {
				send(new Response(Response.Status.OK, fetchedUser));
			} else{
				send(new Response(Response.Status.FAILED, null));
			}
			break;
		case "addEvent":
			Event event = (Event) data;
			update.insertEvent(event);
			send(new Response(Response.Status.OK, null)); 
			break;
		case "addAlarm":
			Alarm alarm = (Alarm) data;
			update.insertAlarm(alarm);
			send(new Response(Response.Status.OK, null)); 
			break;
		case "listUsers":
			ArrayList<User> userList = query.queryUsers();
			Group group = new Group(-1, "resutl", "dummy");
			group.addMembers(userList);
			send(new Response(Response.Status.OK, group));
			break;
		case "newUser":
			User user = (User) data;
			update.insertUser(user);
			send(new Response(Response.Status.OK, null));
			break;
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
