package main.java.com.brain.pulsar.ui.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.ion.input.Mouse;
import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;
import main.java.com.brain.pulsar.universe.Body;

public class BodyUi {
	
	private boolean renderToolTip;
	
	private Body body;
	private VectorGroup vectorLayer;
	private Area lastRendered;
	private Point ToolTipPosition;

	public BodyUi(Body b, VectorGroup vg) {
		
		body = b;
		vectorLayer = vg;
		
	}

	public boolean tick(Mouse m) {
		
		Point p = m.getPosition();
		renderToolTip = lastRendered.contains(p);
		
		if(renderToolTip) {
			ToolTipPosition = p;
		}
		
		return renderToolTip;
		
	}

	public void render(VectorGraphics g, double scale) {
		
		List<Vector> vectors = vectorLayer.getVectors();
		
		double distanceRatio = g.getWindowWidth() / Distance.convert(20, 0, DistanceType.AU, DistanceType.METER).getDistance();
		distanceRatio *= scale;
		
		double parentX = body.getParent().getX() * distanceRatio;
		double parentY = body.getParent().getY() * distanceRatio;
		
		double radius = body.getRadius().convert(DistanceType.METER).getDistance() * distanceRatio;
		double distance = body.getDistance().convert(DistanceType.METER).getDistance() * distanceRatio;
		
		if(radius < 10) {
			radius = 10;
		}
		
		AffineTransform at = new AffineTransform();
		
		Map<String, String> style = new HashMap<>();
		style.put("stroke", "#000000");
		style.put("stroke-opacity", "1");
		style.put("stroke-width", "2");
		
		Ellipse2D orbit = new Ellipse2D.Double(parentX-distance, parentY-distance, distance*2, distance*2);
		Arc2D orbitArc = new Arc2D.Double(orbit.getBounds2D(), body.getAngle(), 360, Arc2D.OPEN);
		Point2D curveStart = orbitArc.getStartPoint();
		
		g.draw(orbitArc, style);

		Map<Shape, Map<String, String>> shapeList = new HashMap<>();
		double max = 1;
		for(Vector v: vectors) {
			
			Shape s = v.getShape();
			shapeList.put(s, v.getStyle());
			
			Rectangle2D bounds = s.getBounds2D();
			if(max < bounds.getWidth()/2) {
				max = bounds.getWidth()/2;
			}
			if(max < bounds.getHeight()/2) {
				max = bounds.getHeight()/2;
			}
			
		}
		
		at.translate(curveStart.getX(), curveStart.getY());
		at.scale(1/max, 1/max);
		at.scale(radius, radius);
		
		lastRendered = new Area();
		for(Entry<Shape, Map<String, String>> e: shapeList.entrySet()) {
			
			Shape shape = at.createTransformedShape(e.getKey());
			
			Area shapeArea = new Area(shape);
			lastRendered.add(shapeArea);
			
			g.draw(shape, e.getValue());
			
		}
		
		lastRendered = lastRendered.createTransformedArea(g.getAffineTransform());
		
		try {
			renderToolTip(g);
		} catch (NoninvertibleTransformException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void renderToolTip(VectorGraphics g) throws NoninvertibleTransformException {
		
		if(renderToolTip) {
			
			Map<String, String> style = new HashMap<>();
			style.put("stroke", "#000000");
			style.put("stroke-opacity", "1");
			style.put("stroke-width", "2");
			
			AffineTransform offset = g.getAffineTransform().createInverse();
			Point2D p = new Point2D.Double(ToolTipPosition.getX()+20, ToolTipPosition.getY()+20);
			Rectangle2D toolTip = new Rectangle2D.Double(p.getX(), p.getY(), 50, 50);
			g.draw(offset.createTransformedShape(toolTip), style);
			
		}
		
	}
	
}
