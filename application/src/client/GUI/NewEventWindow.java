package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import client.Program;

import temp.TestObjects;


import data.Event;
import data.Alarm;
import data.Appointment;
import data.Calendar;
import data.Meeting;
import data.Room;
import data.User;
import data.XMLTranslator;

@SuppressWarnings("serial")
public class NewEventWindow extends JPanel implements ActionListener, ItemListener {
	private static final String FRAME_NAME = "Avtale / M�te";
	
	private static final String LABEL_DESCRIPTION = "Beskrivelse";
	private static final String LABEL_FROM = "Fra";
	private static final String LABEL_TO = "Til";
	private static final String LABEL_LOCATION = "Sted";
	private static final String LABEL_ALARM = "Alarm";
	private static final String LABEL_ALARM_TIME_BEFORE = "Tid f�r:";
	private static final String LABEL_MEETING = "M�te";
	private static final String LABEL_PARTICIPANTS = "Deltakere:";
	
	private static final String FIELD_DATE = "����-MM-DD";
	private static final String FIELD_TIME = "TT:MM";

	private static final String BUTTON_LOCATION_SET_TEXT = "Bruk: Tekst";
	private static final String BUTTON_LOCATION_SET_ROOM = "Bruk: Rom";
	private static final String BUTTON_DELETE = "Slett";
	private static final String BUTTON_CLOSE = "Lukk";
	private static final String BUTTON_SAVE = "Lagre";
	
	private static final String TOOLTIP_NOT_SAVED = "Avtalen/M�tet er ikke lagret. Trykk \"" + BUTTON_CLOSE + "\" for � avbryte";
	
	private static final int SIZE_FIELD = 12;
	
	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;

	
	private JFrame frame;
	private JLabel descriptionLabel, fromLabel, toLabel, locationLabel, alarmLabel, alarmTimeBeforeLabel, meetingLabel, participantsLabel;
	private JTextField descriptionField, fromDateField, fromTimeField, toDateField, toTimeField, locationField, alarmTimeBeforeField;
	private JCheckBox alarmCheckBox, meetingCheckBox;
	private JList<User> participantsList;
	private JButton locationButton, deleteButton, closeButton, saveButton;
	private GridBagConstraints c;

	private Event calendarEvent;
	private Calendar calendar;
	private User user;
	private User[] userArray;
	
	private CalendarPane calendarPane;

	private Program program;
	
	public NewEventWindow(Program program, CalendarPane calendarPane, Calendar calendar, User user, User[] userArray) {
		this.program = program;
		this.calendarPane = calendarPane;
		this.calendar = calendar;
		this.user = user;
		this.userArray = userArray;
		initFrame();
		initPanel();
		
		deleteButton.setEnabled(false);
		deleteButton.setToolTipText(TOOLTIP_NOT_SAVED);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public NewEventWindow(Program program, CalendarPane calendarPane, Event event, User[] userArray) {
		this.program = program;
		this.calendarPane = calendarPane;
		this.calendarEvent = event;
		this.calendar = event.getCalendar();
		this.userArray = userArray;
		initFrame();
		initPanel();
		if (event instanceof Appointment) {
			this.user = ((Appointment) event).getOwner();
		} else if (event instanceof Meeting) {
			this.user = ((Meeting) event).getLeader();
		}
		
		descriptionField.setText(event.getDescription());
		fromDateField.setText(event.getStartDateTime().split(" ")[0]);
		fromTimeField.setText(event.getStartDateTime().split(" ")[1]);
		toDateField.setText(event.getEndDateTime().split(" ")[0]);
		toTimeField.setText(event.getEndDateTime().split(" ")[1]);
		locationField.setText(event.getLocation());
		alarmCheckBox.setSelected(event.getAlarm() != null);
		if (alarmCheckBox.isSelected()) {
			alarmTimeBeforeField.setText(event.getAlarm().getTimeBefore());
		}
		if (event instanceof Meeting) {
			meetingCheckBox.setSelected(true);
			ArrayList<User> allInvitedUsers = new ArrayList<User>();
			allInvitedUsers.addAll(((Meeting) event).getUsersInvited());
			allInvitedUsers.addAll(((Meeting) event).getUsersAccepted());
			allInvitedUsers.addAll(((Meeting) event).getUsersDeclined());
			int[] indecies = new int[allInvitedUsers.size()];
			int i = 0;
			for (User user : allInvitedUsers) {
				indecies[i++] = Arrays.asList(userArray).indexOf(user);
			}
			participantsList.setSelectedIndices(indecies);
		}
		
		// TODO
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private void initFrame() {
		frame = new JFrame(FRAME_NAME);
		frame.setPreferredSize(new Dimension(300, 300)); // TODO temp
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		initComponents();

		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;

		addComponents();
		addListeners();
	}
	private void initComponents() {
		descriptionLabel = new JLabel(LABEL_DESCRIPTION);
		fromLabel = new JLabel(LABEL_FROM);
		toLabel = new JLabel(LABEL_TO);
		locationLabel = new JLabel(LABEL_LOCATION);
		alarmLabel = new JLabel(LABEL_ALARM);
		alarmTimeBeforeLabel = new JLabel(LABEL_ALARM_TIME_BEFORE);
		meetingLabel = new JLabel(LABEL_MEETING);
		participantsLabel = new JLabel(LABEL_PARTICIPANTS);

		descriptionField = new JTextField(SIZE_FIELD);
		fromDateField = new JTextField(FIELD_DATE);
		fromTimeField = new JTextField(FIELD_TIME);
		toDateField = new JTextField(FIELD_DATE);
		toTimeField = new JTextField(FIELD_TIME);
		locationField = new JTextField(SIZE_FIELD);
		alarmTimeBeforeField = new JTextField(FIELD_TIME);
		alarmTimeBeforeField.setEnabled(false);
		
		alarmCheckBox = new JCheckBox();
		meetingCheckBox = new JCheckBox();
		participantsList = new JList<User>(userArray);
		participantsList.setBackground(Color.WHITE);
		participantsList.setEnabled(false);
		participantsList.setCellRenderer(new UserRenderer());
		
		locationButton = new JButton(BUTTON_LOCATION_SET_ROOM);
		deleteButton = new JButton(BUTTON_DELETE);
		closeButton = new JButton(BUTTON_CLOSE);
		saveButton = new JButton(BUTTON_SAVE);
	}
	private void addComponents() {
		addComponent(descriptionLabel, 0, 0, 1, LINE_END);
		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(descriptionField, 1, 0, 3, LINE_START);
		c.fill = GridBagConstraints.NONE;
	
		addComponent(fromLabel, 0, 1, 1, LINE_END);
		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(fromDateField, 1, 1, 2, LINE_START);
		addComponent(fromTimeField, 3, 1, 1, LINE_START);
		c.fill = GridBagConstraints.NONE;
		
		addComponent(toLabel, 0, 2, 1, LINE_END);
		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(toDateField, 1, 2, 2, LINE_START);
		addComponent(toTimeField, 3, 2, 1, LINE_START);
		c.fill = GridBagConstraints.NONE;
		
		addComponent(locationLabel, 0, 3, 1, LINE_END);
		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(locationField, 1, 3, 2, LINE_START);
		c.fill = GridBagConstraints.NONE;
		addComponent(locationButton, 3, 3, 1, LINE_START);
		
		addComponent(alarmLabel, 0, 4, 1, LINE_END);
		addComponent(alarmCheckBox, 1, 4, 1, LINE_START);
		addComponent(alarmTimeBeforeLabel, 2, 4, 1, LINE_END);
		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(alarmTimeBeforeField, 3, 4, 1, LINE_START);
		c.fill = GridBagConstraints.NONE;
		
		addComponent(meetingLabel, 0, 5, 1, LINE_END);
		addComponent(meetingCheckBox, 1, 5, 1, LINE_START);
		
		addComponent(participantsLabel, 0, 6, 1, GridBagConstraints.FIRST_LINE_END);
		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(participantsList, 1, 6, 3, LINE_START);
		c.fill = GridBagConstraints.NONE;
		
		addComponent(deleteButton, 0, 7, 1, LINE_START);
		addComponent(closeButton, 2, 7, 1, LINE_START);
		addComponent(saveButton, 3, 7, 1, LINE_END);
	}
	private void addComponent(JComponent component, int gridx, int gridy, int gridwidth, int anchor) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.anchor = anchor;
		add(component, c);
	}
	private void addListeners() {
		alarmCheckBox.addItemListener(this);
		meetingCheckBox.addItemListener(this);
		locationButton.addActionListener(this);
		deleteButton.addActionListener(this);
		closeButton.addActionListener(this);
		saveButton.addActionListener(this);
	}
	
	public void setRoom(Room room) {
		// TODO add room to event? add room to this?
		// NB! Need to update database!!
		locationField.setText(room.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(deleteButton)) {
			// TODO delete the event, update database // use *calendarEvent*
			frame.dispose();
		} else if (e.getSource().equals(closeButton)) {
			frame.dispose();
		} else if (e.getSource().equals(locationButton)) {
			if (locationButton.getText().equals(BUTTON_LOCATION_SET_ROOM)) {
				new RoomSelectionWindow(this);
				locationField.setEnabled(false);
				locationButton.setText(BUTTON_LOCATION_SET_TEXT);
			} else if (locationButton.getText().equals(BUTTON_LOCATION_SET_TEXT)) {
				// TODO
				locationField.setEnabled(true);
				locationField.setText("");
				locationButton.setText(BUTTON_LOCATION_SET_ROOM);
			}
		} else if (e.getSource().equals(saveButton)) {
			Event calendarEvent;
			String startDateTime = fromDateField.getText() + " " + fromTimeField.getText();
			String endDateTime = toDateField.getText() + " " + toTimeField.getText();
			String description = descriptionField.getText();
			String location = locationField.getText();
			if (meetingCheckBox.isSelected()) {
				calendarEvent = new Meeting(0, calendar, startDateTime, endDateTime, description, location, user);
				for (User participant : participantsList.getSelectedValuesList()) {
					((Meeting)calendarEvent).inviteParticipant(participant);
				}
			} else {
				calendarEvent = new Appointment(0, calendar, startDateTime, endDateTime, description, location, user);
			}
			Alarm alarm = null;
			if(alarmCheckBox.isSelected()){
				alarm = new Alarm(alarmTimeBeforeField.getText(), "", user, calendarEvent);
				// TODO send the alarm to the database
			}
			calendarPane.addEvent(calendarEvent);
			// TODO save new event, set the message string for alarm
			XMLTranslator.toXML(calendarEvent);
			frame.dispose(); // Close if successful; show error message if not?
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(meetingCheckBox)) {
			participantsList.setEnabled(meetingCheckBox.isSelected());
		} else if (e.getSource().equals(alarmCheckBox)) {
			alarmTimeBeforeField.setEnabled(alarmCheckBox.isSelected());
		}
	}
	
	public static void main(String[] args) {
//		new NewEventWindow(TestObjects.getCalendar00(), TestObjects.getUser02(), TestObjects.getUserArray01());
		new NewEventWindow(new Program(), new CalendarPane(new Program()), TestObjects.getMeeting01(), TestObjects.getUserArray01());
	}


}
