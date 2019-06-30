package com.brain.pulsar.ui.view;

import java.awt.Shape;
import java.util.Map;

public class RenderEntry {
	
	public final Shape shape;
	public final Map<String, String> style;
	
	public RenderEntry(Shape shape, Map<String, String> style) {
		this.shape = shape;
		this.style = style;
	}
	
}
