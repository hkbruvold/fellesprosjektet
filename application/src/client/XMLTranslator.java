package client;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XMLTranslator {
	// TODO fields?
	
	// TODO
	
	public static void toXML(Serializeable object) {
		Serializer serializer = new Persister();
		File result = new File("example.xml");
		try {
			serializer.write(object, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Serializeable toModel() {
		Serializer serializer = new Persister();
		File source = new File("example.xml");
		Serializeable object = serializer.read(Serializeable.class, source);
		return object;

	}
}