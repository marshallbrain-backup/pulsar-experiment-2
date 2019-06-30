package com.brain.pulsar.ui.view.body;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.brain.ion.graphics.ScreenPosition;
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
		
		List<renderEntry> shapes = new ArrayList<>();
		
		lastRendered.reset();
		windowCode = g.getParentCode();
		g.moveTranslate(150, 100);
		
		VectorGroup frame = vectorGroups.get("frame");
		VectorGroup header = vectorGroups.get("header");
		
		g.beginAreaRendering();
		
		for(Vector v: frame.getVectors()) {
			g.draw(v.getShape(), v.getStyle());
		}
		
		for(Vector v: header.getVectors()) {
			g.draw(v.getShape(), v.getStyle());
		}
		
		renderBasicInfo(g, vectorGroups, shapes);
		renderBasicColonyInfo(g, vectorGroups, shapes);
		
		g.setTranslate(ScreenPosition.ZERO);
		for(renderEntry e: shapes) {
			g.draw(e.shape, e.style);
		}
		lastRendered.add(g.resetAreaRendering());
		
	}
	
	private void renderBasicInfo(VectorGraphics g, Map<String, VectorGroup> vectorGroups, List<renderEntry> shapes) {
		
		VectorGroup info = vectorGroups.get("basic_info");

		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(info.getOrigin());
		
		Vector infoFrame = info.getVectorById("frame");
		Vector planetType = ((Text)info.getVectorById("planet_type")).setText(body.getName());
		
		Vector[] infoShapeList = new Vector[] {planetType};
		
		scaleVector(g, shapes, infoShapeList, infoFrame);
		
	}
	
	private void renderBasicColonyInfo(VectorGraphics g, Map<String, VectorGroup> vectorGroups, List<renderEntry> shapes) {
		
		VectorGroup info = vectorGroups.get("basic_info_colony");

		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(info.getOrigin());
		
		Vector infoFrame = info.getVectorById("frame");
		Vector planetType = ((Text)info.getVectorById("colony_type")).setText(body.getColony().getDesignation());
		
		Vector[] infoShapeList = new Vector[] {planetType};
		
		scaleVector(g, shapes, infoShapeList, infoFrame);
		
	}
	
	private void scaleVector(VectorGraphics g, List<renderEntry> shapes, Vector[] infoShapeList, Vector infoFrame) {
		
		double max = 0;
		for(Vector v: infoShapeList) {
			
			Shape s = v.getShape(g.getGraphics());
			
			Rectangle2D b = s.getBounds2D();
			double w = b.getX() + b.getWidth();
			if(v instanceof Text) {
				w += ((Text) v).getPadding().getX();
			}
			if(max < w) {
				max = w;
			}
			
			s = g.getAffineTransform().createTransformedShape(s);
			shapes.add(new renderEntry(s, v.getStyle()));
			
		}
		
		Shape infoFrameShape = infoFrame.getShape();
		
		AffineTransform infoFrameAT = new AffineTransform(g.getAffineTransform());
		infoFrameAT.scale(1/infoFrameShape.getBounds2D().getWidth(), 1);
		infoFrameAT.scale(max, 1);
		
		shapes.add(new renderEntry(infoFrameAT.createTransformedShape(infoFrameShape), infoFrame.getStyle()));
		
	}
	
	@Override
	public int getWindowCode() {
		return windowCode;
	}
	
}

class renderEntry {
	
	public final Shape shape;
	public final Map<String, String> style;
	
	public renderEntry(Shape shape, Map<String, String> style) {
		this.shape = shape;
		this.style = style;
	}
	
}
