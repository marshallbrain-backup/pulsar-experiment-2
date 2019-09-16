package com.brain.pulsar.ui.view;

import java.util.Map;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.input.Mouse;

public class DetailMaster implements View {
	
	private Detail current;
	
	private Map<String, VectorGroup> vectorGroups;
	
	public DetailMaster(Map<String, VectorGroup> vectorGroups) {
		this.vectorGroups = vectorGroups;
		
	}

	public void setDetail(Detail newCurrent) {
		current = newCurrent;
	}

	@Override
	public boolean tick(Mouse m) {
		
		if(current != null) {
			
		}
		
		return false;
	}

	@Override
	public void render(VectorGraphics g) {
		
		if(current != null) {
			current.render(g, vectorGroups);
		}
		
	}

	@Override
	public int getWindowCode() {
		return 0;
	}

}
