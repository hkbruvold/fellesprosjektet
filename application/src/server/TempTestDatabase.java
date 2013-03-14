package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class TempTestDatabase extends JPanel implements ActionListener {
	private static final int SIZE_FIELD = 15;

	private static final String FRAME_NAME = "Test database";

	private static final String LABEL_SERVER_URL = "Server URL";
	private static final String LABEL_USERNAME = "Brukernavn";
	private static final String LABEL_PASSWORD = "Passord";
	private static final String LABEL_QUERY = "Query";
	
	private static final String BUTTON_CLOSE = "Lukk";
	private static final String BUTTON_LOG_IN = "Logg inn";
	
	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;
	private JFrame frame;
	private JLabel serverUrlLabel, usernameLabel, passwordLabel, queryLabel;
	private JTextField serverUrlField, usernameField, queryField;
	private JPasswordField passwordField;
	private JButton closeButton, testButton;
	private GridBagConstraints c;

	public TempTestDatabase(){
		initFrame();
		initPanel();

		frame.pack();
		frame.setVisible(true);
	}

	private void initFrame() {
		frame = new JFrame(FRAME_NAME);
		frame.setPreferredSize(new Dimension(300, 200));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initPanel() {
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		serverUrlLabel = new JLabel(LABEL_SERVER_URL);
		usernameLabel = new JLabel(LABEL_USERNAME);
		passwordLabel = new JLabel(LABEL_PASSWORD);
		queryLabel = new JLabel(LABEL_QUERY);
		
		serverUrlField = new JTextField("localhost:port/databaseName", SIZE_FIELD);
		usernameField = new JTextField(SIZE_FIELD);
		passwordField = new JPasswordField(SIZE_FIELD);
		queryField = new JTextField(SIZE_FIELD);
		
		closeButton = new JButton(BUTTON_CLOSE);
		testButton = new JButton(BUTTON_LOG_IN);

		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;

		addComponent(serverUrlLabel, 0, 0, 1, LINE_END);
		addComponent(serverUrlField, 1, 0, 2, LINE_START);
		addComponent(usernameLabel, 0, 1, 1, LINE_END);
		addComponent(usernameField, 1, 1, 2, LINE_START);
		addComponent(passwordLabel, 0, 2, 1, LINE_END);
		addComponent(passwordField, 1, 2, 2, LINE_START);
		addComponent(queryLabel, 0, 3, 1, LINE_END);
		addComponent(queryField, 1, 3, 2, LINE_START);
		addComponent(closeButton, 1, 4, 1, LINE_START);
		addComponent(testButton, 2, 4, 1, LINE_END);

		closeButton.addActionListener(this);
		testButton.addActionListener(this);
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
		} else if (e.getSource().equals(testButton)) {
			String serverUrl = "jdbc:mysql://" + serverUrlField.getText();
			String username = usernameField.getText();
			char[] password = passwordField.getPassword();
			String query = queryField.getText();
			DatabaseConnection dbConn = new DatabaseConnection(serverUrl, username, password);
			DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
			dbComm.query(query);
		}
	}

	public static void main(String[] args){
		new TempTestDatabase();
	}
}
