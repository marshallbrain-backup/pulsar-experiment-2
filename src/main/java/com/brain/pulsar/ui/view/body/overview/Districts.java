package com.brain.pulsar.ui.view.body.overview;

import java.awt.Shape;
import java.util.List;
import java.util.Map;

import com.brain.ion.graphics.ScreenPosition;
import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.Image;
import com.brain.ion.graphics.vectors.Text;
import com.brain.ion.graphics.vectors.Vector;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.input.Mouse;
import com.brain.pulsar.empires.colonies.Colony;
import com.brain.pulsar.empires.colonies.District;
import com.brain.pulsar.ui.view.RenderEntry;

public class Districts {

	public static void action(Mouse m) {
		
		
		
	}
	
	public static void render(VectorGraphics g, Map<String, VectorGroup> vectorGroups, List<RenderEntry> shapes, Colony colony) {
		
		frame(g, vectorGroups, shapes, colony);
		entrys(g, vectorGroups, shapes, colony);
		
	}
	
	public static void frame(VectorGraphics g, Map<String, VectorGroup> vectorGroups, List<RenderEntry> shapes, Colony colony) {
		
		VectorGroup info = vectorGroups.get("districts");

		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(info.getOrigin());
		
		Vector frame = info.getVectorById("frame");
		Shape f = frame.getShape();
		f = g.getAffineTransform().createTransformedShape(f);
		
		Vector text = info.getVectorById("district_text");
		Shape t = text.getShape(g.getGraphics());
		t = g.getAffineTransform().createTransformedShape(t);
		
		Vector div = info.getVectorById("divider");
		Shape d = div.getShape();
		d = g.getAffineTransform().createTransformedShape(d);
		
		shapes.add(new RenderEntry(f, frame.getStyle()));
		shapes.add(new RenderEntry(t, text.getStyle()));
		shapes.add(new RenderEntry(d, div.getStyle()));
		
	}
	
	public static void entrys(VectorGraphics g, Map<String, VectorGroup> vectorGroups, List<RenderEntry> shapes, Colony colony) {
		
		List<District> districts = colony.getDistricts();
		
		for(int i = 0; i < districts.size(); i++) {
			
			VectorGroup info = vectorGroups.get("districts_entry");

			g.setTranslate(ScreenPosition.ZERO);
			g.moveTranslate(150, 100 + i*55);
			g.moveTranslate(info.getOrigin());
			
			Vector frame = info.getVectorById("frame");
			Shape f = frame.getShape();
			f = g.getAffineTransform().createTransformedShape(f);
			
			Vector icon = info.getVectorById("icon");
			((Image) icon).setXform(g.getAffineTransform());
			
			Text text = (Text) info.getVectorById("amount");
			text = text.setText(
					String.format("%02d", districts.get(i).getAmount()) + "/" + 
					String.format("%02d", districts.get(i).getAmount() + colony.getRemainingDistricts()));
			Shape t = text.getShape(g.getGraphics());
			t = g.getAffineTransform().createTransformedShape(t);
				
			shapes.add(new RenderEntry(icon));
			shapes.add(new RenderEntry(f, frame.getStyle()));
			shapes.add(new RenderEntry(t, text.getStyle()));
			
		}
		
	}
	
}
