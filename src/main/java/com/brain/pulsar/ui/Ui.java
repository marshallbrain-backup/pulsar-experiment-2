package main.java.com.brain.pulsar.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.ion.input.Mouse;
import main.java.com.brain.pulsar.ui.map.StarSystemUi;
import main.java.com.brain.pulsar.universe.StarSystem;

/**
 * @author Marshall Brain
 *
 */
public class Ui {
	
	private StarSystemUi map;
	
	/**
	 * Creates a new Ui
	 * 
	 * @param mainSystem
	 *            The StarSystem to desplay on screen
	 */
	public Ui(StarSystem mainSystem) {
		
		Map<String, VectorGroup> vg = VectorGraphics.loadVectors(new File("gfx"));
		
		Map<String, VectorGroup> bodys = getGroups(vg, "body\\..*", true);
		Map<String, VectorGroup> bodyToolTip = getGroups(vg, "body_tool_tip\\..*", true);
		
		vg.clear();
		
		map = new StarSystemUi(mainSystem, bodys, bodyToolTip);
		
	}
	
	/**
	 * Processes a game tick for this class.
	 * 
	 * @param m
	 *            The mouse listener being used
	 */
	public void tick(Mouse m) {
		
		map.tick(m);
	}
	
	/**
	 * Draws the ui to the screen
	 * 
	 * @param g
	 *            The graphics object that is linked to the JFrame
	 */
	public void render(VectorGraphics g) {
		
		map.render(g);
		
	}
	
	/**
	 * Gets the group of VectorGroups that have a key matching the regex String.
	 * 
	 * @param vg
	 *            The map of VectorGroups with there corresponding ids as keys
	 * @param regex
	 *            The expression to match to
	 * @param cut
	 *            Where to cut off the group name from the key
	 * @return A map with the matching VectorGroups
	 */
	private static Map<String, VectorGroup> getGroups(Map<String, VectorGroup> vg, String regex, boolean cut) {
		
		Map<String, VectorGroup> group = new HashMap<>();
		
		Pattern regexPatern = Pattern.compile(regex);
		
		for (Entry<String, VectorGroup> e : vg.entrySet()) {
			if (regexPatern.matcher(e.getKey()).find()) {
				
				String key = e.getKey();
				
				if (cut) {
					key = key.substring(key.indexOf('.') + 1);
				}
				
				group.put(key, new VectorGroup(e.getValue()));
				
			}
		}
		
		return group;
		
	}
	
}
