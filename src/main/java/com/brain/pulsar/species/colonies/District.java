package com.brain.pulsar.species.colonies;

import com.brain.pulsar.other.Scope;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.types.DistrictType;

public class District {
	
	private DistrictType type;

	private District(DistrictType type) {
		this.type = type;
	}

	public static District create(DistrictType districtType, Body body) {
		
		if(districtType.isPotential(new Scope(body.getType()))) {
			return new District(districtType);
		}
		
		return null;
	}
	
}
