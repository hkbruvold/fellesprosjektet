package temp;

import temp.TestObjects;
import data.XMLTranslator;

public class XMLTester {
	public static void main(String[] args) {
		XMLTranslator.toXML(TestObjects.getAuthentication00());
		XMLTranslator.toModel();
	}
}
