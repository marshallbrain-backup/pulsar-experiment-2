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
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.Text;
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
	private VectorGroup toolTipVector;
	private Area lastRendered;
	private Point toolTipPosition;

	public BodyUi(Body b, VectorGroup vg, VectorGroup toolTip) {
		
		body = b;
		vectorLayer = vg;
		toolTipVector = toolTip;
		
	}

	public boolean tick(Mouse m) {
		
		Point p = m.getPosition();
		renderToolTip = lastRendered.contains(p);
		
		if(renderToolTip) {
			toolTipPosition = p;
		}
		
		return renderToolTip;
		
	}
	
	public Point2D getCenter() {
		Rectangle2D b = lastRendered.getBounds2D();
		return new Point2D.Double(b.getCenterX(), b.getCenterY());
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
		
	}
	
	public void renderToolTip(VectorGraphics g) {
		
		if(renderToolTip) {
			
			Point2D p = new Point2D.Double(toolTipPosition.getX()+10, toolTipPosition.getY()+10);
			
			AffineTransform offset = new AffineTransform();
			try {
				offset = g.getAffineTransform().createInverse();
			} catch (NoninvertibleTransformException e1) {
				e1.printStackTrace();
			}
			
			offset.translate(p.getX(), p.getY());
			
			Shape text = null;
			Point padding = new Point();
			Map<Shape, Map<String, String>> shapeList = new LinkedHashMap<>();
			for(Vector v: toolTipVector.getVectors()) {
				
				Shape s = v.getShape(g.getGraphics());
				
				if(v instanceof Text) {
					
					Text textVector = ((Text) v);
					textVector.setText(body.getId());
					padding = textVector.getPading();
					
					s = v.getShape(g.getGraphics());
					s = offset.createTransformedShape(s);
					text = s;
					
				}
				shapeList.put(s, v.getStyle());
				
			}
			
			if(text != null) {
				Rectangle2D bounds = text.getBounds2D();
				offset.scale(padding.getX() + bounds.getWidth() + 1, padding.getY() + bounds.getHeight() + 1);
			}
			
			for(Entry<Shape, Map<String, String>> e: shapeList.entrySet()) {
				Shape shape = e.getKey();
				if(shape != text) {
					shape = offset.createTransformedShape(shape);
				}
				g.draw(shape, e.getValue());
			}
			
		}
		
	}
	
}
