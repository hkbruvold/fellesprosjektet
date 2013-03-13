package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class LoginWindow extends JPanel implements ActionListener {
	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;
	private JFrame frame;
	private JLabel usernameLabel, passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton closeButton, logInButton;
	private GridBagConstraints c;

	public LoginWindow(){
		initFrame();
		initPanel();

		frame.pack();
		frame.setVisible(true);
	}

	private void initFrame() {
		frame = new JFrame("Logg inn");
		frame.setPreferredSize(new Dimension(300, 130));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		usernameLabel = new JLabel("Brukernavn");
		passwordLabel = new JLabel("Passord");
		usernameField = new JTextField(15);
		passwordField = new JPasswordField(15);
		closeButton = new JButton("Lukk");
		logInButton = new JButton("Logg inn");

		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;

		addComponent(usernameLabel, 0, 0, LINE_END);
		addComponent(usernameField, 1, 0, LINE_START);
		addComponent(passwordLabel, 0, 1, LINE_END);
		addComponent(passwordField, 1, 1, LINE_START);
		addComponent(closeButton, 1, 2, LINE_START); // Hmmm...
		addComponent(logInButton, 1, 2, LINE_END);   // Hmmm...

		usernameField.addActionListener(this);
		passwordField.addActionListener(this);
		closeButton.addActionListener(this);
		logInButton.addActionListener(this);
	}

	private void addComponent(JComponent component, int gridx, int gridy, int anchor) {
		c.gridx = gridx;
		c.gridy = gridy;
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
			// TODO logIn
			frame.dispose(); // Close if successful; show error message if not?
		}
	}

	public static void main(String[] args){
		new LoginWindow();
	}
}
