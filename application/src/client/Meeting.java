package client;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;

public class Meeting extends AbstractCalendarEvent implements Serializeable {
	private User leader;
	@ElementList
	private ArrayList<User> usersInvited;
	@ElementList
	private ArrayList<User> usersAccepted;
	@ElementList
	private ArrayList<User> usersDeclined;
	
	public Meeting(Calendar calendar, String startDateTime, String endDateTime, String description, String location, Alarm alarm, User leader) { // TODO temp
		super(calendar, startDateTime, endDateTime, description, location, alarm);
		usersInvited = new ArrayList<User>();
		usersAccepted = new ArrayList<User>();
		usersDeclined = new ArrayList<User>();
		this.leader = leader;
	}
	
	// TODO
	
	public void sendNotification() {
		// TODO (remember database)
	}
	public void inviteParticipant(User user) {
		usersInvited.add(user);
		// TODO (remember database)
	}
	public void removeParticipant(User user) {
		usersInvited.remove(user);
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
