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
import javax.swing.JTextArea;
import javax.swing.ListModel;

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

	private ArrayList<Room> availRoomList=new ArrayList<Room>();  //import from server
	private String[] testRooms ={temp.TestObjects.getRoom00().toString(),temp.TestObjects.getRoom01().toString()};
	private String[] availRooms;
	private JFrame frame;
	private JList<String> roomList; // TODO?
	private JButton closeButton;
	private JButton saveButton;
	private GridBagConstraints c;

	public RoomSelectionWindow() {
		
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
		roomList = new JList<String>(testRooms);
		
		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;
		
		setAvailRooms();
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
		}
	}
	
	public void setAvailRooms(){		
		availRooms=new String[availRoomList.size()];
		
		for (int i=0; i<availRoomList.size();i++){
			availRooms[i]=availRoomList.get(i).toString();
		}
	}
	
	public static void main(String[] args) {
		new RoomSelectionWindow();
	}
	
}
