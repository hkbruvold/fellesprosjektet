package server;

import data.*;

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
	private ObjectOutputStream out;
	private ObjectInputStream in;
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
		System.out.println(action);

		switch (action) {
		case "login":
			Authentication auth = (Authentication) req.getData();
			String username = auth.getUser();
			String password = auth.getPassword();
			User fetchedUser = query.queryUser(username);
			
			if(fetchedUser.getUsername().equals(username) && fetchedUser.getPassword().equals(password)){
				send(new Response(Response.Status.OK, fetchedUser));
			} else{
				send(new Response(Response.Status.FAILED, null));
			}
			break;
		case "addEvent":
			Event event = (Event) req.getData();
			update.insertEvent(event);
			// TODO: what if insert fails?
			send(new Response(Response.Status.OK, null));
			break;
		case "addAlarm":
			Alarm alarm = (Alarm) req.getData();
			update.insertAlarm(alarm);
			// TODO: what if insert fails?
			send(new Response(Response.Status.OK, null));
			break;
		case "listUsers":
			ArrayList<User> userList = query.queryUsers();
			send(new Response(Response.Status.OK, userList));
			break;
		case "newUser":
			User user = (User) req.getData();
			update.insertUser(user);
			// TODO: what if insert fails?
			send(new Response(Response.Status.OK, null));
			break;
		default:
			break;
		}
	}
	public void send(Response res) {
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
