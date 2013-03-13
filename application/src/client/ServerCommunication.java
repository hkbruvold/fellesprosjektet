package client;

import java.net.URL;

public class ServerCommunication {
	private URL serverURL;
	
	public ServerCommunication(URL serverURL) {
		this.serverURL = serverURL;
	}
	
	// TODO
	
	public void /*status*/ logIn (/* TODO */) {
		// TODO
	}
	public void /*status*/ logOut (/* TODO */) {
		// TODO
	}
	
	public void /*status*/ pushChanges (/* TODO */) {
		// TODO
	}
	public void /*status*/ pullChanges (/* TODO */) {
		// TODO
	}
	
	public URL getServerURL() {
		return serverURL;
	}
	public void setServerURL(URL serverURL) {
		this.serverURL = serverURL;
	}
	
}
