package client;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XMLTranslator {
	// TODO fields?
	
	// TODO
	
	public static void toXML(AbstractCalendarEvent calendarEvent){
		Serializer serializer = new Persister();
		File result = new File("example.xml");
		try {
			serializer.write(calendarEvent, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public static void toXML(Alarm alarm){
		Serializer serializer = new Persister();
		File result = new File("example.xml");
		try {
			serializer.write(alarm, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void /* model */ toModel(/* xml */) {
		// TODO
	}
}