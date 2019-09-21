package com.brain.pulsar.ui.view;

import java.util.Map;

import com.brain.ion.graphics.ScreenPosition;
import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.Vector;
import com.brain.ion.graphics.vectors.VectorGroup;

public class Detail {
	
	private DetailInterface parent;
	private Object target;

	public Detail(DetailInterface parent, Object target) {
		this.parent = parent;
		this.target = target;
	}

	public void render(VectorGraphics g, Map<String, VectorGroup> vectorGroups) {
		
		VectorGroup base = vectorGroups.get("base");

		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(base.getOrigin());
		
		Vector frame = base.getVectorById("frame");
		g.draw(frame.getShape(), frame.getStyle());
		
		parent.detailRender(g, vectorGroups, target);
	}

}
