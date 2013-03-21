package client.GUI;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import client.Program;
import data.User;

@SuppressWarnings("serial")
public class CalPanel extends JPanel {
	private Program program;
	
	public CalPanel(Program program) {
		this.program = program;
		this.setBackground(Color.WHITE);
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

}
