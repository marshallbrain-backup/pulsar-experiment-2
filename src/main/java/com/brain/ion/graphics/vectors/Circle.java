package com.brain.ion.graphics.vectors;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds data about a circle vector from an xml file
 * 
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "circle")
public class Circle implements Vector {
	
	@XmlAttribute(name = "cx")
	private int centerX;
	@XmlAttribute(name = "cy")
	private int centerY;
	@XmlAttribute(name = "r")
	private int radius;

	@XmlAttribute
	private String id;
	@XmlAttribute(name = "style")
	private String styleString;
	
	private Map<String, String> style;
	
	/**
	 * Base constructor
	 */
	public Circle() {
		
		id = "";
	}
	
	/**
	 * Clones the circle
	 * 
	 * @param base
	 *            The circle to clone
	 */
	public Circle(Circle base) {
		
		centerX = base.centerX;
		centerY = base.centerY;
		radius = base.radius;
		styleString = base.styleString;
		style = base.style;
		id = base.id;
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.brain.ion.graphics.vectors.Vector#getShape()
	 */
	@Override
	public Shape getShape() {
		
		if (radius <= 0) {
			radius = 1;
		}
		
		int w = radius;
		int h = radius;
		int x = centerX - w;
		int y = centerY - h;
		
		w *= 2;
		h *= 2;
		
		return new Ellipse2D.Float(x, y, w, h);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.brain.ion.graphics.vectors.Vector#getStyle()
	 */
	@Override
	public Map<String, String> getStyle() {
		
		if (style == null) {
			style = convertStyle(styleString);
		}
		
		return style;
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.brain.ion.graphics.vectors.Vector#copyVector()
	 */
	@Override
	public Vector copyVector() {
		
		return new Circle(this);
	}
	
	@Override
	public String getId() {
		
		return id;
	}
	
}
