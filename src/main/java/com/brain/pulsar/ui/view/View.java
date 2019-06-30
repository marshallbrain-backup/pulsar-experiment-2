package com.brain.pulsar.ui.view;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;

import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.Text;
import com.brain.ion.graphics.vectors.Vector;
import com.brain.ion.input.Mouse;

public interface View {

	boolean tick(Mouse m);

	void render(VectorGraphics g);

	int getWindowCode();
	
	public static void scaleVector(VectorGraphics g, List<RenderEntry> shapes, Vector[] infoShapeList, Vector infoFrame) {
		
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
			shapes.add(new RenderEntry(s, v.getStyle()));
			
		}
		
		Shape infoFrameShape = infoFrame.getShape();
		
		AffineTransform infoFrameAT = new AffineTransform(g.getAffineTransform());
		infoFrameAT.scale(1/infoFrameShape.getBounds2D().getWidth(), 1);
		infoFrameAT.scale(max, 1);
		
		shapes.add(new RenderEntry(infoFrameAT.createTransformedShape(infoFrameShape), infoFrame.getStyle()));
		
	}
	
}
