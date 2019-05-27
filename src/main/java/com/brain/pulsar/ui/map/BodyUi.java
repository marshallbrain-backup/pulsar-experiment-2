package main.java.com.brain.pulsar.ui.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;
import main.java.com.brain.pulsar.universe.Body;

public class BodyUi {
	
	private Body body;
	private VectorGroup vectorLayer;

	public BodyUi(Body b, VectorGroup vg) {
		
		body = b;
		vectorLayer = vg;
		
	}

	public void render(VectorGraphics g, AffineTransform transform) {
		
		List<Vector> vectors = vectorLayer.getVectors();
		
		double distanceRatio = g.getWindowWidth() / Distance.convert(20, 0, DistanceType.AU, DistanceType.METER).getDistance();
		Distance radius = body.getRadius().convert(DistanceType.METER);
		Distance distance = body.getDistance().convert(DistanceType.METER);
		
		int x = Math.toIntExact(Math.round(body.getX() * distanceRatio));
		int y = Math.toIntExact(Math.round(body.getY() * distanceRatio));
		
		int parX = Math.toIntExact(Math.round(body.getParent().getX() * distanceRatio));
		int parY = Math.toIntExact(Math.round(body.getParent().getY() * distanceRatio));
		
		int r = Math.toIntExact(Math.round(radius.getDistance() * distanceRatio));
		int d = Math.toIntExact(Math.round(distance.getDistance() * distanceRatio));
		
		if(r < 5) {
			r = 5;
		}
		
		AffineTransform at = new AffineTransform(transform);
		
		Shape e = new Ellipse2D.Double(parX-d, parY-d, d*2, d*2);
		e = at.createTransformedShape(e);
		
		Map<String, String> style = new HashMap<>();
		style.put("stroke", "#000000");
		style.put("stroke-opacity", "1");
		style.put("stroke-width", "2");
		
		g.draw(e, style);
		
		at.translate(x, y);
		for(Vector v: vectors) {
			
			Shape s = v.getShape(r, r);
			s = at.createTransformedShape(s);
			g.draw(s, v.getStyle());
		}
		
	}
	
}
