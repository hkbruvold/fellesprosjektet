package client.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JList;

import client.Program;

import data.Group;
import data.User;

@SuppressWarnings("serial")
public class NewGroupWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldDescription;
	private JButton btnExit;
	private JButton btnSave;
	private Box horizontalBox;
	private JList<User> memberList;
	
	@SuppressWarnings("unused") 
	private Program program;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGroupWindow frame = new NewGroupWindow(new Program());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewGroupWindow(Program program) {
		this.program = program;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel labelName = new JLabel("Navn");
		GridBagConstraints gbc_labelName = new GridBagConstraints();
		gbc_labelName.anchor = GridBagConstraints.EAST;
		gbc_labelName.insets = new Insets(0, 0, 5, 5);
		gbc_labelName.gridx = 1;
		gbc_labelName.gridy = 1;
		contentPane.add(labelName, gbc_labelName);

		textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 3;
		gbc_textFieldName.gridy = 1;
		contentPane.add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);

		JLabel labelDescription = new JLabel("Beskrivelse");
		GridBagConstraints gbc_labelDescription = new GridBagConstraints();
		gbc_labelDescription.anchor = GridBagConstraints.EAST;
		gbc_labelDescription.insets = new Insets(0, 0, 5, 5);
		gbc_labelDescription.gridx = 1;
		gbc_labelDescription.gridy = 2;
		contentPane.add(labelDescription, gbc_labelDescription);

		textFieldDescription = new JTextField();
		GridBagConstraints gbc_textFieldDescription = new GridBagConstraints();
		gbc_textFieldDescription.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDescription.gridx = 3;
		gbc_textFieldDescription.gridy = 2;
		contentPane.add(textFieldDescription, gbc_textFieldDescription);
		textFieldDescription.setColumns(10);

		JLabel labelMembers = new JLabel("Medlemmer");
		GridBagConstraints gbc_labelMembers = new GridBagConstraints();
		gbc_labelMembers.anchor = GridBagConstraints.WEST;
		gbc_labelMembers.insets = new Insets(0, 0, 5, 5);
		gbc_labelMembers.gridx = 1;
		gbc_labelMembers.gridy = 3;
		contentPane.add(labelMembers, gbc_labelMembers);

		//todo Get all users from server
		ArrayList<User> users = new ArrayList<User>();
		//Temporary!
		users.add(temp.TestObjects.getUser00());
		users.add(temp.TestObjects.getUser01());
		users.add(temp.TestObjects.getUser02());
		User[] allusers = program.getAllUsers();
		memberList = new JList<User>(allusers);
		GridBagConstraints gbc_memberList = new GridBagConstraints();
		gbc_memberList.insets = new Insets(0, 0, 5, 5);
		gbc_memberList.fill = GridBagConstraints.BOTH;
		gbc_memberList.gridx = 3;
		gbc_memberList.gridy = 3;
		contentPane.add(memberList, gbc_memberList);

		horizontalBox = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalBox.gridx = 3;
		gbc_horizontalBox.gridy = 7;
		contentPane.add(horizontalBox, gbc_horizontalBox);

		btnSave = new JButton("Lagre");
		horizontalBox.add(btnSave);

		btnExit = new JButton("Lukk");
		horizontalBox.add(btnExit);
	}
	public NewGroupWindow(Group group, String name, String description, ArrayList<User> members) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel labelName = new JLabel("Navn");
		GridBagConstraints gbc_labelName = new GridBagConstraints();
		gbc_labelName.anchor = GridBagConstraints.EAST;
		gbc_labelName.insets = new Insets(0, 0, 5, 5);
		gbc_labelName.gridx = 1;
		gbc_labelName.gridy = 1;
		contentPane.add(labelName, gbc_labelName);

		textFieldName = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		contentPane.add(textFieldName, gbc_textField);
		textFieldName.setColumns(10);
		textFieldName.setText(name);

		JLabel labelDescription = new JLabel("Beskrivelse");
		GridBagConstraints gbc_labelDescription = new GridBagConstraints();
		gbc_labelDescription.anchor = GridBagConstraints.EAST;
		gbc_labelDescription.insets = new Insets(0, 0, 5, 5);
		gbc_labelDescription.gridx = 1;
		gbc_labelDescription.gridy = 2;
		contentPane.add(labelDescription, gbc_labelDescription);

		textFieldDescription = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 2;
		contentPane.add(textFieldDescription, gbc_textField_1);
		textFieldDescription.setColumns(10);
		textFieldDescription.setText(description);

		JLabel labelMembers = new JLabel("Medlemmer");
		GridBagConstraints gbc_labelMembers = new GridBagConstraints();
		gbc_labelMembers.anchor = GridBagConstraints.WEST;
		gbc_labelMembers.insets = new Insets(0, 0, 5, 5);
		gbc_labelMembers.gridx = 1;
		gbc_labelMembers.gridy = 3;
		contentPane.add(labelMembers, gbc_labelMembers);


		memberList = new JList<User>((User[]) members.toArray());
		GridBagConstraints gbc_memberList = new GridBagConstraints();
		gbc_memberList.insets = new Insets(0, 0, 5, 5);
		gbc_memberList.fill = GridBagConstraints.BOTH;
		gbc_memberList.gridx = 3;
		gbc_memberList.gridy = 3;
		contentPane.add(memberList, gbc_memberList);

		horizontalBox = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalBox.gridx = 3;
		gbc_horizontalBox.gridy = 7;
		contentPane.add(horizontalBox, gbc_horizontalBox);

		btnSave = new JButton("Lagre");
		horizontalBox.add(btnSave);

		btnExit = new JButton("Lukk");
		horizontalBox.add(btnExit);
	}

}
