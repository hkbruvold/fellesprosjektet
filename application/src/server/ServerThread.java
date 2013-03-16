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
			
//			System.out.println("Server: handshake start");
//			System.out.println("Server: handshake end\n");
			
			System.out.println("Server: Sending start");
			out.write("Server says: Hello");
			System.out.println("Server: Sending end\n");

			System.out.println("Server: Receiving start");
			String input = null;
			if (in.ready()) {
				input = in.readLine();
			} else {
				System.out.println("not ready");
			}
			while (input != null) {
				System.out.println(input);
				input = in.readLine();
			}
			System.out.println("Server: Receiving end\n");

			System.out.println("Server: closing (thread)");
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
