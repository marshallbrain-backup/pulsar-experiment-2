package com.brain.pulsar.ui.view;

import java.awt.Shape;
import java.util.Map;

import com.brain.ion.graphics.vectors.Image;
import com.brain.ion.graphics.vectors.Vector;

public class RenderEntry {
	
	public final Shape shape;
	public final Map<String, String> style;
	
	public final Image image;
	
	public RenderEntry(Shape shape, Map<String, String> style) {
		this.shape = shape;
		this.style = style;
		this.image = null;
	}

	public RenderEntry(Vector icon) {
		this.shape = null;
		this.style = null;
		this.image = (Image) icon;
	}
	
}
