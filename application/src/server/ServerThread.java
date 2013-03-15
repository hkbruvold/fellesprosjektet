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
			
			send(out);
			receive(in);

			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void receive(BufferedReader in) throws IOException {
		System.out.println("Server: Receiving start");
		String input;
		if (in.ready()) {
			input = in.readLine();
		} else {
			System.out.println("Server: 'in' is not ready. Returning");
			return;
		}
		while (input != null) {
			System.out.println(input);
			input = in.readLine();
		}
		System.out.println("Server: Receiving end");
	}

	private void send(PrintWriter out) {
		System.out.println("Server: Sending start");
		out.write("Server says: Hello");
		System.out.println("Server: Sending end");
	}
}