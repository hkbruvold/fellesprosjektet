package data;

import java.io.File;
import java.io.OutputStream;

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
	
	public static OutputStream toXMLStream(Serializeable object){
		Serializer serializer = new Persister();
		OutputStream ostream = null;
		try {
			serializer.write(object, ostream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ostream;
	}

	public static Serializeable toModel() {
		Serializer serializer = new Persister();
		File source = new File("example.xml");
		Serializeable object = null;
		try {
			object = serializer.read(Alarm.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			object = serializer.read(Appointment.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			object = serializer.read(Calendar.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			object = serializer.read(Group.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			object = serializer.read(Meeting.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			object = serializer.read(Notification.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			object = serializer.read(User.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			object = serializer.read(Authentication.class, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;

	}
}