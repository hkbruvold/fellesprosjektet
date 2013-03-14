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
	
	public void /* model */ toModel(/* xml */) {
		// TODO
	}
}