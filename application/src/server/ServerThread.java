package server;

import data.Appointment;
import data.Authentication;
import data.Alarm;
import data.Event;
import data.Group;
import data.Meeting;
import data.Request;
import data.Response;
import data.User;
import data.XMLTranslator;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import server.database.DatabaseCommunication;
import server.database.DatabaseConnection;
import server.database.Query;
import server.database.Update;

public class ServerThread extends Thread {
    public static final String HOST = "localhost";
	public static final String PORT = "3306";
	public static final String DATABASE = "calendarDatabase";
	public static final String URI = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
	public static final String USERNAME = "root";
	public static final String PASSWORD = "skip";
	
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
		String action = req.getAction();
		Serializable data = req.getData();
		Update update = new Update(null, dbComm);
		Query query = new Query(null, dbComm);
		System.out.println(action);

		switch (action) {
		case "login":
			Authentication auth = (Authentication) data;
			User fetchedUser = query.queryUser(auth.getUsername());
			boolean correctUsername = fetchedUser.getUsername().equals(auth.getUsername());
			boolean correctPassword = fetchedUser.getPassword().equals(auth.getPassword());
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
			// TODO what if it fails?
			break;
		case "addAlarm":
			Alarm alarm = (Alarm) data;
			update.insertAlarm(alarm);
			send(new Response(Response.Status.OK, null)); 
			// TODO what if it fails?
			break;
		case "listUsers":
			ArrayList<User> userList = query.queryUsers();
			Group group = new Group(-1, "resutl", "dummy");
			group.addMembers(userList);
			send(new Response(Response.Status.OK, group));
			// TODO what if it fails?
			break;
		case "newUser":
			User user = (User) data;
			update.insertUser(user);
			send(new Response(Response.Status.OK, null));
			// TODO what if it fails?
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
