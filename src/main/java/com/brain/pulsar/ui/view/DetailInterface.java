package com.brain.pulsar.ui.view;

import java.util.Map;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.VectorGroup;

public interface DetailInterface {

	void detailRender(VectorGraphics g, Map<String, VectorGroup> vectorGroups, Object target);

}
