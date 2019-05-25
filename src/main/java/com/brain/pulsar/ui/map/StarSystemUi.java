package main.java.com.brain.pulsar.ui.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import main.java.com.brain.pulsar.universe.Body;
import main.java.com.brain.pulsar.universe.StarSystem;

public class StarSystemUi {
	
	private StarSystem system;
	private List<BodyUi> bodyList;

	public StarSystemUi(StarSystem mainSystem) {
		
		system = mainSystem;
		
		bodyList = new ArrayList<>();
		
		for(Body b: system.getStarList()) {
			bodyList.add(new BodyUi(b));
		}
		
	}

	public void render(Graphics2D g) {
		
		for(BodyUi b: bodyList) {
			b.render(g);
		}
		
	}
	
}
