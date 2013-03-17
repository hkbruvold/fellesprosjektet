package client.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.Box;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Dimension;

public class MainWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1125, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		Box verticalBox = Box.createVerticalBox();
		GridBagConstraints gbc_verticalBox = new GridBagConstraints();
		gbc_verticalBox.insets = new Insets(0, 0, 5, 5);
		gbc_verticalBox.gridx = 0;
		gbc_verticalBox.gridy = 0;
		contentPane.add(verticalBox, gbc_verticalBox);
		
		JButton btnNewEvent = new JButton("New Event");
		verticalBox.add(btnNewEvent);
		
		JButton btnMessages = new JButton("Messages");
		verticalBox.add(btnMessages);
		
		JButton btnViewCalendars = new JButton("View Calendars");
		verticalBox.add(btnViewCalendars);
		
		Box verticalBox_1 = Box.createVerticalBox();
		GridBagConstraints gbc_verticalBox_1 = new GridBagConstraints();
		gbc_verticalBox_1.fill = GridBagConstraints.BOTH;
		gbc_verticalBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalBox_1.gridx = 0;
		gbc_verticalBox_1.gridy = 2;
		contentPane.add(verticalBox_1, gbc_verticalBox_1);
		
		JButton btnJanuary = new JButton("January");
		verticalBox_1.add(btnJanuary);
		
		JButton btnFebruary = new JButton("February");
		verticalBox_1.add(btnFebruary);
		
		JButton btnMarch = new JButton("March");
		verticalBox_1.add(btnMarch);
		
		JButton btnApril = new JButton("April");
		verticalBox_1.add(btnApril);
		
		JButton btnMay = new JButton("May");
		verticalBox_1.add(btnMay);
		
		JButton btnJune = new JButton("June");
		verticalBox_1.add(btnJune);
		
		JButton btnJuly = new JButton("July");
		verticalBox_1.add(btnJuly);
		
		JButton btnAugust = new JButton("August");
		verticalBox_1.add(btnAugust);
		
		JButton btnSeptember = new JButton("September");
		verticalBox_1.add(btnSeptember);
		
		JButton btnOctober = new JButton("October");
		verticalBox_1.add(btnOctober);
		
		JButton btnNovember = new JButton("November");
		verticalBox_1.add(btnNovember);
		
		JButton btnDecember = new JButton("December");
		verticalBox_1.add(btnDecember);
	}

}
