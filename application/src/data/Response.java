package data;

import java.io.Serializable;
import java.util.HashMap;

public class Response implements Serializable {
    public Status status;
    private Object data;
            
    public Response (Status status, HashMap data) {
        this.status = status;
        this.data = data;
    }
    
    public Object getData () {
        return data;
    }
    
    public Status getStatus () {
        return status;
    }

    public enum Status {
        OK, FAILED
    }
}