package main.java.com.brain.ion.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.brain.ion.SettingEntry;
import main.java.com.brain.ion.XmlParser;
import main.java.com.brain.ion.graphics.vectors.Circle;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;

public class VectorGraphics {
	
	private Graphics2D graphics;
	private AffineTransform currentTransform;
	
	private Map<SettingEntry, String> settings;
	
	public VectorGraphics(Graphics2D g, Map<SettingEntry, String> s) {
		
		graphics = g;
		settings = s;
		
		g.clipRect(0, 0, getWindowWidth(), getWindowHeight());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
	}
	
	public static Map<String, VectorGroup> loadVectors(File gfx) {
		
		Map<String, VectorGroup> vectors = new HashMap<>();
		Class<?>[] classes = {IonXmlRoot.class, VectorGroup.class, Circle.class};
		
		readFiles(gfx, vectors, classes);
		
		return vectors;
		
	}
	
	private static void readFiles(File file, Map<String, VectorGroup> vectors, Class<?>[] classes) {
		
		for(File f: file.listFiles()) {
			
			if(f.isDirectory()) {
				readFiles(f, vectors, classes);
			}
			if(f.isFile()) {
				IonXmlRoot xr = (IonXmlRoot) XmlParser.getXml(f, classes);
				
				for(VectorGroup vg: xr.getVectorGroups()) {
					vectors.put(vg.getPath(), vg);
				}
				
			}
			
		}
		
	}
	
	public int getWindowWidth() {
		return Integer.parseInt(settings.get(SettingEntry.WINDOWED_WIDTH));
	}
	
	public int getWindowHeight() {
		return Integer.parseInt(settings.get(SettingEntry.WINDOWED_HEIGHT));
	}

	public void draw(Shape s, Map<String, String> style) {
		
		s = currentTransform.createTransformedShape(s);
		
		if(style != null) {
			
			String fill = style.get("fill");
			if(!(fill == null || fill.equals("none"))) {
				
				String alpha = style.get("fill-opacity");
				if(alpha == null)
					alpha = "1";
				
				graphics.setColor(getColor(fill, alpha));
				graphics.fill(s);
				
			}
			
			String stroke = style.get("stroke");
			if(!(stroke == null || stroke.equals("none"))) {
				
				String alpha = style.get("stroke-opacity");
				if(alpha == null)
					alpha = "1";
				
				String width = style.get("stroke-width");
				if(width == null)
					width = "1";
				BasicStroke bs = new BasicStroke(Integer.parseInt(width));
				
				graphics.setColor(getColor(stroke, alpha));
				graphics.setStroke(bs);
				graphics.draw(s);
				
			}
			
		}
		
	}

	public void setTranslate(ScreenPosition center) {
		
		currentTransform = new AffineTransform();
		
		switch(center) {
			case ZERO:
				currentTransform.translate(0, 0);
				break;
			case CENTER:
				currentTransform.translate(getWindowWidth()/2, getWindowHeight()/2);
				break;
			default:
				return;
		}
		
	}

	public void moveTranslate(double x, double y) {
		
		currentTransform.translate(x, y);
		
	}
	
	private Color getColor(String hex, String alpha) {
		
		Color c = null;
			
		if(hex.startsWith("#")) {
			
			hex = hex.substring(1);
			
			int r = Integer.parseInt(hex.substring(0, 2), 16);
			int g = Integer.parseInt(hex.substring(2, 4), 16);
			int b = Integer.parseInt(hex.substring(4, 6), 16);
			int a = Math.round(Float.parseFloat(alpha)*255);
			
			c = new Color(r, g, b, a);
			
		}
		
		return c;
		
	}
	
}
