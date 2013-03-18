package server;

import java.net.*;
import java.io.*;

public class Server {
	private static final int PORT = 4444;
	private static ServerSocket serverSocket;
	private static boolean listening;

	public static void main(String[] args) throws IOException {
		listening = true;
		
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(-1);
		}

		ServerThread st;
		while (listening){
			st = new ServerThread(serverSocket.accept());
			st.start();
			serverSocket.close();
			serverSocket = new ServerSocket(PORT);
		}
	}
}
