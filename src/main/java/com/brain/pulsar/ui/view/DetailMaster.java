package com.brain.pulsar.ui.view;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.input.Mouse;

public class DetailMaster implements View {
	
	private Detail current;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWindowCode() {
		return 0;
	}

}
