package main.java.com.brain.ion.graphics.vectors;

import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

public interface Vector extends Cloneable {

	Vector clone();
	
	Shape getShape();

	Map<String, String> getStyle();
	
	default Map<String, String> convertStyle(String s) {
		
		if(s == null) {
			return null;
		}
		
		Map<String, String> style = new HashMap<String, String>();
		
		for(String e: s.split(";")) {
			
			String key = e.split(":")[0];
			String value = e.split(":")[1];
			
			style.put(key, value);
			
		}
		
		return style;
		
	}
	
}
