package data;

import java.io.Serializable;

public class MeetingReply implements Serializable {
	User user;
	Meeting meeting;
	String status;
	public MeetingReply(User user, Meeting meeting, String status){
		this.user = user;
		this.meeting = meeting;
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public Meeting getMeeting() {
		return meeting;
	}
	public String getStatus() {
		return status;
	}
}