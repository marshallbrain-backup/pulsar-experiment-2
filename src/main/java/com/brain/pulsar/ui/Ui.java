package com.brain.pulsar.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.brain.ion.graphics.ScreenPosition;
import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.input.Mouse;
import com.brain.ion.xml.IonXmlRoot;
import com.brain.pulsar.ui.map.StarSystemUi;
import com.brain.pulsar.ui.view.View;
import com.brain.pulsar.ui.view.ViewFactory;
import com.brain.pulsar.universe.StarSystem;

public class Ui {
	
	private StarSystemUi map;
	private List<View> viewList;
	private Map<String, BufferedImage> imageMap;
	
	/**
	 * Creates a new Ui
	 * 
	 * @param mainSystem
	 *            The StarSystem to desplay on screen
	 */
	public Ui(StarSystem mainSystem) {
		
		viewList = new ArrayList<>();
		
		imageMap = VectorGraphics.loadImages(new File("gfx"));
		
		Map<String, VectorGroup> vg = VectorGraphics.loadVectors(new File("gfx"));
		
		Map<String, VectorGroup> bodys = IonXmlRoot.getVectorGroups(vg, "^body\\.", true);
		Map<String, VectorGroup> bodyToolTip = IonXmlRoot.getVectorGroups(vg, "^body_tool_tip\\.", true);
		Map<String, VectorGroup> views = IonXmlRoot.getVectorGroups(vg, "^view_", false);
		
		vg.clear();
		
		ViewFactory viewCreator = new ViewFactory(viewList, views);
		
		map = new StarSystemUi(mainSystem, bodys, bodyToolTip, viewCreator);
		
	}
	
	/**
	 * Processes a game tick for this class.
	 * 
	 * @param m
	 *            The mouse listener being used
	 */
	public void tick(Mouse m) {
		
		boolean action = false;
		
		for(View v: viewList) {
			if(!action && v.tick(m)) {
				action = true;
			}
		}
		
		if(!action) {
			map.tick(m);
		}
		
//		cleanViewList();
		
	}
	
	private void cleanViewList() {

		List<Integer> hashCodes = new ArrayList<>();
		for(int i = viewList.size()-1; i >= 0; i--) {
			
			View v = viewList.get(i);
			
			if(v.getWindowCode() == 0) {
				continue;
			}
			
			if(hashCodes.contains(v.getWindowCode())) {
				viewList.remove(i);
				System.out.println("removed view");
			} else {
				hashCodes.add(v.getWindowCode());
			}
			
		}
		
	}
	
	/**
	 * Draws the ui to the screen
	 * 
	 * @param g
	 *            The graphics object that is linked to the JFrame
	 */
	public void render(VectorGraphics g) {
		
		g.loadImages(imageMap);
		
		map.render(g);
		
		for(View v: viewList) {
			g.setTranslate(ScreenPosition.ZERO);
			v.render(g);
		}
		
	}
	
}
