package server;

import data.Alarm;
import data.Event;
import data.Request;
import data.Response;
import data.User;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

import javax.xml.ws.soap.AddressingFeature.Responses;

import server.database.DatabaseCommunication;
import server.database.DatabaseConnection;
import server.database.Query;
import server.database.Update;

public class ServerThread extends Thread {
	private Socket socket = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
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
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			Request req = null;

			while (req == null) {
				req = (Request) in.readObject();
			}

			parseRequest(req);
			closeSocket();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println(e);
		}
	}

	public void parseRequest (Request req) {
		String action = req.getAction();
		Update update = new Update(null, dbComm);
		Query query = new Query(null, dbComm);

		switch (action) {
		case "login":
		default:
			System.out.println(action);
			String username = req.getData().get("username").toString();
			String password = req.getData().get("password").toString();
			System.out.println("Username: " + username + " " + "Password: " + password);
			User fethcedUser = query.queryUser(username);
			if(fethcedUser.getName().equals(username) && fethcedUser.getPassword().equals(password)){
				send(new Response(Response.Status.OK, null));
			} else{
				send(new Response(Response.Status.FAILED, null));
			}
		case "addEvent":
			System.out.println(action);
			Event event = (Event) req.getData().get("event");
			update.insertEvent(event); 
		case "addAlarm":
			Alarm alarm = (Alarm) req.getData().get("alarm");
			update.insertAlarm(alarm);
		case "listUsers":
			HashMap userHashMap = new HashMap();
			ArrayList<User> userList = query.queryUsers();
			userHashMap.put("users", userList);
			new Response(Response.Status.OK, userHashMap);
		case "newUser":
			User user = (User) req.getData().get("user");
			update.insertUser(user);
			new Response(Response.Status.OK, null);
		}
		}
	public void send (Response res) {
		try {
			out.writeObject(res);
		} catch (IOException e) {
			System.err.println(e);
		}
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
