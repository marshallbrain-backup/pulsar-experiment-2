package main.java.com.brain.pulsar.ui.map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.com.brain.ion.graphics.ScreenPosition;
import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.Circle;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.ion.input.Mouse;
import main.java.com.brain.pulsar.universe.Body;
import main.java.com.brain.pulsar.universe.StarSystem;

public class StarSystemUi {
	
	private double zoom;
	private StarSystem starSystem;
	private List<BodyUi> bodyList;
	private Point offsetAmount;

	public StarSystemUi(StarSystem mainSystem, Map<String, VectorGroup> bodys) {
		
		zoom = 1;
		
		starSystem = mainSystem;
		
		offsetAmount = new Point(0, 0);
		bodyList = new ArrayList<>();
		
		VectorGroup base = bodys.getOrDefault("", new VectorGroup());
		for(Body b: starSystem.getBodyList()) {
			bodyList.add(new BodyUi(b, bodys.getOrDefault(b.getId(), base)));
			System.out.println(b);
		}
		
	}

	public void tick(Mouse m) {
		
		if(m.buttonDown(1)) {
			Point d = m.getChange();
			if(d.getX() != 0 || d.getY() != 0) {
				offsetAmount.translate(-d.x, -d.y);
			}
		}
		if(m.getWheelDir() != 0) {
			double newZoom = zoom+m.getWheelDir();
			if(newZoom == 0) {
				newZoom += m.getWheelDir();
			}
			
			zoom = newZoom;
			
		}
		
	}

	public void render(VectorGraphics g) {
		
		g.setTranslate(ScreenPosition.CENTER);
		g.moveTranslate(offsetAmount.getX(), offsetAmount.getY());
		
		AffineTransform transform = new AffineTransform();
		double scale = Math.pow(1.1, zoom);
		transform.scale(scale, scale);
		
		for(BodyUi b: bodyList) {
			b.render(g, transform);
		}
		
	}
	
}
