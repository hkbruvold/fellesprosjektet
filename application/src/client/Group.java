package client;

import java.util.ArrayList;

public class Group {
	private String name;
	private ArrayList<User> members;
	
	// TODO
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		// TODO update database!
	}
	public ArrayList<User> getMembers() {
		return members;
	}
	public void addMember(User newMember) {
		members.add(newMember);
		// TODO update database!
	}
	
}