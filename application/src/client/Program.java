package client;

import java.io.IOException;

import client.GUI.*;
import data.*;
import java.util.HashMap;

public class Program {
    private Client client;
    private User loggedIn;

    public Program() {
        client = new Client();
        showLogin();
    }

    public void showLogin() {
        if (loggedIn == null) {
            // TODO can't show main window
        }
        new LoginWindow(this);
    }

    public void showMainWindow() {
        new MainWindow(this);
    }

    public void quit() {
        System.out.println("Ouit");
        // TODO quit
    }

    public boolean login (String username, String password) {
        HashMap values = new HashMap();
        values.put("username", username);
        values.put("password", password);
        Response res = client.send(new Request("login", values));
        
        return res.status == Response.Status.OK;
    }
    // TODO

    public void registerEvent(Event event) {
    	HashMap values = new HashMap();
    	values.put("event", event);
    	Response res = client.send(new Request("addEvent", values));
    }
    public void editEvent(Event event/*, TODO */) {
        // TODO (remember database!)
    }
    public void removeEvent(Event event) { 
        // TODO (remember database!)
    }

    public void registerAlarm(Alarm alarm) { 
    	HashMap values = new HashMap();
    	values.put("alarm", alarm);
    	Response res = client.send(new Request("addAlarm", values));
    }

    public static void main(String[] args) {
       new Program();
    }

}
