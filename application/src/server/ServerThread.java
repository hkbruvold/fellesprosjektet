package server;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {
	private Socket socket = null;

	public ServerThread(Socket socket) {
            super();
            this.socket = socket;
	}

	@Override
	public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
                out.writeObject(new String("Server says: Hello with object"));
                out.flush();
                
                Object input = null;
                while (input == null) {
                    input = in.readObject();
                }
                        
                System.out.println(input);
                        
                System.out.println("Server: closing (thread)");
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
}
