package client.GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import client.Program;

import data.Event;

public class EventComponent extends JComponent { //TODO find proper component
	private Event event;
	
	private int borderWidth = 2; //Border width in pixels
	private int[] RGBColor = {45, 60, 255};
	private Color eventColor = new Color(RGBColor[0], RGBColor[1], RGBColor[2]);

	public EventComponent(Event event) {
		this.event = event;
	}
	
	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		g.setColor(eventColor);
		g.fillRect(borderWidth, borderWidth, getWidth()-borderWidth, getHeight()-borderWidth);
		
		g.setColor(Color.black);
		g.drawString(event.getDescription(), 10, 10);
	}
	
	public void setColor(int[] RGBColor) {
		if (RGBColor.length != 3) {
			return;
		}
		for (int c: RGBColor) {
			if (c < 0 || c > 255) {
				return;
			}
		}
		this.RGBColor = RGBColor;
	}

}
