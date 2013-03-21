package temp;

import client.Program;
import client.GUI.CalendarPane;
import client.GUI.MainWindow;
import data.Event;
import data.Meeting;
import data.User;

public class TestCalendarPane {
	
	public TestCalendarPane() {
		MainWindow mainWindow = new MainWindow(new Program(), new User());
		CalendarPane calendarPane = mainWindow.getCalendarPane();
		
		Event event0 = new Meeting();
		event0.setDescription("event0");
		event0.setStartDateTime("2013-03-20 12:00");
		event0.setEndDateTime("2013-03-20 14:30");
//		calendarPane.addEvent(event0);

		Event event1 = new Meeting();
		event1.setDescription("event1");
		event1.setStartDateTime("2013-03-20 13:00");
		event1.setEndDateTime("2013-03-20 16:00");
//		calendarPane.addEvent(event1);

		Event event2 = new Meeting();
		event2.setDescription("event2");
		event2.setStartDateTime("2013-03-20 15:00");
		event2.setEndDateTime("2013-03-20 17:00");
//		calendarPane.addEvent(event2);

		Event event3 = new Meeting();
		event3.setDescription("event3");
		event3.setStartDateTime("2013-03-20 13:00");
		event3.setEndDateTime("2013-03-20 18:00");
//		calendarPane.addEvent(event3);
		
		
//		calendarPane.updateCalendar();
	}
	
	public static void main(String[] args) {
		new TestCalendarPane();
	}
	
	
}
