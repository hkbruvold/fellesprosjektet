package client.GUI;

import javax.swing.JComponent;

import client.Program;

import data.Event;

public class EventComponent extends JComponent { //TODO find proper component
	private Event event;
	private Program program;

	public EventComponent(Program program, Event event) {
		this.program = program;
		this.event = event;
	}

}
