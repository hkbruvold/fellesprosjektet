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

	@Override
	public void run(){
		while(True){
			program.requestChanges();
//			ArrayList<Notification> notificationArray = program.getAllNotifications();
//			for(Notification notification : notificationArray){
//				new NotificationWindow(program, notification);
//			}
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
