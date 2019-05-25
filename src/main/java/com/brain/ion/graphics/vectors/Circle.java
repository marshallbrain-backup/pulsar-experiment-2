package main.java.com.brain.ion.graphics.vectors;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

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

	@Override
	public Shape getShape() {
		
		int w = radius;
		int h = radius;
		int x = centerX - w;
		int y = centerY - h;
		
		w *= 2;
		h *= 2;
		
		return new Ellipse2D.Float(x, y, w, h);
	}

	@Override
	public Shape getShape(int dx, int dy) {
		
		int w = radius + dx;
		int h = radius + dy;
		int x = centerX - h;
		int y = centerY - w;
		
		w *= 2;
		h *= 2;
		
		return new Ellipse2D.Float(x, y, w, h);
	}
	
	@Override
	public Vector clone() {
		
		try {
			return (Circle) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
