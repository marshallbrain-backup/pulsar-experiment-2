package main.java.com.brain.pulsar.ui;

import java.awt.Graphics2D;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.pulsar.ui.map.StarSystemUi;
import main.java.com.brain.pulsar.universe.StarSystem;

public class Ui {
	
	private StarSystemUi map;

	public Ui(StarSystem mainSystem) {
		
		Map<String, VectorGroup> vg = VectorGraphics.loadVectors(new File("gfx"));
		
		Map<String, VectorGroup> bodys = getGroups(vg, "body\\..*", true);
		
		vg.clear();
		
		map = new StarSystemUi(mainSystem, bodys);
		
	}
	
	private Map<String, VectorGroup> getGroups(Map<String, VectorGroup> vg, String regex, boolean cut){
		
		Map<String, VectorGroup> group = new HashMap<>();
		
		for(Entry<String, VectorGroup> e: vg.entrySet()) {
			if(e.getKey().matches(regex)) {
				
				String key = e.getKey();
				
				if(cut) {
					key = key.substring(key.indexOf('.')+1);
				}
				
				group.put(key, new VectorGroup(e.getValue()));
				
			}
		}
		
		return group;
		
	}

	public void render(VectorGraphics g) {
		
		map.render(g);
		
	}
	
}
