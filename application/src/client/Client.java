package client;

import data.XMLTranslator;
import data.communication.Request;
import data.communication.Response;

import java.io.*;
import java.net.*;

public class Client {
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 4444;

	private Socket socket;
	private InputStream in;
	private OutputStream out;

	public Response send(Request req) {
		try {
			Response res = null;
			connect();
			XMLTranslator.send(req, out);
			out.flush();
			while (res == null) { // wait for response
				res = (Response) XMLTranslator.receiveResponse(in);
			}
			closeConnection();
			return res;
		} catch (IOException e) {
			System.err.println(e);
			return new Response(Response.Status.CONNECTION_PROBLEM, null);
		}
	}

	private void connect() throws UnknownHostException, IOException {
		socket = new Socket(HOST, PORT);
		out = socket.getOutputStream();
		in = socket.getInputStream();
	}

	private void closeConnection() throws IOException {
		out.close();
		in.close();
		socket.close();
	}
	
}
