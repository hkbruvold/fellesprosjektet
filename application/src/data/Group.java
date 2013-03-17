package data;

import java.util.List;
import java.util.ArrayList;

public class Group implements Serializeable {
	private int id; // TODO
	private String name;
	private String description; // TODO
	private ArrayList<User> members;
	
	public Group(){
	}
	/**
	 * @param id
	 * Use -1 when creating new objects! Database should set actual id
	 * @param name
	 */
	public Group(int id, String name) {
		this.name = name;
		members = new ArrayList<User>();
	}
	
	// TODO
	
	public String tosString() {
		return String.format("Group; ID: %s, Group name: %s, Description: %s, Number of members: %s", id, name, description, members.size());
	}

	public int getId() {
		return id;
	}
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
