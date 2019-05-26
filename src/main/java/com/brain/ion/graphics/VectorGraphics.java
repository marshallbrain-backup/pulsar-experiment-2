package main.java.com.brain.ion.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
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
		
	}
	
	public static Map<String, VectorGroup> loadVectors(File gfx) {
		
		Map<String, VectorGroup> vectors = new HashMap<>();
		Class<?>[] classes = {VectorGroup.class, Circle.class};
		
		readFiles(gfx, vectors, classes);
		
		return vectors;
		
	}
	
	private static void readFiles(File file, Map<String, VectorGroup> vectors, Class<?>[] classes) {
		
		for(File f: file.listFiles()) {
			
			if(f.isDirectory()) {
				readFiles(f, vectors, classes);
			}
			if(f.isFile()) {
				VectorGroup vg = (VectorGroup) XmlParser.getXml(f, classes);
				
				vectors.put(vg.getPath(), vg);
				
			}
			
		}
		
	}
	
	public int getWindowWidth() {
		return Integer.parseInt(settings.get(SettingEntry.WINDOWED_WIDTH));
	}
	
	public int getWindowHeight() {
		return Integer.parseInt(settings.get(SettingEntry.WINDOWED_HEIGHT));
	}

	public void draw(Shape s) {
		
		s = currentTransform.createTransformedShape(s);
		
		graphics.setColor(Color.WHITE);
		graphics.fill(s);
		
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
	
}
