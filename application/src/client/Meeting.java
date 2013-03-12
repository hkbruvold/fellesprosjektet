package client;

import java.util.ArrayList;

public class Meeting extends AbstractCalendarEvent {
	private User leader;
	private ArrayList<User> usersInvited;
	private ArrayList<User> usersAccepted;
	private ArrayList<User> usersDeclined;
	
	// TODO
	
	public void sendNotification() {
		// TODO (remember database)
	}
	public void addParticipant() {
		// TODO (remember database)
	}
	public void removeParticipant() {
		// TODO (remember database)
	}
	public void acceptInvite() {
		// TODO (remember database)
	}
	public void declineInvite() {
		// TODO (remember database)
	}
	
	public User getLeader() {
		return leader;
	}
	public ArrayList<User> getUsersInvited() {
		return usersInvited;
	}
	public ArrayList<User> getUsersAccepted() {
		return usersAccepted;
	}
	public ArrayList<User> getUsersDeclined() {
		return usersDeclined;
	}
	
}
