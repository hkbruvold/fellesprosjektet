package data;

import java.util.List;
import java.util.ArrayList;

public class Group implements Serializeable {
	private String name;
	private ArrayList<User> members;
	
	public Group(){
		
	}
	public Group(String name) {
		this.name = name;
		members = new ArrayList<User>();
	}
	
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

	public void addMembers(List<User> users) {
		members.addAll(users);
	}
	public int getSize() {
		return members.size();
	}
	
}
