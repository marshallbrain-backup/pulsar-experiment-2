package com.brain.pulsar.ui.view;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.input.Mouse;

public interface View {

	boolean tick(Mouse m);

	void render(VectorGraphics g);

	int getWindowCode();
	
}
