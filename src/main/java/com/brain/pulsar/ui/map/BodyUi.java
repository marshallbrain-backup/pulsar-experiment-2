package main.java.com.brain.pulsar.ui.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.List;

import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
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
		
		for(Vector v: vectors) {
			Shape s = v.getShape(50, 50);
			g.draw(s);
		}
		
	}
	
}
