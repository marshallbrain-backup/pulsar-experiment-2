package com.brain.pulsar.ui.view;

import java.util.List;

import com.brain.pulsar.ui.view.body.BodyOverview;
import com.brain.pulsar.universe.Body;

public class ViewFactory {
	
	private List<View> viewList;
	
	public ViewFactory(List<View> viewList) {
		this.viewList = viewList;
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
		
		viewList.add(new BodyOverview(targetBody));
		
	}
	
}
