package com.brain.pulsar.ui.view.body.overview;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;
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
import com.brain.pulsar.empires.colonies.types.DistrictType;
import com.brain.pulsar.ui.view.Detail;
import com.brain.pulsar.ui.view.DetailInterface;
import com.brain.pulsar.ui.view.RenderEntry;

public class Districts implements DetailInterface {
	
	private static List<Area> entryArea;

	public static Detail action(Mouse m, List<District> districts) {
		
		if(m.buttonClicked(1) && entryArea != null) {
			
			for(int i = 0; i < entryArea.size(); i++) {
				
				if(entryArea.get(i).contains(m.getPosition())) {
					return new Detail(new Districts(), districts.get(i));
				}
				
			}
			
		}
		
		return null;
		
	}
	
	private static Detail actionEntry(Mouse m, List<District> districts) {

//		Mouse mouseOffset = new Mouse(m, 150 + vg.getOrigin().x, 100 + vg.getOrigin().y);
//		
//		Vector frame = vg.getVectorById("frame");
//		Shape f = frame.getShape();
//		
//		Point offset = new Point(mouseOffset.getPosition().x, mouseOffset.getPosition().y%(f.getBounds().height+5));
//		
//		Area clickable = new Area(f);
//		if(clickable.contains(offset)) {
//			int i = mouseOffset.getPosition().y/(f.getBounds().height+5);
//			return new Detail(new Districts(), districts.get(i));
//		}
//		
		return null;
		
	}
	
	public static void render(VectorGraphics g, Map<String, VectorGroup> vectorGroups, Colony colony) {
		
		if(entryArea == null) {
			entryArea = new ArrayList<>();
		}
		
		entryArea.clear();
		
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
			x += frame.getShape().getBounds().height+3;
			entryArea.add(new Area(g.getAffineTransform().createTransformedShape(frame.getShape())));
			
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
		
		if(district.isAssined()) {
			renderTooledButtons(g, vectorGroups);
			renderTooledDistricts(g, vectorGroups, district);
		} else {
			renderUntooledDistricts(g, vectorGroups, district);
		}
		
	}
	
	private void renderTooledDistricts(VectorGraphics g, Map<String, VectorGroup> vectorGroups, District district) {
		
		VectorGroup divs = vectorGroups.get("district_tooled.types");
		VectorGroup type = vectorGroups.get("district.type");
		
		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(divs.getOrigin());

		renderDistrictType(g, type, district.getDistrictType());
		
	}
	
	private void renderTooledButtons(VectorGraphics g, Map<String, VectorGroup> vectorGroups) {
		
		VectorGroup info = vectorGroups.get("district_tooled.button");
		
		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(info.getOrigin());
		
		Vector bulid = info.getVectorById("build");
		g.draw(bulid.getShape(), bulid.getStyle());
		
		Vector demolish = info.getVectorById("demolish");
		g.draw(demolish.getShape(), demolish.getStyle());
		
		Vector replace = info.getVectorById("replace");
		g.draw(replace.getShape(), replace.getStyle());
		
		Vector retool = info.getVectorById("retool");
		g.draw(retool.getShape(), retool.getStyle());
		
		Vector divider = info.getVectorById("divider");
		g.draw(divider.getShape(), divider.getStyle());
		
	}

	private void renderUntooledDistricts(VectorGraphics g, Map<String, VectorGroup> vectorGroups, District district){

		VectorGroup type = vectorGroups.get("district.type");
		VectorGroup list = vectorGroups.get("district.untooled");

		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(list.getOrigin());

		for(DistrictType d: district.getParant().getDistrictTypes()) {
			renderDistrictType(g, type, d);
		}

	}

	private void renderDistrictType(VectorGraphics g, VectorGroup type, DistrictType district) {

		Vector frame = type.getVectorById("frame");
		g.draw(frame.getShape(), frame.getStyle());

		Vector line1 = type.getVectorById("line1");
		g.draw(line1.getShape(), line1.getStyle());

		Vector line2 = type.getVectorById("line2");
		g.draw(line2.getShape(), line2.getStyle());

		Text name = (Text) type.getVectorById("name");
		name = name.setText(district.getName());
		g.draw(name.getShape(g.getGraphics()), name.getStyle());

		Text info = (Text) type.getVectorById("info");
		info = info.setText(district.getUpkeepString());
		g.draw(info.getShape(g.getGraphics()), info.getStyle());

	}
	
}
