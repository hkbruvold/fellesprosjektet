package client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	String hostadress = "127.0.0.1";
    	int port = 4444;
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
 
        try {
            socket = new Socket(hostadress, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            Object input = null;
            while (input == null) {
                input = in.readObject();
            }
            
            System.out.println(input);
            
            out.writeObject(new String("Client says: Hello with object"));
            out.flush();
            
            System.out.println("Client: closing");
            out.close();
            in.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Could not find host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not get IO for host");
            System.exit(1);
        }
    }

}
