package com.brain.ion.graphics.vectors;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImageOp;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "image")
public class Image implements Vector {

	@XmlAttribute
	private int x;
	@XmlAttribute
	private int y;
	@XmlAttribute(name = "w")
	private int width;
	@XmlAttribute(name = "h")
	private int hight;

	@XmlAttribute
	private String id;
	@XmlAttribute
	private String path;
	
	private AffineTransform affineTransform;
	
	public Image() {
	}
	
	public Image(Image image) {
		
		x = image.x;
		y = image.y;
		width = image.width;
		hight = image.hight;
		id = image.id;
		path = image.path;
		
	}
	
	public String getImagePath() {
		return path;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setXform(AffineTransform af) {
		affineTransform = af;
	}

	public AffineTransform getXform() {
		
		AffineTransform af = new AffineTransform(affineTransform);
		af.translate(x, y);
		
		return af;
	}

	@Override
	public String getId() {
		
		return id;
	}
	
	@Override
	public Vector copyVector() {
		
		return new Image(this);
	}

	@Override
	public Shape getShape() {
		
		return null;
	}

	@Override
	public Map<String, String> getStyle() {
		
		return null;
	}
	
}
