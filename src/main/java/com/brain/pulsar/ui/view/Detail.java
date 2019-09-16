package com.brain.pulsar.ui.view;

import java.util.Map;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.VectorGroup;

public class Detail {
	
	private DetailInterface base;
	private Object target;

	public Detail(DetailInterface base, Object target) {
		this.base = base;
		this.target = target;
	}

	public void render(VectorGraphics g, Map<String, VectorGroup> vectorGroups) {
		base.detailRender(g, vectorGroups, target);
	}

}
