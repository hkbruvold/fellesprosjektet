package data;

public class Room {
	private int id;
	private int size;
	private String description;
	
	/**
	 * Use id = -1 when creating new objects. Actual ID should come from database
	 */
	public Room() {
	}
	public Room(int id, int size, String description) {
		this.id = id;
		this.size = size;
		this.description = description;
	}
	
	public String toString() {
		return description + " (" + id + ")";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
