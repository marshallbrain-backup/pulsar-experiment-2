package main.java.com.brain.ion.graphics.vectors;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "text")
public class Text implements Vector {

	@XmlAttribute(name = "pading_x")
	private int padingX;
	@XmlAttribute(name = "pading_y")
	private int padingY;
	
	@XmlValue
	private String textValue;
	private String textFormated;
	@XmlAttribute(name = "style")
	private String styleString;
	
	private Map<String, String> style;
	
	private  Font font = null;
	
	public Text() {
	}
	
	public Text(Text base) {
		
		padingX = base.padingX;
		padingY = base.padingY;
		textValue = base.textValue;
		textFormated = base.textFormated;
		styleString = base.styleString;
		style = base.style;
		
	}
	
	public Point getPading() {
		return new Point(padingX*2, padingY*2);
	}

	public void setText(String newText) {
		textValue = newText;
		textFormated = newText;
	}

	@Override
	public Shape getShape() {
		return null;
	}

	@Override
	public Shape getShape(Graphics2D g) {
		
		if(textFormated == null) {
			textFormated = textValue.replaceAll("\\s+","");
		}
		
		if(font == null) {
			
			String type = getStyle().getOrDefault("font-family", "Open Sans");
			String size = getStyle().getOrDefault("font-size", "14");
			
			font = new Font(type, Font.PLAIN, Math.toIntExact(Math.round(Double.parseDouble(size))));
			
		}
		
		FontMetrics fontMetrics = g.getFontMetrics(font);
		GlyphVector v = font.createGlyphVector(fontMetrics.getFontRenderContext(), textFormated);
		Rectangle2D b = v.getVisualBounds();
		
		return v.getOutline(padingX - 2, (int) Math.ceil(padingY + b.getHeight() - fontMetrics.getDescent()) + 2);
		
	}

	@Override
	public Map<String, String> getStyle() {
		
		if(style == null) {
			style = convertStyle(styleString);
		}
		
		return style;
		
	}

	@Override
	public Vector copyVector() {
		return new Text(this);
	}
	
}
