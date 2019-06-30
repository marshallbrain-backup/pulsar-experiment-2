package com.brain.pulsar.ui.view.body.overview;

import java.util.List;
import java.util.Map;

import com.brain.ion.graphics.ScreenPosition;
import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.graphics.vectors.Text;
import com.brain.ion.graphics.vectors.Vector;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.pulsar.ui.view.RenderEntry;
import com.brain.pulsar.ui.view.View;
import com.brain.pulsar.universe.Body;

public class Information {

	public static void render(VectorGraphics g, Map<String, VectorGroup> vectorGroups, List<RenderEntry> shapes, Body body) {
		
		renderBasicInfo(g, vectorGroups, shapes, body);
		renderBasicColonyInfo(g, vectorGroups, shapes, body);
		
	}
	
	private static void renderBasicInfo(VectorGraphics g, Map<String, VectorGroup> vectorGroups, List<RenderEntry> shapes, Body body) {
		
		VectorGroup info = vectorGroups.get("basic_info");

		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(info.getOrigin());
		
		Vector infoFrame = info.getVectorById("frame");
		Vector planetType = ((Text)info.getVectorById("planet_type")).setText(body.getName());
		
		Vector[] infoShapeList = new Vector[] {planetType};
		
		View.scaleVector(g, shapes, infoShapeList, infoFrame);
		
	}
	
	private static void renderBasicColonyInfo(VectorGraphics g, Map<String, VectorGroup> vectorGroups, List<RenderEntry> shapes, Body body) {
		
		VectorGroup info = vectorGroups.get("basic_info_colony");

		g.setTranslate(ScreenPosition.ZERO);
		g.moveTranslate(150, 100);
		g.moveTranslate(info.getOrigin());
		
		Vector infoFrame = info.getVectorById("frame");
		Vector planetType = ((Text)info.getVectorById("colony_type")).setText(body.getColony().getDesignation());
		
		Vector[] infoShapeList = new Vector[] {planetType};
		
		View.scaleVector(g, shapes, infoShapeList, infoFrame);
		
	}
	
}
