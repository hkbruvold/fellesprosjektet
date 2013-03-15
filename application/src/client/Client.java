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
            System.out.println("Sending...");
            out.write("Hello world!");
            System.out.println("Sending done");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
}