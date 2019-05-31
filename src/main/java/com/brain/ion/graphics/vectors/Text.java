package com.brain.ion.graphics.vectors;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Holds data about a text vector from an xml file
 * 
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "text")
public class Text implements Vector {
	
	@XmlAttribute
	private int x;
	@XmlAttribute
	private int y;
	@XmlAttribute(name = "padding_x")
	private int paddingX;
	@XmlAttribute(name = "padding_y")
	private int paddingY;

	@XmlAttribute
	private String id;
	@XmlAttribute(name = "style")
	private String styleString;
	@XmlValue
	private String textValue;
	private String textFormated;
	
	private Map<String, String> style;
	
	private Font font = null;
	
	/**
	 * Base constructor
	 */
	public Text() {
		
		id = "";
	}
	
	/**
	 * Clones the text
	 * 
	 * @param base
	 *            The text to clone
	 */
	public Text(Text base) {
		
		paddingX = base.paddingX;
		paddingY = base.paddingY;
		textValue = base.textValue;
		textFormated = base.textFormated;
		styleString = base.styleString;
		style = base.style;
		x = base.x;
		y = base.y;
		id = base.id;
		
	}
	
	public Text setText(String newText) {
		
		Text newTextVector = new Text(this);
		
		newTextVector.textValue = newText;
		newTextVector.textFormated = null;
		return newTextVector;
		
	}
	
	public Point getPadding() {
		
		return new Point(paddingX, paddingY);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.brain.ion.graphics.vectors.Vector#getShape()
	 */
	@Override
	public Shape getShape() {
		
		String error = "USE getShape(Graphics2D g)";
		
		System.out.println(error);
		System.out.println(error);
		System.out.println(error);
		System.out.println(error);
		System.out.println(error);
		System.out.println(error);
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.brain.ion.graphics.vectors.Vector#getShape(java.awt.Graphics2D)
	 */
	@Override
	public Shape getShape(Graphics2D g) {
		
		if (textFormated == null) {
			textFormated = textValue.replaceAll("\\s+", "");
		}
		
		if (font == null) {
			
			String type = getStyle().getOrDefault("font-family", "Open Sans");
			String size = getStyle().getOrDefault("font-size", "14");
			
			font = new Font(type, Font.PLAIN, Math.toIntExact(Math.round(Double.parseDouble(size))));
			
		}
		
		FontMetrics fontMetrics = g.getFontMetrics(font);
		GlyphVector v = font.createGlyphVector(fontMetrics.getFontRenderContext(), textFormated);
		
		//System.out.println(textFormated + " - " + (txt.getAscent() - txt.getDescent()));
		
		return v.getOutline(x + paddingX, y + paddingY + fontMetrics.getAscent() - fontMetrics.getDescent() - fontMetrics.getLeading());
		
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
		
		return new Text(this);
	}
	
	@Override
	public String getId() {
		
		return id;
	}
	
}
