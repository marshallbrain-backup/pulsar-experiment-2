package com.brain.pulsar.ui.view;

import java.util.List;
import java.util.Map;

import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.xml.IonXmlRoot;
import com.brain.pulsar.ui.view.body.BodyOverview;
import com.brain.pulsar.universe.Body;

public class ViewFactory {
	
	private List<View> viewList;
	private Map<String, VectorGroup> views;
	
	public ViewFactory(List<View> viewList, Map<String, VectorGroup> views) {
		this.viewList = viewList;
		this.views = views;
	}

	public void create(ViewType targetView, Object... paramaters) {
		
		switch(targetView) {
			case BODY_OVERVIEW:
				createBodyOverview(paramaters);
				break;
			default:
				System.out.println("unrecognized view type: " + targetView);
		}
		
	}
	
	private void createBodyOverview(Object... paramaters) {
		
		Body targetBody = (Body) paramaters[0];
		
		Map<String, VectorGroup> view = IonXmlRoot.getVectorGroups(views, "view_body\\..*", true);
		
		viewList.add(new BodyOverview(targetBody, view));
		
	}
	
}
