package client;

import data.User;
import data.communication.ChangeData;

public class ChangePoller extends Thread {
	private static final boolean True = true;
	private User currentUser;
	private Program program;

	public ChangePoller(User currentUser, Program program){
		this.currentUser = currentUser;
		this.program = program;
	}

	@Override
	public void run(){
		while(True){
			ChangeData res = program.requestChanges();
			System.out.println(res);
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
