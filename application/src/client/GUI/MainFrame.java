package client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.Program;

@SuppressWarnings("serial")
public class MainFrame extends JPanel implements ActionListener {
	private static final String FRAME_NAME = "Kalender";

	private static final String BTN_NEW_EVENT = "Ny avtale";
	private static final String BTN_NOTIFICATIONS = "Meldinger";
	private static final String BTN_CALENDARS = "Kalendere";
	private static final String BTN_QUIT = "Avslutt";

	private static final int LINE_START = GridBagConstraints.LINE_START;
	private static final int LINE_END = GridBagConstraints.LINE_END;

	private static final int HORIZONTAL = GridBagConstraints.HORIZONTAL;
	private static final int NONE = GridBagConstraints.NONE;

	private JFrame frame;
	private GridBagConstraints c;
	private Program program;

	private JButton btnNewEvent, btnNotifications, btnCalendars, btnQuit;

	public MainFrame(Program program){
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

		btnNewEvent = new JButton(BTN_NEW_EVENT);
		btnNotifications = new JButton(BTN_NOTIFICATIONS);
		btnCalendars = new JButton(BTN_CALENDARS);
		btnQuit = new JButton(BTN_QUIT);

		c.insets = new Insets(0,0,5,0);
		c.ipadx = 10;

		addComponent(0, 1, 1, btnNewEvent, LINE_START, HORIZONTAL);
		addComponent(0, 2, 1, btnNotifications, LINE_START, HORIZONTAL);
		addComponent(0, 3, 1, btnCalendars, LINE_START, HORIZONTAL);
		addComponent(0, 4, 1, btnQuit, LINE_START, HORIZONTAL);

		btnNewEvent.addActionListener(this);
		btnNotifications.addActionListener(this);
		btnCalendars.addActionListener(this);
		btnQuit.addActionListener(this);
	}
	private void addComponent(int gridx, int gridy, int gridwidth, JComponent component, int anchor, int fill) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.anchor = anchor;
		c.fill = fill;
		add(component, c);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnNewEvent)) {

		} else if (e.getSource().equals(btnNotifications)) {

		} else if (e.getSource().equals(btnCalendars)) {

		} else if (e.getSource().equals(btnQuit)) {

		}
	}

}
