package client.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;

import temp.TestObjects;

public class MainWindow extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 1024, 576);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{69, 66, 64, 0, 12, 103, 68, 43, 234, 97, 109, 0};
		gridBagLayout.rowHeights = new int[]{33, 23, 427, 23, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel chooseWeekLabel = new JLabel("Week:");
		GridBagConstraints gbc_chooseWeekLabel = new GridBagConstraints();
		gbc_chooseWeekLabel.insets = new Insets(0, 0, 5, 5);
		gbc_chooseWeekLabel.gridx = 2;
		gbc_chooseWeekLabel.gridy = 1;
		getContentPane().add(chooseWeekLabel, gbc_chooseWeekLabel);
		
		final JComboBox weekNumberBox = new JComboBox();
		weekNumberBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarPane.setWeek(weekNumberBox.getSelectedIndex());
				CalendarPane.updateDates();
			}
		});
		weekNumberBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53"}));
		GridBagConstraints gbc_weekNumberBox = new GridBagConstraints();
		gbc_weekNumberBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_weekNumberBox.insets = new Insets(0, 0, 5, 5);
		gbc_weekNumberBox.gridx = 3;
		gbc_weekNumberBox.gridy = 1;
		getContentPane().add(weekNumberBox, gbc_weekNumberBox);
		
		JLabel lblYear = new JLabel("Year:");
		GridBagConstraints gbc_lblYear = new GridBagConstraints();
		gbc_lblYear.insets = new Insets(0, 0, 5, 5);
		gbc_lblYear.gridx = 6;
		gbc_lblYear.gridy = 1;
		getContentPane().add(lblYear, gbc_lblYear);
		
		JComboBox yearNumberBox = new JComboBox();
		yearNumberBox.setModel(new DefaultComboBoxModel(new String[] {"2013","2014","2015","2016","2017"}));

		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 7;
		gbc_comboBox.gridy = 1;
		getContentPane().add(yearNumberBox, gbc_comboBox);
		
		Box verticalBox = Box.createVerticalBox();
		GridBagConstraints gbc_verticalBox = new GridBagConstraints();
		gbc_verticalBox.anchor = GridBagConstraints.NORTH;
		gbc_verticalBox.insets = new Insets(0, 0, 5, 5);
		gbc_verticalBox.gridx = 0;
		gbc_verticalBox.gridy = 2;
		getContentPane().add(verticalBox, gbc_verticalBox);
		
		JButton btnnewEvent = new JButton("New Event");
		btnnewEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new NewEventWindow(TestObjects.getCalendar00(), TestObjects.getUser00(), TestObjects.getUserArray01());
			}
		});
		verticalBox.add(btnnewEvent);
		
		JButton btnMessages = new JButton("Messages");
		verticalBox.add(btnMessages);
		
		JButton btnViewCalendars = new JButton("View Calendars");
		verticalBox.add(btnViewCalendars);
		
		CalendarPane calendarPane = new CalendarPane();
		GridBagConstraints gbc_calendarPane = new GridBagConstraints();
		gbc_calendarPane.fill = GridBagConstraints.BOTH;
		gbc_calendarPane.insets = new Insets(0, 0, 5, 0);
		gbc_calendarPane.gridwidth = 10;
		gbc_calendarPane.gridx = 1;
		gbc_calendarPane.gridy = 2;
		getContentPane().add(calendarPane, gbc_calendarPane);
		
		JButton logOutButton = new JButton("Log out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_logOutButton = new GridBagConstraints();
		gbc_logOutButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_logOutButton.insets = new Insets(0, 0, 0, 5);
		gbc_logOutButton.gridx = 0;
		gbc_logOutButton.gridy = 3;
		getContentPane().add(logOutButton, gbc_logOutButton);
	}
}