package client;

import data.Request;
import data.Response;
import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 4444;
    
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client () {}
    
    public Boolean connect () {
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            return true;
        } catch (UnknownHostException e) {
            System.err.println("Could not find host");
            return false;
        } catch (IOException ex) {
            System.err.println(ex);
            return false;
        }
    }
    
    public Response send (Request req) throws IOException, ClassNotFoundException {
        Object input = null;
            
        out.writeObject(req);
        out.flush();
        
        // Wait for response
        while (input == null) {
            input = in.readObject();
        }
        
        return (Response) input;
    }
    
    public void closeConnection () throws IOException {
        out.writeObject(new Request("end", null));
        out.close();
        in.close();
        socket.close();
    }
}