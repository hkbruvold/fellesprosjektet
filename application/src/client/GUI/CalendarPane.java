package client.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import client.Program;

import data.Event;

@SuppressWarnings("serial")
public class CalendarPane extends JPanel {

	private static final int ROWS = 24;
	private static final int COLLUMNS = 8;

	private final String[] days = {"Time", "Monday", "Tuesday", "wednesday", "Thurday", "Friday", "Saturday", "Sunday"};
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

	/**
	 * Create the application.
	 */
	public CalendarPane(Program program) {
		this.program = program;

		setSize(800, 400);
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
		panel.setPreferredSize(new Dimension(600, 800));
		panel.setSize(panel.getPreferredSize());
		panel.setLayout(null);

		gridSizeX = panel.getWidth() / COLLUMNS;
		gridSizeY = panel.getHeight() / ROWS;

		JViewport viewPort = new JViewport();
		viewPort.setView(panel);
		viewPort.setViewPosition(new Point(0, 8 * gridSizeY));

		calendarScroller = new JScrollPane();
		calendarScroller.setSize(panel.getWidth() + 20, 350);
		calendarScroller.setLocation(getWidth() / 2 - calendarScroller.getWidth() / 2, getHeight() - calendarScroller.getHeight());
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
			label.setVerticalAlignment(SwingConstants.BOTTOM);
			addToCalendar(label, 0, i, 1, 1);
		}


		Date test = new Date();

	}
	public void updateDates() {

		dayLine.removeAll();
		Date dates = null;
		DateFormat df = new SimpleDateFormat("yyyy w u");
		//int date = 0;
		//int month = 0;

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
					System.out.println(dates);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				JLabel dateLabel = new JLabel(new SimpleDateFormat("d/M yy").format(dates));
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

	public void addToCalendar(Component comp, double posX, double posY, double width, double height) {
		comp.setBounds((int)(posX * gridSizeX), (int)(posY * gridSizeY), (int)(gridSizeX * width), (int)(gridSizeY * height));
		panel.add(comp);
	}

	public void addEvent(Event event) {
		String start = (new SimpleDateFormat("MM HH mm u")).format(event.getStartDateTime());
		String end = (new SimpleDateFormat("MM HH mm u")).format(event.getEndDateTime());
		int day = Integer.parseInt(start.substring(9, 10));
		int hour = Integer.parseInt(start.substring(3, 5));
		int duration = Integer.parseInt(end.substring(3, 5)) - Integer.parseInt(start.substring(3, 5));
		EventComponent comp = new EventComponent(new Program(), event);
		addToCalendar(comp, day, hour, 1, duration);
	}

	public void setWeek(int newweek){
		week = newweek;
		//TODO update calendar view
	}
	public void setYear(int newyear){
		year = newyear;
		//TODO update calendar view
	}

}