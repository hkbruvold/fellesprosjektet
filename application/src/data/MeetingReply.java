package data;

import java.io.Serializable;

@SuppressWarnings("serial")
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
	@Override
	public String toString() {
		return "MeetingReply [user=" + user + ", meeting=" + meeting
				+ ", status=" + status + "]";
	}
}