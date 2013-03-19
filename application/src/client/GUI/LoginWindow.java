package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.xml.XMLConstants;

import client.Program;
import client.ServerCommunication;

import data.Authentication;
import data.XMLTranslator;

@SuppressWarnings("serial")
public class LoginWindow extends JPanel implements ActionListener {
	private static final int SIZE_FIELD = 15;

	private static final String FRAME_NAME = "Logg inn";

	private static final String LABEL_USERNAME = "Brukernavn";
	private static final String LABEL_PASSWORD = "Passord";
	
	private static final String BUTTON_CLOSE = "Lukk";
	private static final String BUTTON_LOG_IN = "Logg inn";
	
	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;
	private JFrame frame;
	private JLabel usernameLabel, passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton closeButton, logInButton;
	private GridBagConstraints c;

	private Program program;

	public LoginWindow(Program program){
		this.program = program;
		initFrame();
		initPanel();

		frame.pack();
		frame.setVisible(true);
	}

	private void initFrame() {
		frame = new JFrame(FRAME_NAME);
		frame.setPreferredSize(new Dimension(300, 130));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		usernameLabel = new JLabel(LABEL_USERNAME);
		passwordLabel = new JLabel(LABEL_PASSWORD);
		usernameField = new JTextField(SIZE_FIELD);
		passwordField = new JPasswordField(SIZE_FIELD);
		closeButton = new JButton(BUTTON_CLOSE);
		logInButton = new JButton(BUTTON_LOG_IN);

		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;

		addComponent(usernameLabel, 0, 0, 1, LINE_END);
		addComponent(usernameField, 1, 0, 2, LINE_START);
		addComponent(passwordLabel, 0, 1, 1, LINE_END);
		addComponent(passwordField, 1, 1, 2, LINE_START);
		addComponent(closeButton, 1, 2, 1, LINE_START); // Hmmm...
		addComponent(logInButton, 2, 2, 1, LINE_END);   // Hmmm...

		usernameField.addActionListener(this);
		passwordField.addActionListener(this);
		closeButton.addActionListener(this);
		logInButton.addActionListener(this);
	}

	private void addComponent(JComponent component, int gridx, int gridy, int gridwidth, int anchor) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.anchor = anchor;
		add(component, c);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(usernameField)) {
			// TODO
		} else if (e.getSource().equals(passwordField)) {
			// TODO
		} else if (e.getSource().equals(closeButton)) {
			frame.dispose();
		} else if (e.getSource().equals(logInButton)) {
			ServerCommunication sc = new ServerCommunication("TODO"); // TODO
			sc.login(usernameField.getText(),passwordField.getPassword().toString());
			frame.dispose(); // Close if successful; show error message if not?
		}
	}

	public static void main(String[] args){
		new LoginWindow(new Program());
	}
}
