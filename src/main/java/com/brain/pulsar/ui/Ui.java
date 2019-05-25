package main.java.com.brain.pulsar.ui;

import java.awt.Graphics2D;
import java.io.File;
import java.util.Map;

import main.java.com.brain.ion.graphics.VectorGraphics;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;
import main.java.com.brain.pulsar.ui.map.StarSystemUi;
import main.java.com.brain.pulsar.universe.StarSystem;

public class Ui {
	
	private StarSystemUi map;

	public Ui(StarSystem mainSystem) {
		
		map = new StarSystemUi(mainSystem);
		
		Map<String, VectorGroup> vg = VectorGraphics.loadVectors(new File("gfx"));
		vg.size();
		
	}

	public void render(Graphics2D g) {
		
		map.render(g);
		
	}
	
}
