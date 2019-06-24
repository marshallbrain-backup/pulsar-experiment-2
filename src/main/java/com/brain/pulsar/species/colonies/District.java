package com.brain.pulsar.species.colonies;

import com.brain.pulsar.other.JobCollection;
import com.brain.pulsar.other.ResourceBucket;
import com.brain.pulsar.other.ResourceManager;
import com.brain.pulsar.other.Scope;
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

	public int getAmount() {
		return amount;
	}

	public ResourceManager getOperations() {
		
		ResourceBucket operations = new ResourceBucket("district", type.getName());
		
		operations.combine(type.getUpkeep());
		operations.combine(type.getProduction());
		
		return operations;
		
	}

	public ResourceManager getSupply() {
		
		return type.getSupply();
		
	}
	
}
