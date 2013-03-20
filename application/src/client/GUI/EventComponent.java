package client.GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import client.Program;

import data.Event;

public class EventComponent extends JComponent { //TODO find proper component
	private Event event;
	
	private int borderWidth = 1; //Border width in pixels
	private int[] RGBColor = {45, 60, 255};
	private int[] RGBBGColor = {0, 0, 155};

	public EventComponent(Event event) {
		this.event = event;
		setColor(new int[]{45, 60, 255});
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(RGBBGColor[0], RGBBGColor[1], RGBBGColor[2]));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(new Color(RGBColor[0], RGBColor[1], RGBColor[2]));
		g.fillRect(borderWidth, borderWidth, getWidth()-2*borderWidth, getHeight()-2*borderWidth);
		
		g.setColor(Color.black);
		g.drawString(event.getDescription(), 2, 10);
	}
	
	public void setColor(int[] RGBColor) {
		if (RGBColor.length != 3) {
			return;
		}
		
		for (int i = 0; i < 3; i++) {
			if (RGBColor[i] < 0 || RGBColor[i] > 255) {
				return;
			}
			
			int bgc = RGBColor[i] - 100;
			if (bgc < 0) {
				bgc = 0;
			}
			RGBBGColor[i] = bgc;
		}
		
		this.RGBColor = RGBColor;
	}
	
	public Event getEvent() {
		return this.event;
	}

}
