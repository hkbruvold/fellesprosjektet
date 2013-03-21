package client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.MatteBorder;

import client.Program;
import data.User;

@SuppressWarnings("serial")
public class CalPanel extends JPanel {
	private Program program;

	private final int COLUMNS = 8;
	private final int ROWS = 24;
	private final int width = 800;
	private final int height = 600;
	private final int colWidth = width / COLUMNS;
	private final int rowHeight = 2 * height / ROWS;

	private JPanel headerPanel;
	private JPanel contentPanel;
	private JScrollPane contentScroller;

	public CalPanel(Program program) {
		this.program = program;
		this.setBackground(Color.WHITE);
		initPanel();
	}

	private void initPanel() {
		initComponents();
		addComponents();
		addListeners();
	}
	private void initComponents() {
		makeHeader();
		makeContent();
		contentScroller = new JScrollPane();

		JViewport viewPort = new JViewport();
		viewPort.setView(contentPanel);
		viewPort.setViewPosition(new Point(0, 8 * rowHeight));
		contentScroller.setSize(width, height/4);
		contentScroller.setViewport(viewPort);
		contentScroller.setColumnHeaderView(headerPanel);

		add(contentScroller);


	}
	private void makeHeader() {
		headerPanel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				for(int i = 1; i < COLUMNS; i++) {
					g.drawLine(i * (getWidth() / COLUMNS), 0, i * (getWidth() / COLUMNS), getHeight());
				}
				super.paintChildren(g);
			}
		};
	}
	private void makeContent() {
		contentPanel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				for(int j = 1; j < ROWS + 1; j++) {
					if(j > 0) {
						g.setColor(Color.LIGHT_GRAY);
					}
					g.drawLine(0, j * (getHeight() / ROWS), getWidth(), j * (getHeight() / ROWS));
				}
				g.setColor(Color.BLACK);
				for(int i = 1; i < COLUMNS; i++) {
					g.drawLine(i * (getWidth() / COLUMNS), 0, i * (getWidth() / COLUMNS), getHeight());
				}
				super.paintChildren(g);
			}
		};
		contentPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		contentPanel.setPreferredSize(new Dimension(width, height));
		contentPanel.setSize(contentPanel.getPreferredSize());
		contentPanel.setLayout(null);

	}
	private void addComponents() {
		// TODO
	}
	private void addListeners() {
		// TODO
	}

	public void addCalendarsFromUsers(List<User> selectedValuesList) {
		// TODO Auto-generated method stub
		// from selectCalendars.java
	}

	public void setWeek(Object selectedItem) {
		// TODO Auto-generated method stub
		// from mainframe
	}
	public void setYear(Object selectedItem) {
		// TODO Auto-generated method stub
		// from mainframe
	}

	private void showWeekAndYear(/* TODO */) {
		// update the header and content
		contentScroller.setColumnHeaderView(headerPanel);
	}

}
