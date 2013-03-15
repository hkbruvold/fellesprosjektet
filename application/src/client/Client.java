package client;

import java.io.*;
import java.net.*;
 
public class Client {
    public static void main(String[] args) throws IOException {
    	String hostadress = "127.0.0.1";
    	int port = 4444;
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
 
        try {
            socket = new Socket(hostadress, port);
            
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            receive(in);
            send(out);
            
        } catch (UnknownHostException e) {
            System.err.println("Could not find host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not get IO for host");
            System.exit(1);
        }
 
 
        System.out.println("closing");
        
        out.close();
        in.close();
        socket.close();
    }

    private static void send(PrintWriter out) {
    	System.out.println("Client: Sending start");
    	out.write("Client says: Hello");
    	System.out.println("Client: Sending end");
    }
	private static void receive(BufferedReader in) throws IOException {
		System.out.println("Client: Receiving start");
		String input;
		if (in.ready()) {
			input = in.readLine();
		} else {
			System.out.println("Client: 'in' is not ready. Returning");
			return;
		}
		while (input != null) {
			System.out.println(input);
			input = in.readLine();
		}
		System.out.println("Client: Receiving end");
	}

}