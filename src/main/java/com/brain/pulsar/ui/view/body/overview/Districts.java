package com.brain.pulsar.ui.view.body.overview;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;
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
import com.brain.pulsar.ui.view.Detail;
import com.brain.pulsar.ui.view.DetailInterface;
import com.brain.pulsar.ui.view.RenderEntry;

public class Districts implements DetailInterface {

	public static Detail action(Mouse m, Map<String, VectorGroup> vectorGroups, List<District> districts) {
		
		if(m.buttonClicked(1)) {
			
			VectorGroup vg = vectorGroups.get("districts");

			Mouse mouseOffset = new Mouse(m, 150 + vg.getOrigin().x, 100 + vg.getOrigin().y);
			
			Vector frame = vg.getVectorById("frame");
			Shape f = frame.getShape();
			
			Area clickable = new Area(f);
			if(clickable.contains(mouseOffset.getPosition())) {
				return actionEntry(m, vectorGroups.get("districts_entry"), districts);
			}
			
		}
		
		return null;
		
	}
	
	private static Detail actionEntry(Mouse m, VectorGroup vg, List<District> districts) {

		Mouse mouseOffset = new Mouse(m, 150 + vg.getOrigin().x, 100 + vg.getOrigin().y);
		
		Vector frame = vg.getVectorById("frame");
		Shape f = frame.getShape();
		
		Point offset = new Point(mouseOffset.getPosition().x, mouseOffset.getPosition().y%(f.getBounds().height+5));
		
		Area clickable = new Area(f);
		if(clickable.contains(offset)) {
			int i = mouseOffset.getPosition().y/(f.getBounds().height+5);
			return new Detail(new Districts(), districts.get(i));
		}
		
		return null;
		
	}
	
	public static void render(VectorGraphics g, Map<String, VectorGroup> vectorGroups, Colony colony) {
		
		frame(g, vectorGroups, colony);
		entrys(g, vectorGroups, colony);
		
	}
	
	private static void frame(VectorGraphics g, Map<String, VectorGroup> vectorGroups, Colony colony) {
		
		VectorGroup info = vectorGroups.get("districts");

		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(info.getOrigin());
		
		Vector frame = info.getVectorById("frame");
		g.draw(frame.getShape(), frame.getStyle());
		
		Vector text = info.getVectorById("district_text");
		g.draw(text.getShape(g.getGraphics()), text.getStyle());
		
		Vector div = info.getVectorById("divider");
		g.draw(div.getShape(), div.getStyle());
		
	}
	
	private static void entrys(VectorGraphics g, Map<String, VectorGroup> vectorGroups, Colony colony) {
		
		List<District> districts = colony.getDistricts();
		
		int x = 0;
		
		for(int i = 0; i < districts.size(); i++) {
			
			VectorGroup info = vectorGroups.get("districts_entry");

			g.setTranslate(ScreenPosition.ZERO);
			g.moveTranslate(150, 100 + x);
			g.moveTranslate(info.getOrigin());
			
			Vector frame = info.getVectorById("frame");
			g.draw(frame.getShape(), frame.getStyle());
			x += frame.getShape().getBounds().height+5;
			
			Vector icon = info.getVectorById("icon");
			g.draw(icon.getShape(), icon.getStyle());
			
			Text text = (Text) info.getVectorById("amount");
			text = text.setText(
					String.format("%02d", districts.get(i).getAmount()) + "/" + 
					String.format("%02d", districts.get(i).getAmount() + colony.getRemainingDistricts()));
			g.draw(text.getShape(g.getGraphics()), text.getStyle());
			
		}
		
	}

	@Override
	public void detailRender(VectorGraphics g, Map<String, VectorGroup> vectorGroups, Object target) {
		
		District district = (District) target;
		
		System.out.println(district.getName());
		
	}
	
}
