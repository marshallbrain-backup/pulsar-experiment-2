package com.brain.ion.graphics.vectors;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

/**
 * The interface for a vector.
 * 
 * @author Marshall Brain
 *
 */
public interface Vector {
	
	/**
	 * @return A copy of the current vector
	 */
	Vector copyVector();
	
	/**
	 * Generates a {@link java.awt.Shape} representation of the vector.
	 * 
	 * @return The shape
	 */
	Shape getShape();
	
	/**
	 * Generates a {@link java.awt.Shape} representation of the vector. Also is
	 * given the current graphics object incase it is needed. EX - Generating a path
	 * based on a string is best done with a graphics object.
	 * 
	 * @param g
	 *            The graphics object that is linked to the JFrame
	 * @return
	 */
	default Shape getShape(Graphics2D g) {
		
		// If this method is not overridden than call the default getShape().
		return getShape();
	}
	
	/**
	 * Gets the style of the vector. Used when rendering.
	 * 
	 * @return The style map
	 */
	Map<String, String> getStyle();
	
	/**
	 * Converts a style in string format to a style map.
	 * 
	 * @param s
	 *            The style as a string
	 * @return The style map
	 */
	default Map<String, String> convertStyle(String s) {
		
		if (s == null) {
			return null;
		}
		
		s = s.replaceAll("\\s+", "");
		
		Map<String, String> style = new HashMap<>();
		
		for (String e : s.split(";")) {
			
			String key = e.split(":")[0];
			String value = e.split(":")[1];
			
			style.put(key, value);
			
		}
		
		return style;
		
	}
	
}
