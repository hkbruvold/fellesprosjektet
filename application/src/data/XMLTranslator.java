package data;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XMLTranslator {

	public static void send(Serializable object, OutputStream out) {
		Serializer serializer = new Persister();
		try {
			serializer.write(object, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Serializable receiveRequest(InputStream source) {
		Serializer serializer = new Persister();
		Serializable object = null;
		try {
			object = serializer.read(Request.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	public static Serializable receiveResponse(InputStream source) {
		Serializer serializer = new Persister();
		Serializable object = null;
		try {
			object = serializer.read(Response.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

}
