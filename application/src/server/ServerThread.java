package server;

import data.Event;
import data.Request;
import data.Response;
import data.User;

import java.net.*;
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
	private String URI;
	private String port;
	private String databaseName;
	private String serverUrl = "jdbc:mysql://" + URI + ":" + port + "/" + databaseName;
	private String username;
	private char[] password;

	public ServerThread (Socket socket) {
		super();
		this.socket = socket;
		//dbConn = new DatabaseConnection(serverUrl, username, password);
		dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
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

		switch (action) {
		case "login":
		default:
			System.out.println(action);
			String username = req.getData().get("username").toString();
			String password = req.getData().get("password").toString();
			System.out.println("Username: " + username + " " + "Password: " + password);
			Query query = new Query(null, dbComm);
			User fethcedUser = query.queryUser(username);
			if(fethcedUser.getName().equals(username) && fethcedUser.getPassword().equals(password)){
				send(new Response(Response.Status.OK, null));
			}
			else{
				send(new Response(Response.Status.FAILED, null));
			}
		case "addEvent":
			System.out.println(action);
			Update update = new Update(null, dbComm);
			Event event = (Event) req.getData().get("event");
			update.insertEvent(event); 
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
