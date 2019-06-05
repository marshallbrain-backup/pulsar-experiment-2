package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import com.brain.pulsar.species.ResourceBucket;
import com.brain.pulsar.species.ResourceMaster;

public class ConstructionCoordinator {
	
	private List<ConstrictionEntity> constructionQueue;
	
	public ConstructionCoordinator() {
		constructionQueue = new ArrayList<>();
	}

	public boolean addToQueue(Constructible district, ResourceMaster total) {
		
		ResourceBucket cost = district.getCost();
		
		if(total.transaction(cost)) {
			constructionQueue.add(new ConstrictionEntity(district));
			return true;
		}
		
		return false;
	}

	public boolean tick(int i) {
		
		boolean constructed = false;
		List<ConstrictionEntity> remove = new ArrayList<>();
		
		for(ConstrictionEntity e: constructionQueue) {
			i = e.removeTime(i);
			if(e.isFinnished()) {
				Constructible c = e.getEntity();
				c.build();
				remove.add(e);
				constructed = true;
			}
			if(i <= 0) {
				break;
			}
		}
		
		return constructed;
	}
	
	
	
}
