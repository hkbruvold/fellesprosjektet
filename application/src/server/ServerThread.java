package server;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
	private Socket socket = null;

	public ServerThread(Socket socket) {
            super();
            this.socket = socket;
	}

	@Override
	public void run() {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
                out.println("Server says: Hello");
                out.flush();
                
                String input = null;
                while (input == null) {
                    input = in.readLine();
                }
                        
                System.out.println(input);
                        
                System.out.println("Server: closing (thread)");
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
	}

}
