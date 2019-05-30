package com.brain.ion.graphics.vectors;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.font.GlyphVector;
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
	
	@XmlAttribute(name = "padding_x")
	private int paddingX;
	@XmlAttribute(name = "padding_y")
	private int paddingY;
	
	@XmlValue
	private String textValue;
	private String textFormated;
	@XmlAttribute(name = "style")
	private String styleString;
	
	private Map<String, String> style;
	
	private Font font = null;
	
	/**
	 * Base constructor
	 */
	public Text() {
	
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
		
	}
	
	/**
	 * @return The padding that the text should have
	 */
	public Point getPading() {
		
		return new Point(paddingX * 2, paddingY * 2);
	}
	
	/**
	 * @param newText
	 *            The new text
	 */
	public void setText(String newText) {
		
		textValue = newText;
		textFormated = newText;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.brain.ion.graphics.vectors.Vector#getShape()
	 */
	@Override
	public Shape getShape() {
		
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
		Rectangle2D b = v.getVisualBounds();
		
		return v.getOutline(paddingX - 2, (int) Math.ceil(paddingY + b.getHeight() - fontMetrics.getDescent()) + 2);
		
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
	
}
