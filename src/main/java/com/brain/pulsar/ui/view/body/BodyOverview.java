package com.brain.pulsar.ui.view.body;

import java.util.Map;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.Vector;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.pulsar.ui.view.View;
import com.brain.pulsar.universe.Body;

public class BodyOverview implements View {
	
	private int windowCode;
	
	private Body body;
	private Map<String, VectorGroup> vectorGroups;

	public BodyOverview(Body body, Map<String, VectorGroup> vectorGroups) {
		
		this.body = body;
		this.vectorGroups = vectorGroups;
		
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		windowCode = g.getParentCode();
		
		VectorGroup frame = vectorGroups.get("frame");
		
		for(Vector v: frame.getVectors()) {
			g.draw(v.getShape(), v.getStyle());
		}
		
	}

	@Override
	public int getWindowCode() {
		return windowCode;
	}
	
}
