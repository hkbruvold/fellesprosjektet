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
    
    public Response send (Request req) throws IOException, ClassNotFoundException {
        Response res = null;
        
        connect();
        
        out.writeObject(req);
        out.flush();
        
        // Wait for response
        while (res == null) {
            res = (Response) in.readObject();
        }
        
        closeConnection();
        
        return res;
    }
    
    private void connect () throws UnknownHostException, IOException {
        socket = new Socket(HOST, PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    private void closeConnection () throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}