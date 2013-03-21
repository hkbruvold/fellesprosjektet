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
		ChangeData res = program.requestChanges();
		System.out.println(res);
	}

}
