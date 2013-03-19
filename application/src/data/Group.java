package data;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Group implements Serializable {
	private int id;
	private String name;
	private String description;
	private ArrayList<User> members;

	public Group(){
	}
	/**
	 * Use id = 0 when creating new objects. Actual id should come from database
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
		if (!members.contains(newMember)) {
			members.add(newMember);
		}
	}
	public void addMembers(List<User> users) {
		for (User user : users) {
			addMember(user);
		}
	}
	public int getSize() {
		return members.size();
	}

}
