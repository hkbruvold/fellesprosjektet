package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import temp.TestObjects;

import client.*;
import data.Meeting;

@SuppressWarnings("serial")
public class MeetingInvitationWindow extends JPanel implements ActionListener {
	private static final String FRAME_NAME = "Invitasjon til møte"; 
	
	private static final String LABEL_DESCRIPTION = "Beskrivelse";
	private static final String LABEL_START_TIME = "Starttidspunkt";
	private static final String LABEL_END_TIME = "Sluttidspunkt";
	private static final String LABEL_LOCATION = "Sted";
	private static final String LABEL_LEADER = "Leder";
	
	private static final String BUTTON_CLOSE = "Svar senere";
	private static final String BUTTON_DECLINE = "Avslå";
	private static final String BUTTON_ACCEPT = "Godta";

	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;

	private Meeting meeting;
	private JFrame frame;
	private JLabel descriptionLabel, startTimeLabel, endTimeLabel, locationLabel, leaderLabel;
	private JTextField descriptionField, startTimeField, endTimeField, locationField, leaderField;
	private JButton closeButton, declineButton, acceptButton;
	private GridBagConstraints c;
	
	public MeetingInvitationWindow(Meeting meeting) {
		this.meeting = meeting;
		
		initFrame();
		initPanel();
		
		frame.pack();
		frame.setVisible(true);
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

		descriptionField = createField(descriptionField, meeting.getDescription());
		startTimeField = createField(startTimeField, meeting.getStartDateTime());
		endTimeField = createField(endTimeField, meeting.getEndDateTime());
		locationField = createField(locationField, meeting.getLocation());
		leaderField = createField(leaderField, meeting.getLeader().getName());
		
		closeButton = new JButton(BUTTON_CLOSE);
		declineButton = new JButton(BUTTON_DECLINE);
		acceptButton = new JButton(BUTTON_ACCEPT);
		
		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;
		
		addComponent(descriptionLabel, 0, 0, 1, LINE_END);
		addComponent(startTimeLabel, 0, 1, 1, LINE_END);
		addComponent(endTimeLabel, 0, 2, 1, LINE_END);
		addComponent(locationLabel, 0, 3, 1, LINE_END);
		addComponent(leaderLabel, 0, 4, 1, LINE_END);

		c.fill = GridBagConstraints.HORIZONTAL;
		addComponent(descriptionField, 1, 0, 2, LINE_START);
		addComponent(startTimeField, 1, 1, 2, LINE_START);
		addComponent(endTimeField, 1, 2, 2, LINE_START);
		addComponent(locationField, 1, 3, 2, LINE_START);
		addComponent(leaderField, 1, 4, 2, LINE_START);
		c.fill = GridBagConstraints.NONE;
		
		addComponent(closeButton, 0, 5, 1, LINE_END);
		addComponent(declineButton, 1, 5, 1, LINE_END);
		addComponent(acceptButton, 2, 5, 1, LINE_END);
		
		closeButton.addActionListener(this);
		declineButton.addActionListener(this);
		acceptButton.addActionListener(this);
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
			meeting.declineInvite(user);
			frame.dispose();
		} else if (e.getSource().equals(acceptButton)) {
			meeting.acceptInvite(user);
			frame.dispose();
		}
	}

	public static void main(String[] args) {
		new MeetingInvitationWindow(TestObjects.getMeeting00());
	}
	
}
