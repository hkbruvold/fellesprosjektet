package temp;

import temp.TestObjects;
import client.XMLTranslator;

public class XMLTester {
	public static void main(String[] args) {
		XMLTranslator.toXML(TestObjects.getMeeting01());
	}
}
