package main.java.com.brain.pulsar.ui.map;

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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.Text;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.ion.input.Mouse;
import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;
import main.java.com.brain.pulsar.universe.Body;

/**
 * Handles the rendering of a body to the screen.
 * 
 * @author Marshall Brain
 *
 */
public class BodyUi {
	
	private boolean renderToolTip;
	
	private Body body;
	private BodyUi parent;
	private VectorGroup bodyVG;
	private VectorGroup tooltipVG;
	private Area lastRendered;
	private Point toolTipPosition;
	
	/**
	 * Constructs a new BodyUi.
	 * 
	 * @param body
	 *            The body that is being rendered, used for positioning information
	 * @param bodyVG
	 *            The vectors to render
	 * @param tooltipVG
	 *            The vectors for the tooltip
	 * @param parent
	 *            The BodyUi of the parent of the body
	 */
	public BodyUi(Body body, VectorGroup bodyVG, VectorGroup tooltipVG, BodyUi parent) {
		
		this.body = body;
		this.parent = parent;
		this.bodyVG = bodyVG;
		this.tooltipVG = tooltipVG;
		
	}
	
	/**
	 * Processes a game tick for this class.
	 * 
	 * @param m
	 *            The mouse listener being used
	 * @return Whether the mouse is inside the rendered body
	 */
	public boolean tick(Mouse m) {
		
		Point p = m.getPosition();
		renderToolTip = lastRendered.contains(p);
		
		if (renderToolTip) {
			toolTipPosition = p;
		}
		
		return renderToolTip;
		
	}
	
	/**
	 * @return The center of the body
	 */
	public Point2D getCenter() {
		
		Rectangle2D b = lastRendered.getBounds2D();
		return new Point2D.Double(b.getCenterX(), b.getCenterY());
	}
	
	/**
	 * Draws the body to the screen
	 * 
	 * @param g
	 *            The graphics object that is linked to the JFrame
	 * @param scale
	 *            The zoom factor of the screen
	 */
	public void render(VectorGraphics g, double scale) {
		
		// Gets the ratio of the screen width in pixels to the screen width in meters.
		double distanceRatio = g.getWindowWidth()
				/ Distance.convert(20, 0, DistanceType.AU, DistanceType.METER).getDistance();
		distanceRatio *= scale;
		
		// Converts meters to pixels at the given ratio
		double radius = body.getRadius().convert(DistanceType.METER).getDistance() * distanceRatio;
		double distance = body.getDistance().convert(DistanceType.METER).getDistance() * distanceRatio;
		
		if (radius < 10) {
			radius = 10;
		}
		
		AffineTransform at = new AffineTransform();
		
		Map<String, String> style = new HashMap<>();
		style.put("stroke", "#000000");
		style.put("stroke-opacity", "1");
		style.put("stroke-width", "2");
		
		Point2D origin = new Point2D.Double(0, 0);
		if (parent != null) {
			origin = parent.getCenter();
			
			AffineTransform screenAT ;
			try {
				screenAT = g.getAffineTransform().createInverse();
				origin.setLocation(origin.getX() + screenAT.getTranslateX(), origin.getY() + screenAT.getTranslateY());
			} catch (NoninvertibleTransformException e1) {
				e1.printStackTrace();
			}
			
		}
		
		/*
		 * When an ellipse is rendered it does not line up exactly with what
		 * trigonometry predicts. While this disparity is usably much smaller than a
		 * pixel, it becomes apparent when the ellipse is 1,000,000+ pixels in radius
		 */
		Ellipse2D orbit = new Ellipse2D.Double(origin.getX() - distance, origin.getY() - distance, distance * 2,
				distance * 2);
		Arc2D orbitArc = new Arc2D.Double(orbit.getBounds2D(), body.getAngle(), 360, Arc2D.OPEN);
		Point2D curveStart = orbitArc.getStartPoint();
		
		g.draw(orbitArc, style);
		
		// Finds the max size of the vectors
		Map<Shape, Map<String, String>> shapeList = new HashMap<>();
		double max = 1;
		for (Vector v : bodyVG.getVectors()) {
			
			Shape s = v.getShape();
			shapeList.put(s, v.getStyle());
			
			Rectangle2D bounds = s.getBounds2D();
			if (max < bounds.getWidth() / 2) {
				max = bounds.getWidth() / 2;
			}
			if (max < bounds.getHeight() / 2) {
				max = bounds.getHeight() / 2;
			}
			
		}
		
		// Sets the max size of the vectors to the radius of the body
		at.translate(curveStart.getX(), curveStart.getY());
		at.scale(1 / max, 1 / max);
		at.scale(radius, radius);
		
		lastRendered = new Area();
		for (Entry<Shape, Map<String, String>> e : shapeList.entrySet()) {
			
			Shape shape = at.createTransformedShape(e.getKey());
			
			Area shapeArea = new Area(shape);
			lastRendered.add(shapeArea);
			
			g.draw(shape, e.getValue());
			
		}
		
		lastRendered = lastRendered.createTransformedArea(g.getAffineTransform());
		
	}
	
	/**
	 * Renders the tooltip for the body if applicable
	 * 
	 * @param g
	 *            The graphics object that is linked to the JFrame
	 */
	public void renderToolTip(VectorGraphics g) {
		
		if (renderToolTip) {
			
			Point2D p = new Point2D.Double(toolTipPosition.getX() + 10, toolTipPosition.getY() + 10);
			
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
			for (Vector v : tooltipVG.getVectors()) {
				
				Shape s = v.getShape(g.getGraphics());
				
				if (v instanceof Text) {
					
					Text textVector = ((Text) v);
					textVector.setText(body.getId());
					padding = textVector.getPading();
					
					s = v.getShape(g.getGraphics());
					s = offset.createTransformedShape(s);
					text = s;
					
				}
				shapeList.put(s, v.getStyle());
				
			}
			
			// Scales the other vectors to fit around the text
			if (text != null) {
				Rectangle2D bounds = text.getBounds2D();
				offset.scale(padding.getX() + bounds.getWidth() + 1, padding.getY() + bounds.getHeight() + 1);
			}
			
			for (Entry<Shape, Map<String, String>> e : shapeList.entrySet()) {
				Shape shape = e.getKey();
				if (shape != text) {
					shape = offset.createTransformedShape(shape);
				}
				g.draw(shape, e.getValue());
			}
			
		}
		
	}
	
}
