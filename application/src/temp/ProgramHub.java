package temp;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.*;
import client.GUI.*;
import server.*;

public class ProgramHub extends JPanel implements ActionListener {
	private static final String FRAME_NAME = "Program hub";
	
	private static final String BUTTON_LOGIN = "LogIn"; 
	private static final String BUTTON_MEETING_INVITATION = "MeetingInvitation"; 
	private static final String BUTTON_NEW_EVENT = "NewEvent";
	private static final String BUTTON_NOTIFICATION = "Notification";
	private static final String BUTTON_TEST_DATABASE = "TestDatabase";
	private static final String BUTTON_CLOSE = "Close";
	
	private JFrame frame;
	private JButton loginButton, meetingInvitationButton, newEventButton, notificationButton, testDatabaseButton, closeButton;
	private GridBagConstraints c;
	
	public ProgramHub() {
		initFrame();
		initPanel();
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private void initFrame() {
		frame = new JFrame(FRAME_NAME);
		frame.setPreferredSize(new Dimension(400, 150));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		loginButton = new JButton(BUTTON_LOGIN);
		meetingInvitationButton = new JButton(BUTTON_MEETING_INVITATION);
		newEventButton = new JButton(BUTTON_NEW_EVENT);
		notificationButton = new JButton(BUTTON_NOTIFICATION);
		testDatabaseButton = new JButton(BUTTON_TEST_DATABASE);
		closeButton = new JButton(BUTTON_CLOSE);
		
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 10;
		
		addComponent(loginButton, 0, 0, 1);
		addComponent(meetingInvitationButton, 1, 0, 1);
		addComponent(newEventButton, 2, 0, 1);
		addComponent(notificationButton, 0, 1, 1);
		addComponent(testDatabaseButton, 1, 1, 1);

		c.insets = new Insets(5,0,0,0);
		addComponent(closeButton, 0, 2, 1);
		
		loginButton.addActionListener(this);
		meetingInvitationButton.addActionListener(this);
		newEventButton.addActionListener(this);
		notificationButton.addActionListener(this);
		testDatabaseButton.addActionListener(this);
		closeButton.addActionListener(this);
	}
	private void addComponent(JComponent component, int gridx, int gridy, int gridwidth) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		add(component, c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(closeButton)) {
			frame.dispose();
		} else if (e.getSource().equals(loginButton)) {
			new LoginWindow();
		} else {
			if (e.getSource().equals(meetingInvitationButton)) {
				Calendar calendar = new Calendar("cal", new User("Ola"));
				String startDateTime = "2013-03-10 10:10";
				String endDateTime = "2013-03-10 10:15";
				String description = "Description";
				String location = "Location";
				Alarm alarm = new Alarm("2013-03-10", "10:10");
				User leader = new User("Kari");
				Meeting meeting = new Meeting(calendar, startDateTime, endDateTime, description, location, alarm, leader);
				new MeetingInvitationWindow(meeting);
			} else if (e.getSource().equals(newEventButton)) {
				User user = new User("Ola");
				Calendar calendar = new Calendar("cal", user);
				new NewEventWindow(calendar, user);
			} else if (e.getSource().equals(notificationButton)) {
				new NotificationWindow(new Notification("Message"));
			} else if (e.getSource().equals(testDatabaseButton)) {
				new TempTestDatabase();
			}
		}
	}
	
	public static void main(String[] args) {
		new ProgramHub();
	}
}
