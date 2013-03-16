package client;

public class User implements Serializeable {
	private String username;
	private String name;
	private String type;
	
	public User(){
		
	}
	public User(String username, String name, String type) {
		this.username = username;
		this.name = name;
		this.type = type;
	}
	
	// TODO
	
	public String toString() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
		// TODO update database!
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		// TODO update database!
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		// TODO update database!
	}
	
}
