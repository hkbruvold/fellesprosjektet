package data;

public class User implements Serializeable {
	private String username;
	private String password;
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
		return String.format("User; Username: %s, Name: %s, Type: %s", username, name, type);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
