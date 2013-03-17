package data;

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
	
	public Meeting(){
		
	}
	public Meeting(Calendar calendar, String startDateTime, String endDateTime, String description, String location, Alarm alarm, User leader) { // TODO temp
		super(calendar, startDateTime, endDateTime, description, location, alarm);
		usersInvited = new ArrayList<User>();
		usersAccepted = new ArrayList<User>();
		usersDeclined = new ArrayList<User>();
		this.leader = leader;
	}
	
	// TODO
	
	public void sendNotification() { // Unused?
		// TODO (remember database)
	}
	public void inviteParticipant(User user) {
		usersInvited.add(user);
		usersAccepted.remove(user);
		usersDeclined.remove(user);
		// TODO (remember database)
	}
	public void removeParticipant(User user) {
		usersInvited.remove(user);
		usersAccepted.remove(user);
		usersDeclined.remove(user);
		// TODO (remember database)
	}
	public void acceptInvite(User user) {
		usersInvited.remove(user);
		usersAccepted.add(user);
		usersDeclined.remove(user);
		// TODO (remember database)
	}
	public void declineInvite(User user) {
		usersInvited.remove(user);
		usersAccepted.remove(user);
		usersDeclined.add(user);
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
	@Override
	public String toString() {
		return "Meeting [leader=" + leader + ", usersInvited=" + usersInvited
				+ ", usersAccepted=" + usersAccepted + ", usersDeclined="
				+ usersDeclined + "]";
	}
	
}
