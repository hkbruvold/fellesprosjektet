package client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import data.Authentication;
import data.User;
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
	
	public User login (String username, String password ) throws IOException {
		return new User(); //temporary until server communication is supported
		/*
		Authentication auth = new Authentication(username, password, "LOGIN");
		Client.sendObject(data.XMLTranslator.toXMLStream(auth));
		if (true) {
			return new User();// TODO return user from server
		} else {
			return null;
		}
		 // TODO return user from server*/
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
