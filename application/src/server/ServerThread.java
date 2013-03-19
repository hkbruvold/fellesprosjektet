package server;

import data.Request;
import data.Response;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
	private Socket socket = null;
        private ObjectOutputStream out;
        private ObjectInputStream in;

	public ServerThread (Socket socket) {
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
                closeSocket();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e);
            }
        }
        
        public void parseRequest (Request req) {
            String action = req.getAction();
            
            switch (action) {
                case "login":
                default:
                    System.out.println(action);
                    send(new Response(Response.Status.FAILED, null));
            }
        }
        
        public void send (Response res) {
            try {
                out.writeObject(res);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        
        public void closeSocket () {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
}
