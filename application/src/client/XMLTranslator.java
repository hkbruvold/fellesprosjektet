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

	public static Serializeable toModel() {
		Serializer serializer = new Persister();
		File source = new File("example.xml");
		Serializeable object = null;
		try {
			if (serializer.validate(Alarm.class, source)) {
				
			} 
		} catch (Exception e) {
			System.out.println("wrong one...");
		}
		try {
			if (serializer.validate(Appointment.class, source)) {

			} 
		} catch (Exception e) {
			System.out.println("wrong one...");
		}
		try {
			if (serializer.validate(Calendar.class, source)) {

			} 
		} catch (Exception e) {
			System.out.println("wrong one...");
		}
		try {
			if (serializer.validate(Group.class, source)) {

			} 
		} catch (Exception e) {
			System.out.println("wrong one...");
		}
		try {
			if (serializer.validate(Meeting.class, source)) {
				System.out.println("Correct one!");
				object = serializer.read(Meeting.class, source);
			} 
		} catch (Exception e) {
			System.out.println("wrong one... maybe");
			e.printStackTrace();
		}
		try {
			if (serializer.validate(Notification.class, source)) {

			} 
		} catch (Exception e) {
			System.out.println("wrong one...");
		}
		try {
			if (serializer.validate(User.class, source)) {

			}
		} catch (Exception e) {
			System.out.println("wrong one...");
		}
		return object;

	}
}