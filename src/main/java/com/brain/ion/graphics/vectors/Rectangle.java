package main.java.com.brain.ion.graphics.vectors;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rect")
public class Rectangle implements Vector {
	
	private int x;
	private int y;
	@XmlAttribute(name = "w")
	private int width;
	@XmlAttribute(name = "h")
	private int hight;
	
	@XmlAttribute(name = "style")
	private String styleString;
	
	private Map<String, String> style;
	
	public Rectangle() {
	}
	
	public Rectangle(Rectangle base) {
		
		x = base.x;
		y = base.y;
		width = base.width;
		hight = base.hight;
		styleString = base.styleString;
		style = base.style;
		
	}

	@Override
	public Shape getShape() {
		
		if(width <= 0) {
			width = 1;
		}
		
		if(hight <= 0) {
			hight = 1;
		}
		
		return new Rectangle2D.Double(x, y, width, hight);
		
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
		return new Rectangle(this);
	}
	
}
