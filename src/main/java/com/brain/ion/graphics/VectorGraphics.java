package main.java.com.brain.ion.graphics;

import java.awt.Graphics2D;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.brain.ion.XmlParser;
import main.java.com.brain.ion.graphics.vectors.Circle;
import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;

public class VectorGraphics {
	
	private Graphics2D graphics;
	
	public VectorGraphics(Graphics2D g) {
		
		graphics = g;
		
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
				
				String path = f.getPath();
				path = path.substring(0, path.indexOf('.')).replace('\\', '.');
				VectorGroup vg = (VectorGroup) XmlParser.getXml(f, classes);
				
				vectors.put(path, vg);
				
			}
			
		}
		
	}
	
}
