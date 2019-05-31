package com.brain.pulsar.ui.view.body;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.Text;
import com.brain.ion.graphics.vectors.Vector;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.input.Mouse;
import com.brain.pulsar.ui.view.View;
import com.brain.pulsar.universe.Body;

public class BodyOverview implements View {
	
	private int windowCode;
	
	private Body body;
	private Area lastRendered;
	private Map<String, VectorGroup> vectorGroups;

	public BodyOverview(Body body, Map<String, VectorGroup> vectorGroups) {
		
		this.body = body;
		this.vectorGroups = vectorGroups;
		
		lastRendered = new Area();
		
	}

	@Override
	public boolean tick(Mouse m) {
		
		if(lastRendered.contains(m.getPosition())) {
			return true;
		}
		
		return false;
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		if(windowCode == 0) {
			windowCode = g.getParentCode();
			return;
		}
		
		Map<Shape, Map<String, String>> shapes = new LinkedHashMap<>();
		
		lastRendered.reset();
		windowCode = g.getParentCode();
		g.moveTranslate(150, 100);
		
		VectorGroup frame = vectorGroups.get("frame");
		VectorGroup header = vectorGroups.get("header");
		VectorGroup info = vectorGroups.get("basic_info");
		
		g.beginAreaRendering();
		
		for(Vector v: frame.getVectors()) {
			g.draw(v.getShape(), v.getStyle());
		}
		
		for(Vector v: header.getVectors()) {
			g.draw(v.getShape(), v.getStyle());
		}
		
		g.moveTranslate(info.getOrigin());
		
		Vector infoFrame = info.getVectorById("frame");
		Vector colonyType = ((Text)info.getVectorById("colony_type")).setText(body.getName());
		Vector planetType = info.getVectorById("planet_type");
		
		Vector[] infoShapeList = new Vector[] {colonyType, planetType};
		
		double max = 0;
		for(Vector v: infoShapeList) {
			
			Shape s = v.getShape(g.getGraphics());
			shapes.put(s, v.getStyle());
			
			Rectangle2D b = s.getBounds2D();
			double w = b.getX() + b.getWidth();
			if(v instanceof Text) {
				w += ((Text) v).getPadding().getX();
			}
			if(max < w) {
				max = w;
			}
			
		}
		
		Shape infoFrameShape = infoFrame.getShape();
		
		AffineTransform infoFrameAT = new AffineTransform();
		infoFrameAT.scale(1/infoFrameShape.getBounds2D().getWidth(), 1);
		infoFrameAT.scale(max, 1);
		
		shapes.put(infoFrameAT.createTransformedShape(infoFrameShape), infoFrame.getStyle());
		
		for(Entry<Shape, Map<String, String>> e: shapes.entrySet()) {
			g.draw(e.getKey(), e.getValue());
		}
		
		lastRendered.add(g.resetAreaRendering());
		
	}
	
	@Override
	public int getWindowCode() {
		return windowCode;
	}
	
}
