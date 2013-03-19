package client;

import java.io.IOException;

import client.GUI.*;
import client.ServerCommunication;
import data.Alarm;
import data.Event;
import data.User;

public class Program {
	ServerCommunication sc;
	User loggedIn;
	
	private static final String SERVER_ADDRESS = "";
	
	public Program() {
		sc = new ServerCommunication(SERVER_ADDRESS);
		showLogin();
	}
	
	public void showLogin() {
		new LoginWindow(this);
	}
	
	public void showMainWindow() {
		new MainWindow(this);
	}
	
	public void quit() {
		System.out.println("Ouit");
		// TODO quit
	}
	
	public boolean login(String username, String password) {
		User loginUser;
		try {
			loginUser = sc.login(username, password);
		} catch (IOException e) {
			System.out.println("Error connecting to server");
			e.printStackTrace();
			return false;
		}
		
		if (loginUser != null) {
			this.loggedIn = loginUser;
			return true;
		} else {
			return false;
		}
	}
	// TODO
	
	public void registerEvent(Event event) {
		// TODO (remember database!)
	}
	public void editEvent(Event event/*, TODO */) {
		// TODO (remember database!)
	}
	public void removeEvent(Event event) {
		// TODO (remember database!)
	}
	
	public void registerAlarm(Alarm alarm) {
		// TODO send alarm to server
	}
	
	public static void main(String[] args) {
		new Program();
	}
	
}
