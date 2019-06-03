package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brain.pulsar.xml.elements.Resource;

public class ConstructionCoordinator {
	
	private List<Constructible> constructonQueue;
	
	private Map<Resource, Resource> total;

	public ConstructionCoordinator(Map<Resource, Resource> total) {
		
		constructonQueue = new ArrayList<>();
		this.total = new HashMap<>(total);
	}

	public void tick() {
		
		Constructible c = constructonQueue.get(0);
		c.finnishBuild();
		
	}

	public void addToQueue(Constructible district) {
		
		if(constructonQueue.add(district)) {
			
			district.queueBuild();
			List<Resource> cost = district.getBuildCost();
			
			for(Resource r: cost) {
				Resource base = total.remove(r);
				base = base.append(r, -1);
				total.put(base, base);
			}
			
		}
		
	}

	public void finnishFromQueue(int index) {
		
		Constructible district = constructonQueue.remove(index);
		district.finnishBuild();
		
	}
	
}
