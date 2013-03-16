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
            
//            System.out.println("Client: handshake start");
//            System.out.println("Client: handshake end\n");

            System.out.println("Client: Receiving start");
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
            System.out.println("Client: Receiving end\n");

            System.out.println("Client: Sending start");
            out.write("Client says: Hello");
            System.out.println("Client: Sending end\n");
            
        } catch (UnknownHostException e) {
            System.err.println("Could not find host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not get IO for host");
            System.exit(1);
        }
 
 
        System.out.println("Client: closing");
        
        out.close();
        in.close();
        socket.close();
    }

}
