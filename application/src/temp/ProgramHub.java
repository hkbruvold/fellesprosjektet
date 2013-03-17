package temp;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.GUI.*;
import server.*;

@SuppressWarnings("serial")
public class ProgramHub extends JPanel implements ActionListener {
	private static final String FRAME_NAME = "Program hub";

	private static final String BUTTON_LOGIN = "LogIn"; 
	private static final String BUTTON_MEETING_INVITATION = "MeetingInvitation"; 
	private static final String BUTTON_NEW_EVENT = "NewEvent";
	private static final String BUTTON_OLD_EVENT = "NewEvent (see tool tip)";
	private static final String BUTTON_NOTIFICATION = "Notification";
	private static final String BUTTON_TEST_DATABASE = "TestDatabase";
	private static final String BUTTON_CLOSE = "Close";
	
	private static final String TOOLTIP_BUTTON_OLD_EVENT = "NewEventWindow with a previously made event. Not yet fully implemented";

	private JFrame frame;
	private JButton loginButton, meetingInvitationButton, newEventButton, oldEventButton, notificationButton, testDatabaseButton, closeButton;
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
		oldEventButton = new JButton(BUTTON_OLD_EVENT);
		closeButton = new JButton(BUTTON_CLOSE);
		
		oldEventButton.setToolTipText(TOOLTIP_BUTTON_OLD_EVENT);

		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 10;

		addComponent(loginButton, 0, 0, 1);
		addComponent(meetingInvitationButton, 1, 0, 1);
		addComponent(newEventButton, 2, 0, 1);
		addComponent(notificationButton, 0, 1, 1);
		addComponent(testDatabaseButton, 1, 1, 1);
		addComponent(oldEventButton, 2, 1, 1);

		c.insets = new Insets(5,0,0,0);
		addComponent(closeButton, 0, 2, 1);

		loginButton.addActionListener(this);
		meetingInvitationButton.addActionListener(this);
		newEventButton.addActionListener(this);
		notificationButton.addActionListener(this);
		testDatabaseButton.addActionListener(this);
		oldEventButton.addActionListener(this);
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
		} else if (e.getSource().equals(meetingInvitationButton)) {
			new MeetingInvitationWindow(TestObjects.getMeeting00(),TestObjects.getUser01());
		} else if (e.getSource().equals(newEventButton)) {
			new NewEventWindow(TestObjects.getCalendar00(), TestObjects.getUser00(), TestObjects.getUserArray01());
		} else if (e.getSource().equals(notificationButton)) {
			new NotificationWindow(TestObjects.getNotification00());
		} else if (e.getSource().equals(testDatabaseButton)) {
			new TempTestDatabase();
		} else if (e.getSource().equals(oldEventButton)) {
			new NewEventWindow(TestObjects.getMeeting01(), TestObjects.getUserArray01());
		}
	}

	public static void main(String[] args) {
		new ProgramHub();
	}
}
