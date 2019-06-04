package com.brain.pulsar.species.colonies;

import com.brain.pulsar.other.Scope;
import com.brain.pulsar.species.ResourceBucket;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.types.DistrictType;

public class District {
	
	private int amount;
	
	private DistrictType type;

	public static District create(DistrictType districtType, Body body) {
		
		if(districtType.isPotential(new Scope(body.getType()))) {
			return new District(districtType);
		}
		
		return null;
	}

	private District(DistrictType type) {
		this.type = type;
	}
	
	public ResourceBucket getResources() {
		
		return new ResourceBucket(type.getId(), type.getOperations().getUpkeep(), type.getOperations().getProduction());
		
	}
	
}
