package client;
import java.util.ArrayList;

import client.*;
import client.GUI.NotificationWindow;
import data.Notification;
import data.User;

public class ChangePoller extends Thread {
	private static final boolean True = true;
	private User currentUser;
	private Program program;

	public ChangePoller(User currentUser, Program program){
		this.currentUser = currentUser;
		this.program = program;
		
		
		}
	public void run(){
		while(True){
			ArrayList<Notification> notificationArray = program.getAllNotifications();
			for(Notification notification : notificationArray){
				new NotificationWindow(program, notification);
				}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	}
}