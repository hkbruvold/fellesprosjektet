package client.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import client.Program;

import data.Event;
import data.User;
import client.GUI.EventComponent;

@SuppressWarnings("serial")
public class CalendarPane extends JPanel {
	private static final int ROWS = 24;
	private static final int COLLUMNS = 8;

	private final String[] days = {"Time", "Monday", "Tuesday", "Wednesday", "Thurday", "Friday", "Saturday", "Sunday"};
	private final String[] hours = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", 
			"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", 
			"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};


	private int gridSizeX;
	private int gridSizeY;

	private JPanel dayLine;
	private JPanel panel;
	private JScrollPane calendarScroller;

	private Date currentDate;
	private int year;
	private Program program;
	private static int week;
	private String[] daysOfWeek;
	
	private ArrayList<ArrayList<EventComponent>> eventDayList;
	private int[] laneSizes = new int[7];
	private HashMap<EventComponent, ArrayList<EventComponent>> overlapList;
	private HashMap<EventComponent, Integer> eventPosition; 
	private ArrayList<User> showUserCalendars; // List of users to show event from
	private ArrayList<EventComponent> displayedComponents;

	/**
	 * Create the application.
	 */
	public CalendarPane(Program program) {
		this.program = program;

		setSize(853, 482);
		setLayout(null);

		currentDate = new Date();
		year = Integer.parseInt((new SimpleDateFormat("yyyy")).format(currentDate));
		week = Integer.parseInt((new SimpleDateFormat("w")).format(currentDate));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				for(int j = 1; j < ROWS + 1; j++) {
					if(j > 0) {
						g.setColor(Color.LIGHT_GRAY);
					}
					g.drawLine(0, j * (getHeight() / ROWS), getWidth(), j * (getHeight() / ROWS));
				}
				g.setColor(Color.BLACK);
				for(int i = 1; i < COLLUMNS; i++) {
					g.drawLine(i * (getWidth() / COLLUMNS), 0, i * (getWidth() / COLLUMNS), getHeight());
				}
				super.paintChildren(g);
			}
		};
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setPreferredSize(new Dimension(500, 800));
		panel.setSize(panel.getPreferredSize());
		panel.setLayout(null);

		gridSizeX = 810 / COLLUMNS;
		gridSizeY = panel.getHeight() / ROWS;

		JViewport viewPort = new JViewport();
		viewPort.setView(panel);
		viewPort.setViewPosition(new Point(0, 8 * gridSizeY));

		calendarScroller = new JScrollPane();
		calendarScroller.setSize(833, 405);
		calendarScroller.setLocation(10, 11);
		calendarScroller.setViewport(viewPort);
		add(calendarScroller);


		dayLine = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				for(int i = 1; i < COLLUMNS; i++) {
					g.drawLine(i * (getWidth() / COLLUMNS), 0, i * (getWidth() / COLLUMNS), getHeight());
				}
				super.paintChildren(g);
			}
		};
		dayLine.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		dayLine.setPreferredSize(new Dimension(panel.getWidth(), (int)(1.3 * gridSizeY)));
		dayLine.setSize(dayLine.getPreferredSize());
		dayLine.setLayout(null);

		updateDates();

		for(int i = 0; i < hours.length; i++) {
			JLabel label = new JLabel(hours[i]);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.TOP);
			addToCalendar(label, 0, i, 1, 1);
		}
		
		showUserCalendars = new ArrayList<User>();
		showUserCalendars.add(program.getCurrentUser());

		initEventData();
		displayedComponents = new ArrayList<EventComponent>();
	}
	public void updateDates() {

		dayLine.removeAll();
		Date dates = null;
		DateFormat df = new SimpleDateFormat("yyyy w u");
		DateFormat ISOdf = new SimpleDateFormat("yyyy-MM-dd");
		//int date = 0;
		//int month = 0;
		//TODO Fetch events from database, and add them all to the calendarPane!
		
		daysOfWeek = new String[7];
		
		for(int i = 0; i < days.length; i++) {
			JLabel weekDay = new JLabel(days[i]);
			weekDay.setBounds(i * gridSizeX, 0, gridSizeX, gridSizeY);
			weekDay.setHorizontalAlignment(SwingConstants.CENTER);
			dayLine.add(weekDay);
			if(i > 0) {
				try {
					if(i != 7) {
						dates = df.parse(year + " " + week + " " + (i % 8));
					}
					else {
						dates = df.parse(year + " " + (week + 0) + " " + (-6));
					}
					daysOfWeek[i-1] = ISOdf.format(dates);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				JLabel dateLabel = new JLabel(new SimpleDateFormat("YYYY-MM-dd").format(dates));
				dateLabel.setBounds(i * gridSizeX, dateLabel.getFont().getSize(), gridSizeX, gridSizeY);
				dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
				dayLine.add(dateLabel);
			}
		}

		try {
			dates = (new SimpleDateFormat("yyyy w u")).parse(year + " " + week + " "  + 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		calendarScroller.setColumnHeaderView(dayLine);
	}

	private void addToCalendar(Component comp, double posX, double posY, double width, double height) {
		comp.setBounds((int)(posX * gridSizeX), (int)(posY * gridSizeY), (int)(gridSizeX * width), (int)(gridSizeY * height));
		panel.add(comp);
	}
	
	public void setShowUserCalendars(List<User> users) {
		for (User user: users) {
			this.showUserCalendars.add(user);
		}
	}
	
	public void initEventData() {
		eventDayList = new ArrayList<ArrayList<EventComponent>>();
		for (int i = 0; i < 7; i++) {
			eventDayList.add(new ArrayList<EventComponent>());
		}
		overlapList = new HashMap<EventComponent, ArrayList<EventComponent>>();
		eventPosition = new HashMap<EventComponent, Integer>();
	}
	
	public void showCalendar() {
		// Remove previously shown events
		hideAllEvents();
		initEventData();
		
		addRelevantEvents();
		
		// Generate data for display logic
		generateLaneSizes();
		
		
		for (int i = 0; i<7; i++) {
			ArrayList<EventComponent> eventList = eventDayList.get(i);
			for (EventComponent event: eventList) {
				addEventToCalendar(event);
			}
		}
		panel.repaint();
	}
	
	private void hideAllEvents() {
		for (EventComponent ec : displayedComponents) {
			panel.remove(ec);
		}
		panel.repaint();
		displayedComponents = new ArrayList<EventComponent>();
	}
	
	/**
	 * Adds events to list if it is to be shown
	 */
	private void addRelevantEvents() {
		HashMap<String, HashMap<Integer,Event>> events = program.getEventList();
		for (User user: showUserCalendars) {
			String username = user.getUsername();
			if (events.containsKey(username)) {
				for (Event event : events.get(username).values()) {
					String string_0 = event.getStartDateTime().split(" ")[0];
					if (Arrays.asList(daysOfWeek).contains(string_0)) {
						EventComponent eventComp = new EventComponent(event);
						eventDayList.get(event.getDayOfWeek()).add(eventComp);
						if (!overlapList.containsKey(event)) {
							overlapList.put(eventComp, new ArrayList<EventComponent>());
						}
					}
				}
			}
		}
	}

	private void addEventToCalendar(EventComponent eventComp) {
		Event event = eventComp.getEvent();
		Calendar start = event.getStartDate();
		Calendar end = event.getEndDate();
		
		int dayOfWeek = event.getDayOfWeek();
		double startTime = start.get(Calendar.HOUR_OF_DAY) + (double) (start.get(Calendar.MINUTE)) * 100 / 60 / 100;
		double duration = (double) (end.getTimeInMillis() - start.getTimeInMillis()) / (1000*60*60);
		
		int position = getFreeLane(eventComp);
		eventPosition.put(eventComp, position);
		
		int height = (int) (duration*gridSizeY);
		int width = (int) (gridSizeX/laneSizes[dayOfWeek]);
		int x = dayOfWeek*gridSizeX + (int) (position*(gridSizeX/laneSizes[dayOfWeek]) - 2*gridSizeX);
		int y = (int) startTime*gridSizeY;
		
		// Set another colour for other users
		if (program.getCurrentUser() != event.getUser()) {
			eventComp.setColor(new int[] {126, 126, 126});
		}

		// Stored here so that it can easily be removed later
		displayedComponents.add(eventComp);
		
		eventComp.setBounds(x, y, width, height);
		panel.add(eventComp);
		eventComp.addMouseListener(new EventMouseListener());
		eventComp.setVisible(true);
	}
	
	private int getFreeLane(EventComponent eventComp) {
		boolean[] takenLane = new boolean[laneSizes[eventComp.getEvent().getDayOfWeek()]];
		
		if (overlapList.containsKey(eventComp)) {
			ArrayList<EventComponent> eventOverlaps = overlapList.get(eventComp);
			for (EventComponent event0: eventOverlaps) {
				if (eventPosition.containsKey(event0)) {
					takenLane[(int)eventPosition.get(event0)] = true;
				}
			}
		}
		
		for (int i = 0; i < laneSizes[eventComp.getEvent().getDayOfWeek()]; i++) {
			if (takenLane[i] == false) {
				return i;
			}
		}
		return -1;
	}
	
	private void generateLaneSizes() {
		for (int i=0; i < 7; i++) {
			this.laneSizes[i] = getLaneSize(i);
		}
	}
	
	private int getLaneSize(int day) {		
		int curMax = 1;
		int curSize;
		for (EventComponent eventComp: this.eventDayList.get(day)) {
			curSize = amountOfOverlappingEvents(eventComp);
			if (curSize > curMax) {
				curMax = curSize;
			}
		}
		return curMax;
	}
	
	private int amountOfOverlappingEvents(EventComponent eventComp) {
		int result = 1;
		
		for (EventComponent event0: this.eventDayList.get(eventComp.getEvent().getDayOfWeek())) {
			if (isOverlapping(eventComp, event0) && eventComp != event0) {
				result++;
				overlapList.get(eventComp).add(event0);
			}
		}
		return result;
	}
	
	private boolean isOverlapping(EventComponent event0, EventComponent event1) {
		long start0 = event0.getEvent().getStartDate().getTimeInMillis();
		long end0 = event0.getEvent().getEndDate().getTimeInMillis(); 
		long start1 = event1.getEvent().getStartDate().getTimeInMillis();
		long end1 = event1.getEvent().getEndDate().getTimeInMillis();
		
		if ((start0 < start1 && start1 < end0) || // start1 in event0
				(start0 < end1 && end1 < end0) || // end1 in event0
				(start1 < start0 && start0 < end1) || // start0 in event1
				(start1 < end0 && end0 < end1) || // end0 in event1
				(start0 == start1 && end0 == end1) || // events are in the same time
				(start0 < start1 && end1 < end0) || //event1 inside event0
				(start1 < start0 && end0 < end1)) { //event0 inside event1
			return true;
		}
		return false;
	}

	public void setWeek(int newweek){
		week = newweek;
		//TODO update calendar view
	}
	public void setYear(int newyear){
		year = newyear;
		//TODO update calendar view
	}

	private final class EventMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			new NewEventWindow(program, ((EventComponent) e.getComponent()).getEvent(), program.getAllUsers());
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}

}