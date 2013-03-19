package server;

import data.Request;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {
	private Socket socket = null;
        private ObjectOutputStream out;
        private ObjectInputStream in;

	public ServerThread(Socket socket) {
            super();
            this.socket = socket;
	}

	@Override
	public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                Request req = null;
                
                while (req == null) {
                    req = (Request) in.readObject();
                }
                
                parseRequest(req);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e);
            }
        }
        
        public void parseRequest (Request req) {
            // TODO
        }
        
        public boolean closeSocket () {
            try {
                in.close();
                out.close();
                socket.close();
                return true;
            } catch (IOException e) {
                System.err.println(e);
                return false;
            }
        }
}
