package client;

import java.awt.*;
import javax.swing.*;

public class Login extends JPanel{
	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;
	private JPanel panel;
	private JLabel userLabel, passwordLabel;
	private JTextField userField;
	private JPasswordField passwordField;
	private JButton logInButton;
	private GridBagConstraints c;
	
	public Login(){
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		userLabel = new JLabel("Brukernavn");
		passwordLabel = new JLabel("Passord");
		userField = new JTextField(20);
		passwordField = new JPasswordField(20);
		logInButton = new JButton("Logg inn");
		
		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;
		
		addComponent(userLabel, LINE_END, 0, 0);
		addComponent(userField, LINE_START, 1, 0);
		addComponent(passwordLabel, LINE_END, 0, 1);
		addComponent(passwordField, LINE_START, 1, 1);
		addComponent(logInButton, LINE_END, 1, 2);
	}
	
	private void addComponent(JComponent component, int anchor, int gridx, int gridy) {
		c.anchor = anchor;
		c.gridx = gridx;
		c.gridy = gridy;
		add(component, c);
	}
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Logg inn");
		frame.setPreferredSize(new Dimension(350, 130));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(new Login());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
