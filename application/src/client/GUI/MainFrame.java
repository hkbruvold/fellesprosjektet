package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import client.Program;
import data.Notification;
import data.User;

@SuppressWarnings("serial")
public class MainFrame extends JPanel implements ActionListener {
	private static final String FRAME_NAME = "Kalender";

	private static final String BUTTON_NEW_EVENT = "Ny avtale";
	private static final String BUTTON_NOTIFICATIONS = "Meldinger";
	private static final String BUTTON_CALENDARS = "Kalendere";
	private static final String BUTTON_QUIT = "Avslutt";

	private static final String LABEL_WEEK = "Uke";
	private static final String LABEL_YEAR = "Aar";

	private static final String[] ARRAY_STRING_WEEKS = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53"};
	private static final String[] ARRAY_STRING_YEARS = new String[] {"2013","2014","2015","2016","2017"};

	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;

	private static final int HORIZONTAL = GridBagConstraints.HORIZONTAL;
	private static final int NONE = GridBagConstraints.NONE;

	private JFrame frame;
	private GridBagConstraints c;

	private Program program;
	private CalPanel calPanel;
	private User currentUser;

	private JLabel labelWeek, labelYear;
	private JComboBox<String> comboBoxWeek, comboBoxYear;
	private JButton buttonNewEvent, buttonNotifications, buttonCalendars, buttonQuit;


	public MainFrame(Program program, User currentUser){
		this.program = program;
		this.currentUser = currentUser;
		initFrame();
		initPanel();

		frame.pack();
		frame.setVisible(true);
	}

	private void initFrame() {
		frame = new JFrame(FRAME_NAME);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new FlowLayout());
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		initComponents();

		c.insets = new Insets(0,0,5,5);
		c.ipadx = 10;

		addComponents();

		addListeners();
	}

	private void initComponents() {
		labelWeek = new JLabel(LABEL_WEEK);
		comboBoxWeek = new JComboBox<String>(ARRAY_STRING_WEEKS);
		labelYear = new JLabel(LABEL_YEAR);
		comboBoxYear = new JComboBox<String>(ARRAY_STRING_YEARS);
		
		buttonNewEvent = new JButton(BUTTON_NEW_EVENT);
		buttonNotifications = new JButton(BUTTON_NOTIFICATIONS);
		buttonCalendars = new JButton(BUTTON_CALENDARS);
		buttonQuit = new JButton(BUTTON_QUIT);
		
		calPanel = new CalPanel(program);
	}
	private void addComponents() {
		addComponent(1, 0, 1, 1, labelWeek, LINE_END, HORIZONTAL);
		addComponent(2, 0, 1, 1, comboBoxWeek, LINE_START, HORIZONTAL);
		addComponent(4, 0, 1, 1, labelYear, LINE_END, HORIZONTAL);
		addComponent(5, 0, 1, 1, comboBoxYear, LINE_START, HORIZONTAL);

		addComponent(0, 1, 1, 1, buttonNewEvent, LINE_START, HORIZONTAL);
		addComponent(0, 2, 1, 1, buttonNotifications, LINE_START, HORIZONTAL);
		addComponent(0, 3, 1, 1, buttonCalendars, LINE_START, HORIZONTAL);
		addComponent(0, 4, 1, 1, buttonQuit, LINE_START, HORIZONTAL);
		
		addComponent(1, 1, 5, 4, calPanel, GridBagConstraints.FIRST_LINE_START, NONE);
	}
	private void addListeners() {
		comboBoxWeek.addActionListener(this);
		comboBoxYear.addActionListener(this);
		buttonNewEvent.addActionListener(this);
		buttonNotifications.addActionListener(this);
		buttonCalendars.addActionListener(this);
		buttonQuit.addActionListener(this);
		// calpane?
	}
	private void addComponent(int gridx, int gridy, int gridwidth, int gridheight, JComponent component, int anchor, int fill) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor = anchor;
		c.fill = fill;
		add(component, c);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(comboBoxWeek)) {
			calPanel.setWeek(comboBoxWeek.getSelectedItem());
		} else if (e.getSource().equals(comboBoxYear)) {
			calPanel.setYear(comboBoxYear.getSelectedItem());
		} else if (e.getSource().equals(buttonNewEvent)) {
			new NewEventWindow(program, currentUser, program.getAllUsers());
		} else if (e.getSource().equals(buttonNotifications)) {
			ArrayList<Notification> notifications = program.getNotificationsToCurrentUser();
			for (Notification notification : notifications) {
				new NotificationWindow(program, notification);
			}
		} else if (e.getSource().equals(buttonCalendars)) {
			new SelectCalendars(program, calPanel);
		} else if (e.getSource().equals(buttonQuit)) {
			program.quit();
		}
	}

}
