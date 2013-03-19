package data;

import java.io.Serializable;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;

public class Meeting extends Event implements Serializable{
	private User leader;
	@ElementList
	private ArrayList<User> usersInvited;
	@ElementList
	private ArrayList<User> usersAccepted;
	@ElementList
	private ArrayList<User> usersDeclined;

	public Meeting(){
	}
	/**
	 * Use id = 0 when creating new objects. Actual ID should come from database
	 */
	public Meeting(int id, String startDateTime, String endDateTime, String description, String location, User leader) {
		super(id, startDateTime, endDateTime, description, location);
		usersInvited = new ArrayList<User>();
		usersAccepted = new ArrayList<User>();
		usersDeclined = new ArrayList<User>();
		this.leader = leader;
	}
	public Meeting(int id, String startDateTime, String endDateTime, String description, Room room, User leader) {
		super(id, startDateTime, endDateTime, description, room);
		usersInvited = new ArrayList<User>();
		usersAccepted = new ArrayList<User>();
		usersDeclined = new ArrayList<User>();
		this.leader = leader;
	}

	public String toString() {
		return String.format("Meeting; ID: %s, Description: %s, Start: %s, End: %s, Location: %s, Leader: %s, Invited: %s, Accepted: %s, Declined: %s", 
				id, description, startDateTime, endDateTime, location, leader.getUsername(), usersInvited, usersAccepted, usersDeclined);
	}

	public void inviteParticipant(User user) {
		removeParticipant(user);
		usersInvited.add(user);
	}
	public void removeParticipant(User user) {
		usersInvited.remove(user);
		usersAccepted.remove(user);
		usersDeclined.remove(user);
	}
	public void acceptInvite(User user) {
		removeParticipant(user);
		usersAccepted.add(user);
	}
	public void declineInvite(User user) {
		removeParticipant(user);
		usersDeclined.add(user);
	}

	public User getLeader() {
		return leader;
	}
	public void setLeader(User leader) {
		this.leader = leader;
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
	public void setUsersInvited(ArrayList<User> invited) {
		usersInvited.clear();
		usersInvited.addAll(invited);
	}
	public void setUsersAccepted(ArrayList<User> accepted) {
		usersAccepted.clear();
		usersAccepted.addAll(accepted);
	}
	public void setUsersDeclined(ArrayList<User> declined) {
		usersDeclined.clear();
		usersDeclined.addAll(declined);
	}

}
