package client.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import client.Program;

import temp.TestObjects;

import data.Event;
import data.Room;
import data.User;

@SuppressWarnings("serial")
public class SelectCalendars extends JPanel implements ActionListener {
	private static final String FRAME_NAME = "Velg kalendere"; 
	private static final String BUTTON_CLOSE = "Lukk";

	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;

	private ArrayList<User> userArrayList;
	private JFrame frame;
	private JList<User> userList = new JList<User>();
	private JButton closeButton;
	private GridBagConstraints c;

	private Program program;
	private CalendarPane calPane;

	public SelectCalendars(Program program, CalendarPane calPane) {
		this.program = program;
		this.calPane = calPane;
		userArrayList = new ArrayList<User>();
		for (String i: program.getUserList().keySet()) {
			userArrayList.add(program.getUserList().get(i));
		}
		initFrame();
		initPanel();
		frame.pack();
		frame.setVisible(true);
	}

	private void initFrame() {
		frame = new JFrame(FRAME_NAME);
		frame.setPreferredSize(new Dimension(250, 150)); // TODO?
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		closeButton = new JButton(BUTTON_CLOSE);
		userList = new JList<User>(makeUserArray());
		userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;

		addComponent(userList, 0, 0, 1, LINE_END);
		addComponent(closeButton, 0, 1, 1, LINE_START);

		closeButton.addActionListener(this);
	}
	private void addComponent(JComponent component, int gridx, int gridy, int gridwidth, int anchor) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.anchor = anchor;
		add(component, c);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(closeButton)) {
			calPane.setShowUserCalendars(userList.getSelectedValuesList());
			frame.dispose();
		}
	}
	
	private User[] makeUserArray() {
		User[] users = new User[userArrayList.size()];
		int i = 0;
		for (User user : userArrayList) {
			users[i++] = user;
		}
		return users;
	}
}
