package com.brain.ion.graphics.vectors;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds data about a rectangle vector from an xml file
 * 
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "rect")
public class Rectangle implements Vector {

	@XmlAttribute
	private double x;
	@XmlAttribute
	private double y;
	@XmlAttribute(name = "w")
	private double width;
	@XmlAttribute(name = "h")
	private double hight;

	@XmlAttribute
	private String id;
	@XmlAttribute(name = "style")
	private String styleString;
	
	private Map<String, String> style;
	
	/**
	 * Base constructor
	 */
	public Rectangle() {
		
		id = "";
	}
	
	/**
	 * Clones the rectangle
	 * 
	 * @param base
	 *            The rectangle to clone
	 */
	public Rectangle(Rectangle base) {
		
		x = base.x;
		y = base.y;
		width = base.width;
		hight = base.hight;
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
		
		if (width <= 0) {
			width = 1;
		}
		
		if (hight <= 0) {
			hight = 1;
		}
		
		return new Rectangle2D.Double(x, y, width, hight);
		
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
		
		return new Rectangle(this);
	}
	
	@Override
	public String getId() {
		
		return id;
	}
	
}
