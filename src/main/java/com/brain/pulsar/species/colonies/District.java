package com.brain.pulsar.species.colonies;

import com.brain.pulsar.other.Scope;
import com.brain.pulsar.species.ResourceBucket;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.types.DistrictType;

public class District implements Constructible {
	
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

	public int getAmount() {
		return amount;
	}

	@Override
	public ResourceBucket getCost() {
		return new ResourceBucket(type.getId(), type.getCost(), null, 1);
	}

	@Override
	public int getBuildTime() {
		return type.getBuildTime();
	}

	@Override
	public void build() {
		amount++;
	}
	
}
