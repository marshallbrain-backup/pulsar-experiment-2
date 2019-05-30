package com.brain.pulsar.ui.map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brain.ion.graphics.ScreenPosition;
import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.input.Mouse;
import com.brain.pulsar.ui.view.View;
import com.brain.pulsar.ui.view.body.BodyOverview;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.universe.StarSystem;

/**
 * @author Marshall Brain
 *
 */
public class StarSystemUi {
	
	private int height;
	private int width;
	
	private double zoom;
	
	private List<BodyUi> bodyList;
	private List<View> viewList;
	
	private StarSystem starSystem;
	private Point2D moveOffset;
	private Point2D zoomOffset;
	private BodyUi zoomTarget;
	
	/**
	 * Constructs a new StarSystemUi.
	 * 
	 * @param starSystem
	 *            The system being rendered
	 * @param bodyVectors
	 *            The map of VectorGroups for rendering bodies
	 * @param tooltipVectors
	 *            The map of VectorGroups for rendering tooltips
	 * @param viewList 
	 */
	public StarSystemUi(StarSystem starSystem, Map<String, VectorGroup> bodyVectors,
			Map<String, VectorGroup> tooltipVectors, List<View> viewList) {
		
		this.viewList = viewList;
		this.starSystem = starSystem;
		
		moveOffset = new Point2D.Double(0, 0);
		zoomOffset = new Point2D.Double(0, 0);
		bodyList = new ArrayList<>();
		
		VectorGroup base = bodyVectors.getOrDefault("", new VectorGroup());
		VectorGroup baseTooltip = tooltipVectors.getOrDefault("", new VectorGroup());
		
		Map<Body, BodyUi> bodyUiMap = new HashMap<>();
		for (Body b : starSystem.getBodyList()) {
			
			BodyUi ui = new BodyUi(b, bodyVectors.getOrDefault(b.getId(), base),
					tooltipVectors.getOrDefault(b.getId(), baseTooltip), bodyUiMap.get(b.getParent()));
			
			bodyUiMap.put(b, ui);
			bodyList.add(ui);
			
			System.out.println(b);
			
		}
		
	}
	
	/**
	 * Processes a game tick for this class.
	 * 
	 * @param m
	 *            The mouse listener being used
	 */
	public void tick(Mouse m) {
		
		// Sets the zoom target to the body that was clicked
		boolean hover = false;
		for (BodyUi b : bodyList) {
			hover = b.tick(m);
			if (hover) {
				if (m.buttonDoubleClicked(1)) {
					viewList.add(new BodyOverview());
					System.out.println("view opened");
				}
				if (m.buttonClicked(1)) {
					zoomTarget = b;
					System.out.println("target set");
				}
				break;
			}
		}
		// Unsets the zoom target if a planet was not clicked
		if (!hover && m.buttonClicked(1)) {
			zoomTarget = null;
			System.out.println("target unset");
		}
		
		// Moves the screen if the left mouse button is held down
		if (m.buttonDown(1)) {
			Point d = m.getChange();
			if (d.getX() != 0 || d.getY() != 0) {
				double scale = Math.pow(1.1, zoom);
				moveOffset.setLocation(moveOffset.getX() - d.x / scale, moveOffset.getY() - d.y / scale);
			}
		}
		
		// Zooms the screen
		if (m.getWheelDir() != 0) {
			
			double newZoom = zoom + m.getWheelDir();
			
			Point2D target = m.getPosition();
			
			// Sets the point to the zoom target if it is set
			if (zoomTarget != null) {
				target = zoomTarget.getCenter();
			}
			
			double oldScale = Math.pow(1.1, zoom);
			double newScale = Math.pow(1.1, newZoom);
			
			Point2D oldPosition = new Point.Double((target.getX() - width / 2) / oldScale,
					(target.getY() - height / 2) / oldScale);
			
			Point2D newPosition = new Point.Double((target.getX() - width / 2) / newScale,
					(target.getY() - height / 2) / newScale);
			
			/*
			 * Finds the difference between the point using the old zoom and the new zoom.
			 * This is then used to offset the screen so that it zooms in on where the mouse
			 * is positioned
			 */
			zoomOffset.setLocation(zoomOffset.getX() + newPosition.getX() - oldPosition.getX(),
					zoomOffset.getY() + newPosition.getY() - oldPosition.getY());
			
			zoom = newZoom;
			
		}
		
	}
	
	/**
	 * Draws the body to the screen
	 * 
	 * @param g
	 *            The graphics object that is linked to the JFrame
	 */
	public void render(VectorGraphics g) {
		
		width = g.getWindowWidth();
		height = g.getWindowHeight();
		double scale = Math.pow(1.1, zoom);
		
		g.setTranslate(ScreenPosition.CENTER);
		g.moveTranslate(moveOffset.getX() * scale, moveOffset.getY() * scale);
		g.moveTranslate(zoomOffset.getX() * scale, zoomOffset.getY() * scale);
		
		// TODO Handle planet's orbits and the body's themselves separately
		for (BodyUi b : bodyList) {
			b.render(g, scale);
		}
		
		// keeps the tooltip on top
		for (BodyUi b : bodyList) {
			b.renderToolTip(g);
		}
		
	}
	
}
