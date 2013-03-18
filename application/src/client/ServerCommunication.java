package client;

import java.net.MalformedURLException;
import java.net.URL;

import data.Authentication;
import data.XMLTranslator;

public class ServerCommunication {
	private URL serverURL;
	
	public ServerCommunication(URL serverURL) {
		this.serverURL = serverURL;
	}
	public ServerCommunication(String serverURL) {
		try {
			this.serverURL = new URL(serverURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO
	
	public void login (String username, String password ) {
		Authentication auth = new Authentication(username, password, "LOGIN");
		data.XMLTranslator.toXMLStream(auth);
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
