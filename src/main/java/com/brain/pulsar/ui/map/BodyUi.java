package main.java.com.brain.pulsar.ui.map;

import java.awt.Color;
import java.awt.Graphics2D;

import main.java.com.brain.pulsar.universe.Body;

public class BodyUi {
	
	private Body body;

	public BodyUi(Body b) {
		
		body = b;
		
	}

	public void render(Graphics2D g) {
		
		g.setColor(Color.BLUE);
		g.fillOval(0, 0, 50, 50);
		
	}
	
}
