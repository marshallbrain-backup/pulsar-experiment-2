package main.java.com.brain.ion.graphics.vectors;

import java.awt.Shape;

public interface Vector extends Cloneable {

	Vector clone();
	
	Shape getShape();

	default Shape getShape(int x, int y) {
		return getShape();
	}
	
}
