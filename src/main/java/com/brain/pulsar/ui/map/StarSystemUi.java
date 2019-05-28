package main.java.com.brain.pulsar.ui.map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.brain.ion.graphics.ScreenPosition;
import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.Circle;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.ion.input.Mouse;
import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;
import main.java.com.brain.pulsar.universe.Body;
import main.java.com.brain.pulsar.universe.StarSystem;

public class StarSystemUi {
	
	private int height;
	private int width;
	
	private double zoom;
	
	private List<BodyUi> bodyList;
	
	private StarSystem starSystem;
	private Point2D moveOffset;
	private Point2D zoomOffset;
	private BodyUi zoomTarget;

	public StarSystemUi(StarSystem mainSystem, Map<String, VectorGroup> bodys, Map<String, VectorGroup> toolTip) {
		
		starSystem = mainSystem;
		
		moveOffset = new Point2D.Double(0, 0);
		zoomOffset = new Point2D.Double(0, 0);
		bodyList = new ArrayList<>();
		
		VectorGroup base = bodys.getOrDefault("", new VectorGroup());
		VectorGroup baseToolTip = toolTip.getOrDefault("", new VectorGroup());
		for(Body b: starSystem.getBodyList()) {
			bodyList.add(new BodyUi(b, bodys.getOrDefault(b.getId(), base), toolTip.getOrDefault(b.getId(), baseToolTip)));
			System.out.println(b);
		}
		
	}

	public void tick(Mouse m) {
		
		boolean hover = false;
		for(BodyUi b: bodyList) {
			hover = b.tick(m);
			if(hover) {
				if(m.buttonClicked(1)) {
					zoomTarget = b;
					System.out.println("target set");
				}
				break;
			}
		}
		if(!hover) {
			if(m.buttonClicked(1)) {
				zoomTarget = null;
				System.out.println("target unset");
			}
		}
		
		if(m.buttonDown(1)) {
			Point d = m.getChange();
			if(d.getX() != 0 || d.getY() != 0) {
				double scale = Math.pow(1.1, zoom);
				moveOffset.setLocation(moveOffset.getX()-d.x/scale, moveOffset.getY()-d.y/scale);
			}
		}
		if(m.getWheelDir() != 0) {
			
			double newZoom = zoom+m.getWheelDir();
			
			Point2D target = m.getPosition();
			
			if(zoomTarget != null) {
				target = zoomTarget.getCenter();
			}
			
			double oldScale = Math.pow(1.1, zoom);
			double newScale = Math.pow(1.1, newZoom);
			
			Point2D oldPosition = new Point.Double(
					(target.getX()-width/2)/oldScale,
					(target.getY()-height/2)/oldScale
					);
			
			Point2D newPosition = new Point.Double(
					(target.getX()-width/2)/newScale,
					(target.getY()-height/2)/newScale
					);
			
			zoomOffset.setLocation(
					zoomOffset.getX() + newPosition.getX() - oldPosition.getX(),
					zoomOffset.getY() + newPosition.getY() - oldPosition.getY()
					);
			
			zoom = newZoom;
			
		}
		
	}

	public void render(VectorGraphics g) {
		
		width = g.getWindowWidth();
		height = g.getWindowHeight();
		double scale = Math.pow(1.1, zoom);
		
		g.setTranslate(ScreenPosition.CENTER);
		g.moveTranslate(moveOffset.getX()*scale, moveOffset.getY()*scale);
		g.moveTranslate(zoomOffset.getX()*scale, zoomOffset.getY()*scale);
		
		for(BodyUi b: bodyList) {
			b.render(g, scale);
		}
		
		for(BodyUi b: bodyList) {
			b.renderToolTip(g);
		}
		
	}
	
}
