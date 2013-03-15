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
			System.out.println("Receiving...");
			String input = in.readLine();
			while (input != null) {
				System.out.println(input);
				input = in.readLine();
			}
			System.out.println("Receiving done");
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}