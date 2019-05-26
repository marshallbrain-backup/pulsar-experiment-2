package main.java.com.brain.pulsar.ui.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.com.brain.ion.graphics.ScreenPosition;
import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.Circle;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.pulsar.universe.Body;
import main.java.com.brain.pulsar.universe.StarSystem;

public class StarSystemUi {
	
	private StarSystem system;
	private List<BodyUi> bodyList;

	public StarSystemUi(StarSystem mainSystem, Map<String, VectorGroup> bodys) {
		
		system = mainSystem;
		
		bodyList = new ArrayList<>();
		
		VectorGroup base = bodys.getOrDefault("", new VectorGroup());
		for(Body b: system.getStarList()) {
			bodyList.add(new BodyUi(b, bodys.getOrDefault(b.getId(), base)));
		}
		
	}

	public void render(VectorGraphics g) {
		
		g.setTranslate(ScreenPosition.CENTER);
		
		for(BodyUi b: bodyList) {
			b.render(g);
		}
		
	}
	
}
