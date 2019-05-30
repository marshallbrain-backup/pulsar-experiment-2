package com.brain.ion.graphics.vectors;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "path")
public class Path implements Vector {
	
	@XmlAttribute(name = "x")
	private int offsetX;
	@XmlAttribute(name = "y")
	private int offsetY;
	
	@XmlAttribute(name = "style")
	private String styleString;
	@XmlAttribute(name = "p")
	private String pathString;
	
	private Map<String, String> style;
	
	private Path2D path;
	
	public Path() {
	}
	
	public Path(Path clone) {
		
		offsetX = clone.offsetX;
		offsetY = clone.offsetY;
		styleString = clone.styleString;
		pathString = clone.pathString;
		style = clone.style;
		path = clone.path;
		
	}
	
	private void parsePath() {
		
		path = new Path2D.Double();
		
		Pattern number = Pattern.compile("(^[-]?[0-9]+[\\.]?[0-9]*)(?:[\\p{Space}[,]]?)([-]?[0-9]*[\\.]?[0-9]*)");
		Pattern letter = Pattern.compile("^[a-zA-Z]");
		Pattern space = Pattern.compile("^[\\p{Space}]");
		
		String parsingString = pathString;
		String currentType = "m";
		while(!parsingString.isEmpty()) {
			
			Matcher m;
			
			m = letter.matcher(parsingString);
			if(m.find()) {
				
				currentType = m.group();
				parsingString = parsingString.substring(m.end());
				if(currentType.equalsIgnoreCase("z")) {
					path.closePath();
				}
				
			}
			
			m = number.matcher(parsingString);
			if(m.find()) {
				
				double x = 0;
				double y = 0;
				
				String s = m.group();
				
				try {
					x = Double.parseDouble(m.group(1));
					y = Double.parseDouble(m.group(2));
				} catch(IndexOutOfBoundsException | NumberFormatException e) {
				}
				
				String t = currentType;
				Point2D lastPoint = path.getCurrentPoint();
				if(lastPoint == null) {
					lastPoint = new Point2D.Double();
				}
				
				if(t.equalsIgnoreCase("v")) {
					y = x;
					x = 0;
				}
				if(t.equals(t.toLowerCase()) || t.equals("H")) {
					x += lastPoint.getX();
				}
				if(t.equals(t.toLowerCase()) || t.equals("V")) {
					y += lastPoint.getY();
				}
				
				if(t.equalsIgnoreCase("v") || t.equalsIgnoreCase("h")) {
					t = "l";
				}
				
				if(t.equalsIgnoreCase("m")) {
					path.moveTo(x, y);
				} else if(t.equalsIgnoreCase("l")) {
					path.lineTo(x, y);
				}
				
				parsingString = parsingString.substring(m.end());
				
			}
			
			m = space.matcher(parsingString);
			if(m.find()) {
				
				parsingString = parsingString.substring(m.end());
				
			}
			
		}
		
	}
	
	@Override
	public Shape getShape() {
		
		if(path == null) {
			parsePath();
		}
		
		return path;
		
	}
	
	@Override
	public Map<String, String> getStyle() {
		
		if (style == null) {
			style = convertStyle(styleString);
		}
		
		return style;
	}
	
	@Override
	public Vector copyVector() {

		return new Path(this);
	}
	
}
