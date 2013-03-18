package client.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import temp.TestObjects;

import data.Notification;
import data.Room;
import data.User;

@SuppressWarnings("serial")
public class RoomSelectionWindow extends JPanel implements ActionListener {
	private static final String FRAME_NAME = "Ledige rom"; 
	private static final String BUTTON_CLOSE = "Lukk";
	private static final String BUTTON_SAVE = "Lagre";

	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;

	private ArrayList<Room> availRoomList;
	private JFrame frame;
	private JList<Room> roomList = new JList<Room>();
	private JButton closeButton;
	private JButton saveButton;
	private GridBagConstraints c;

	private NewEventWindow newEventWindow;
	
	public RoomSelectionWindow() { // Temporary; For testing
		initFrame();
		initPanel();
		frame.pack();
		frame.setVisible(true);
	}
	public RoomSelectionWindow(NewEventWindow newEventWindow) {
		this.newEventWindow = newEventWindow;
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
		
		saveButton = new JButton(BUTTON_SAVE);
		closeButton = new JButton(BUTTON_CLOSE);
		roomList = new JList<Room>(getRoomArray());
		roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;
		
		addComponent(roomList, 0, 0, 1, LINE_END);
		addComponent(closeButton, 0, 1, 1, LINE_START);
		addComponent(saveButton, 1, 1, 1, LINE_END);
		
		closeButton.addActionListener(this);
		saveButton.addActionListener(this);
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
			frame.dispose();
		} else if (e.getSource().equals(saveButton)) {
			newEventWindow.setRoom(roomList.getSelectedValue());
			frame.dispose();
		}
	}
	
	private Room[] getRoomArray() {
		getAvailableRooms();
		return makeRoomArray();
	}
	private void getAvailableRooms() {
		availRoomList = new ArrayList<Room>();
		// TODO get from server (and database)
		availRoomList.add(TestObjects.getRoom00()); // Temporary!
		availRoomList.add(TestObjects.getRoom01()); // Temporary!
		availRoomList.add(TestObjects.getRoom02()); // Temporary!
	}
	private Room[] makeRoomArray() {
		Room[] rooms = new Room[availRoomList.size()];
		int i = 0;
		for (Room room : availRoomList) {
			rooms[i++] = room;
		}
		return rooms;
	}
	
	public static void main(String[] args) {
		new RoomSelectionWindow();
	}
	
}
