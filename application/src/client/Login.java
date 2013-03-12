package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login extends JPanel implements ActionListener {
	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;
	private JFrame frame;
	private JLabel userLabel, passwordLabel;
	private JTextField userField;
	private JPasswordField passwordField;
	private JButton closeButton, logInButton;
	private GridBagConstraints c;

	public Login(){
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

		userLabel = new JLabel("Brukernavn");
		passwordLabel = new JLabel("Passord");
		userField = new JTextField(15);
		passwordField = new JPasswordField(15);
		closeButton = new JButton("Lukk");
		logInButton = new JButton("Logg inn");

		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;

		addComponent(userLabel, LINE_END, 0, 0);
		addComponent(userField, LINE_START, 1, 0);
		addComponent(passwordLabel, LINE_END, 0, 1);
		addComponent(passwordField, LINE_START, 1, 1);
		addComponent(closeButton, LINE_START, 1, 2); // Hmmm...
		addComponent(logInButton, LINE_END, 1, 2);
		
		closeButton.addActionListener(this);
		logInButton.addActionListener(this);
	}

	private void addComponent(JComponent component, int anchor, int gridx, int gridy) {
		c.anchor = anchor;
		c.gridx = gridx;
		c.gridy = gridy;
		add(component, c);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(closeButton)) {
			frame.dispose();
		} else if (e.getSource().equals(logInButton)) {
			// TODO logIn
			frame.dispose(); // If successful? Display error if not successful?
		} 
		// TODO Action when return is pressed in userField or passwordField
	}

	public static void main(String[] args){
		new Login();
	}
}
