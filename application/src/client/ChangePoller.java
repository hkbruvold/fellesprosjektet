package client;

import data.User;
import data.communication.ChangeData;

public class ChangePoller extends Thread {
	private static final int INTERVAL = 5000;
	private User currentUser;
	private Program program;

	public ChangePoller(User currentUser, Program program){
		this.currentUser = currentUser;
		this.program = program;
	}

	@Override
	public void run(){
		while(true){
			request();
			try {
				sleep(INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void request() {
		ChangeData changeData = program.requestChanges();
		if (changeData == null) {
			return;
		}
		long clientVersion = program.getVersion();
		long serverVersion = changeData.getVersionNumber();
		if (clientVersion == serverVersion) {
			return;
		}
		System.out.println(changeData);
		// TODO use cangedata to update client-side data
		program.refreshCalendar(); // need to be changed when the above todo is finished
		program.setVersion(changeData.getVersionNumber());
	}

}
