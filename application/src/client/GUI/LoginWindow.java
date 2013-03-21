package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.Program;
import data.Authentication;

@SuppressWarnings("serial")
public class LoginWindow extends JPanel implements ActionListener {
	private static final int SIZE_FIELD = 15;

	private static final String FRAME_NAME = "Logg inn";

	private static final String LABEL_WRONG_USERNAME_PASSWORD = "Feil brukernavn eller passord";
	private static final String LABEL_ERROR_CONNECTION = "Feil i oppkobling til server";
	private static final String LABEL_USERNAME = "Brukernavn";
	private static final String LABEL_PASSWORD = "Passord";

	private static final String BUTTON_CLOSE = "Lukk";
	private static final String BUTTON_LOG_IN = "Logg inn";

	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;
	private JFrame frame;
	private JLabel usernameLabel, passwordLabel, statusLabel;
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
		frame.setPreferredSize(new Dimension(300, 150));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		statusLabel = new JLabel();
		usernameLabel = new JLabel(LABEL_USERNAME);
		passwordLabel = new JLabel(LABEL_PASSWORD);
		usernameField = new JTextField(SIZE_FIELD);
		passwordField = new JPasswordField(SIZE_FIELD);
		closeButton = new JButton(BUTTON_CLOSE);
		logInButton = new JButton(BUTTON_LOG_IN);

		statusLabel.setForeground(Color.red);

		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;

		addComponent(statusLabel, 0, 0, 3, LINE_END);
		addComponent(usernameLabel, 0, 1, 1, LINE_END);
		addComponent(usernameField, 1, 1, 2, LINE_START);
		addComponent(passwordLabel, 0, 2, 1, LINE_END);
		addComponent(passwordField, 1, 2, 2, LINE_START);
		addComponent(closeButton, 1, 3, 1, LINE_START); // Hmmm...
		addComponent(logInButton, 2, 3, 1, LINE_END);   // Hmmm...

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

	public void showWrongLoginLabel() {
		statusLabel.setText(LABEL_WRONG_USERNAME_PASSWORD);
	}

	public void showErrorConnecting() {
		statusLabel.setText(LABEL_ERROR_CONNECTION);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(passwordField)) {
			tryLogin();
		} else if (e.getSource().equals(closeButton)) {
			frame.dispose();
			program.quit();
		} else if (e.getSource().equals(logInButton)) {
			tryLogin();
		}
	}
	private void tryLogin() {
		String username = usernameField.getText();
		char[] password = passwordField.getPassword();
		StringBuilder sb = new StringBuilder();
		for (char c : password) {
			sb.append(c);
		}
		if (program.login(new Authentication(username, sb.toString()))) {
			frame.dispose();
			program.showMainWindow();
		} else {
			showWrongLoginLabel();
		}
	}

}
