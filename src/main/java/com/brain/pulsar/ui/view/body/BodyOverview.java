package com.brain.pulsar.ui.view.body;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Map;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.Vector;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.input.Mouse;
import com.brain.pulsar.ui.view.View;
import com.brain.pulsar.universe.Body;

public class BodyOverview implements View {
	
	private int windowCode;
	
	private Body body;
	private Area lastRendered;
	private Map<String, VectorGroup> vectorGroups;

	public BodyOverview(Body body, Map<String, VectorGroup> vectorGroups) {
		
		this.body = body;
		this.vectorGroups = vectorGroups;
		
		lastRendered = new Area();
		
	}

	@Override
	public boolean tick(Mouse m) {
		
		if(lastRendered.contains(m.getPosition())) {
			return true;
		}
		
		return false;
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		lastRendered.reset();
		windowCode = g.getParentCode();
		g.moveTranslate(150, 100);
		
		VectorGroup frame = vectorGroups.get("frame");
		VectorGroup header = vectorGroups.get("header");
		VectorGroup info = vectorGroups.get("basic_info");
		
		g.beginAreaRendering();
		
		for(Vector v: frame.getVectors()) {
			g.draw(v.getShape(), v.getStyle());
		}
		
		for(Vector v: header.getVectors()) {
			g.draw(v.getShape(), v.getStyle());
		}
		
		g.moveTranslate(info.getOrigin());
		
		for(Vector v: info.getVectors()) {
			g.draw(v.getShape(g.getGraphics()), v.getStyle());
		}
		
		lastRendered.add(g.resetAreaRendering());
		
	}
	
	@Override
	public int getWindowCode() {
		return windowCode;
	}
	
}
