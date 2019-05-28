package main.java.com.brain.ion.graphics.vectors;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "circle")
public class Circle implements Vector {
	
	@XmlAttribute(name = "cx")
	private int centerX;
	@XmlAttribute(name = "cy")
	private int centerY;
	@XmlAttribute(name = "r")
	private int radius;
	
	@XmlAttribute(name = "style")
	private String styleString;
	
	private Map<String, String> style;
	
	public Circle() {
	}
	
	public Circle(Circle base) {
		
		centerX = base.centerX;
		centerY = base.centerY;
		radius = base.radius;
		styleString = base.styleString;
		style = base.style;
		
	}

	@Override
	public Shape getShape() {
		
		if(radius <= 0) {
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

	@Override
	public Map<String, String> getStyle() {
		
		if(style == null) {
			style = convertStyle(styleString);
		}
		
		return style;
		
	}

	@Override
	public Vector copyVector() {
		return new Circle(this);
	}
	
}
