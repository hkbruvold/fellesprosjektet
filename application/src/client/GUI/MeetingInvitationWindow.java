package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import temp.TestObjects;

import client.*;
import data.Alarm;
import data.Meeting;
import data.MeetingReply;
import data.User;

@SuppressWarnings("serial")
public class MeetingInvitationWindow extends JPanel implements ActionListener, ItemListener {
	private static final String FRAME_NAME = "Invitasjon til m�te"; 

	private static final String LABEL_DESCRIPTION = "Beskrivelse";
	private static final String LABEL_START_TIME = "Starttidspunkt";
	private static final String LABEL_END_TIME = "Sluttidspunkt";
	private static final String LABEL_LOCATION = "Sted";
	private static final String LABEL_LEADER = "Leder";
	private static final String LABEL_ALARM = "Legg til alarm:";
	private static final String LABEL_ALARM_TIME_BEFORE = "Tid før:";

	private static final String FIELD_TIME = "HH:MM";

	private static final String BUTTON_CLOSE = "Svar senere";
	private static final String BUTTON_DECLINE = "Avsl�";
	private static final String BUTTON_ACCEPT = "Godta";

	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;

	private Meeting meeting;
	private Event model;
	private User user;
	private JFrame frame;
	private JLabel descriptionLabel, startTimeLabel, endTimeLabel, locationLabel, leaderLabel, alarmLabel, alarmTimeBeforeLabel;
	private JTextField descriptionField, startTimeField, endTimeField, locationField, leaderField, alarmTimeBeforeField;
	private JButton closeButton, declineButton, acceptButton;
	private GridBagConstraints c;
	private JCheckBox alarmCheckBox;

	private Program program;

	public MeetingInvitationWindow(Program program, Meeting meeting, User user) {
		this.program = program;
		this.meeting = meeting;
		this.user = user;
		initFrame();
		initPanel();

		frame.pack();
		frame.setVisible(true);
	}
	public Event getModel() {
		return model;
	}

	private void initFrame() {
		frame = new JFrame(FRAME_NAME);
		frame.setPreferredSize(new Dimension(300, 220));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		descriptionLabel = new JLabel(LABEL_DESCRIPTION);
		startTimeLabel = new JLabel(LABEL_START_TIME);
		endTimeLabel = new JLabel(LABEL_END_TIME);
		locationLabel = new JLabel(LABEL_LOCATION);
		leaderLabel = new JLabel(LABEL_LEADER);
		alarmLabel = new JLabel(LABEL_ALARM);
		alarmTimeBeforeLabel = new JLabel(LABEL_ALARM_TIME_BEFORE);

		descriptionField = createField(descriptionField, meeting.getDescription());
		startTimeField = createField(startTimeField, meeting.getStartDateTime());
		endTimeField = createField(endTimeField, meeting.getEndDateTime());
		locationField = createField(locationField, meeting.getLocation());
		leaderField = createField(leaderField, meeting.getLeader().getName());
		alarmTimeBeforeField = new JTextField(FIELD_TIME);
		alarmTimeBeforeField.setEnabled(false);

		closeButton = new JButton(BUTTON_CLOSE);
		declineButton = new JButton(BUTTON_DECLINE);
		acceptButton = new JButton(BUTTON_ACCEPT);

		alarmCheckBox = new JCheckBox();

		c.insets = new Insets(0,0,6,0);
		c.ipadx = 10;

		addComponent(descriptionLabel, 0, 0, 1, LINE_END);
		addComponent(startTimeLabel, 0, 1, 1, LINE_END);
		addComponent(endTimeLabel, 0, 2, 1, LINE_END);
		addComponent(locationLabel, 0, 3, 1, LINE_END);
		addComponent(leaderLabel, 0, 4, 1, LINE_END);

		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(descriptionField, 1, 0, 3, LINE_START);
		addComponent(startTimeField, 1, 1, 3, LINE_START);
		addComponent(endTimeField, 1, 2, 3, LINE_START);
		addComponent(locationField, 1, 3, 3, LINE_START);
		addComponent(leaderField, 1, 4, 3, LINE_START);

		addComponent(alarmLabel, 0, 5, 1, LINE_START);
		addComponent(alarmCheckBox, 1, 5, 1, LINE_START);
		addComponent(alarmTimeBeforeLabel, 2, 5, 1, LINE_START);
		addComponent(alarmTimeBeforeField, 3, 5, 1, LINE_START);
		c.fill = GridBagConstraints.NONE;

		addComponent(closeButton, 0, 6, 1, LINE_END);
		addComponent(declineButton, 2, 6, 1, LINE_END);
		addComponent(acceptButton, 3, 6, 1, LINE_END);

		closeButton.addActionListener(this);
		declineButton.addActionListener(this);
		acceptButton.addActionListener(this);
		alarmCheckBox.addItemListener(this);
	}
	private JTextField createField(JTextField field, String text) {
		field = new JTextField(text);
		field.setEditable(false);
		field.setBackground(frame.getBackground());
		return field;
	}
	private void addComponent(JComponent component, int gridx, int gridy, int gridwidth, int anchor) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.anchor = anchor;
		add(component, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(closeButton)) {
			// TODO ?
					frame.dispose();
		} else if (e.getSource().equals(declineButton)) {
			// TODO Set user based on the user of the program
			MeetingReply meetingReply = new MeetingReply(user, meeting, "DECLINED");
			System.out.println(meetingReply.toString());
			program.updateStatus(meetingReply);
			meeting.declineInvite(user);
			System.out.println(meeting.toString());
			frame.dispose();
		} else if (e.getSource().equals(acceptButton)) {
			meeting.acceptInvite(user);
			System.out.println(meeting.toString());
			frame.dispose();
			if(alarmCheckBox.isSelected()){
				Alarm alarm = new Alarm(alarmTimeBeforeField.getText(), "", user, meeting);
				program.registerAlarm(alarm);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(alarmCheckBox)) {
			alarmTimeBeforeField.setEnabled(alarmCheckBox.isSelected());
		}
	}

	public static void main(String[] args) {
		new MeetingInvitationWindow(new Program(), TestObjects.getMeeting00(),TestObjects.getUser00());
	}

}
