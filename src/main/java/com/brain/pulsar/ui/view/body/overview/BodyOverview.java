package com.brain.pulsar.ui.view.body.overview;

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
import com.brain.pulsar.ui.view.Detail;
import com.brain.pulsar.ui.view.DetailMaster;
import com.brain.pulsar.ui.view.RenderEntry;
import com.brain.pulsar.ui.view.View;
import com.brain.pulsar.universe.Body;

public class BodyOverview implements View {
	
	private int windowCode;
	
	private Body body;
	private Area lastRendered;
	private DetailMaster currentDetail;
	
	private Map<String, VectorGroup> vectorGroups;

	public BodyOverview(Body body, Map<String, VectorGroup> vectorGroups, DetailMaster currentDetail) {
		
		this.body = body;
		this.currentDetail = currentDetail;
		this.vectorGroups = vectorGroups;
		
		lastRendered = new Area();
		
	}

	@Override
	public boolean tick(Mouse m) {
		
		if(lastRendered.contains(m.getPosition())) {
			
			Detail newCurrent = Districts.action(m, vectorGroups, body.getColony().getDistricts());
			if(newCurrent != null) {
				currentDetail.setDetail(newCurrent);
			}
			
			return true;
		}
		
		return false;
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		List<RenderEntry> shapes = new ArrayList<>();
		
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
		
		Information.render(g, vectorGroups, shapes, body);
		Districts.render(g, vectorGroups, shapes, body.getColony());
		
		g.setTranslate(ScreenPosition.ZERO);
		for(RenderEntry e: shapes) {
			if(e.shape == null) {
				g.draw(e.image);
			} else {
				g.draw(e.shape, e.style);
			}
		}
		
		lastRendered.add(g.resetAreaRendering());
		
	}
	
	
	
	@Override
	public int getWindowCode() {
		return windowCode;
	}
	
}
