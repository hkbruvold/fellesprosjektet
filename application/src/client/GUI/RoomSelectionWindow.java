package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import client.Program;
import data.Room;

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
	private Program program;
	private int numOfParticipants;

	public RoomSelectionWindow(Program program) { // Temporary; For testing
		this.program = program;
		availRoomList = new ArrayList<Room>();
		initFrame();
		initPanel();
		frame.pack();
		frame.setVisible(true);
	}
	public RoomSelectionWindow(NewEventWindow newEventWindow, Program program, int numOfParticipants) {
		this.newEventWindow = newEventWindow;
		this.program = program;
		this.numOfParticipants = numOfParticipants;
		availRoomList = new ArrayList<Room>();
		initFrame();
		initPanel();

		frame.pack();
		frame.setVisible(true);
	}

	private void initFrame() {
		frame = new JFrame(FRAME_NAME);
		frame.setPreferredSize(new Dimension(250, 150)); // temp?
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
		ArrayList<Room> allRooms = program.getAllRooms();
		for(Room room : allRooms){
			if(room.getSize()>= numOfParticipants){
				availRoomList.add(room);
			}
		}
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
		new RoomSelectionWindow(new Program());
	}

}
