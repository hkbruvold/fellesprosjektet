package client.GUI;

import javax.swing.JComponent;

import data.Event;

public class EventComponent extends JComponent { //TODO find proper component
	private Event event;

	public EventComponent(Event event) {
		this.event = event;
	}
	
}
