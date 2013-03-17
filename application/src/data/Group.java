package data;

import java.util.List;
import java.util.ArrayList;

public class Group implements Serializeable {
	private int id;
	private String name;
	private String description;
	private ArrayList<User> members;
	
	public Group(){
	}
	/**
	 * Use id = -1 when creating new objects. Actual id should come from database
	 */
	public Group(int id, String name, String description) {
		this.name = name;
		members = new ArrayList<User>();
		this.description = description;
	}
	
	public String tosString() {
		return String.format("Group; ID: %s, Group name: %s, Description: %s, Number of members: %s", id, name, description, members.size());
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		// TODO update database!
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
