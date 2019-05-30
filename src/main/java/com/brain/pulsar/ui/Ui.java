package com.brain.pulsar.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.brain.ion.graphics.ScreenPosition;
import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.input.Mouse;
import com.brain.pulsar.ui.map.StarSystemUi;
import com.brain.pulsar.ui.view.View;
import com.brain.pulsar.universe.StarSystem;

public class Ui {
	
	private StarSystemUi map;
	private List<View> viewList;
	
	/**
	 * Creates a new Ui
	 * 
	 * @param mainSystem
	 *            The StarSystem to desplay on screen
	 */
	public Ui(StarSystem mainSystem) {
		
		viewList = new ArrayList<>();
		
		Map<String, VectorGroup> vg = VectorGraphics.loadVectors(new File("gfx"));
		
		Map<String, VectorGroup> bodys = getGroups(vg, "body\\..*", true);
		Map<String, VectorGroup> bodyToolTip = getGroups(vg, "body_tool_tip\\..*", true);
		
		vg.clear();
		
		map = new StarSystemUi(mainSystem, bodys, bodyToolTip, viewList);
		
	}
	
	/**
	 * Processes a game tick for this class.
	 * 
	 * @param m
	 *            The mouse listener being used
	 */
	public void tick(Mouse m) {
		
		map.tick(m);
		
		for(View v: viewList) {
			v.tick();
		}
		
		cleanViewList();
		
	}
	
	private void cleanViewList() {
		
		
		
	}
	
	/**
	 * Draws the ui to the screen
	 * 
	 * @param g
	 *            The graphics object that is linked to the JFrame
	 */
	public void render(VectorGraphics g) {
		
		map.render(g);
		
		g.setTranslate(ScreenPosition.ZERO);
		
		for(View v: viewList) {
			v.render(g);
		}
		
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
