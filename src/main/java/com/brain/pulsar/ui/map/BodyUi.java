package main.java.com.brain.pulsar.ui.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.List;

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

	public void render(VectorGraphics g) {
		
		List<Vector> vectors = vectorLayer.getVectors();
		
		double distanceRatio = g.getWindowWidth() / Distance.convert(5, 0, DistanceType.AU, DistanceType.METER).getDistance();
		Distance radius = body.getRadius().convert(DistanceType.METER);
		
		int x = Math.toIntExact(Math.round(body.getX() * distanceRatio));
		int y = Math.toIntExact(Math.round(body.getY() * distanceRatio));
		int r = Math.toIntExact(Math.round(radius.getDistance() * distanceRatio));
		
		if(r < 5) {
			r = 5;
		}
		
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		
		for(Vector v: vectors) {
			
			Shape s = v.getShape(r, r);
			s = at.createTransformedShape(s);
			g.draw(s);
		}
		
	}
	
}
