package client.GUI;

import java.awt.*;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import client.Program;
import data.Group;

@SuppressWarnings("serial")
public class GroupOverviewWindow extends JFrame {
	private JPanel contentPane;
	
	@SuppressWarnings("unused") 
	private Program program;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupOverviewWindow frame = new GroupOverviewWindow(new Program());
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
	public GroupOverviewWindow(Program program) {
		this.program = program;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblGroups = new JLabel("Select Group");
		GridBagConstraints gbc_lblGroups = new GridBagConstraints();
		gbc_lblGroups.insets = new Insets(0, 0, 5, 5);
		gbc_lblGroups.anchor = GridBagConstraints.EAST;
		gbc_lblGroups.gridx = 0;
		gbc_lblGroups.gridy = 1;
		contentPane.add(lblGroups, gbc_lblGroups);
		//todo pull grouplist from server
		Group[] fetchedGroups = program.getAllGroups();
		JComboBox<Group> comboBox = new JComboBox<Group>(fetchedGroups);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		contentPane.add(comboBox, gbc_comboBox);

		Box horizontalBox = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.gridx = 1;
		gbc_horizontalBox.gridy = 5;
		contentPane.add(horizontalBox, gbc_horizontalBox);

		JButton btnNewButton_1 = new JButton("Edit Group");
		horizontalBox.add(btnNewButton_1);

		JButton btnNewButton = new JButton("Delete Group");
		horizontalBox.add(btnNewButton);
	}

}
