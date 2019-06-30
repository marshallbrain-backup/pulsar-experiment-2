package com.brain.pulsar.empires;

import java.util.List;

import com.brain.pulsar.empires.colonies.Colony;
import com.brain.pulsar.empires.colonies.types.BuildingType;
import com.brain.pulsar.empires.colonies.types.DistrictType;
import com.brain.pulsar.universe.Body;

public class Empire {
	
	private List<DistrictType> districtTypes;
	private List<BuildingType> buildingTypes;
	
	public Empire(List<DistrictType> dt, List<BuildingType> bt) {
		districtTypes = dt;
		buildingTypes = bt;
	}

	public Colony createColony(Body b) {
		
		return new Colony(b, districtTypes, buildingTypes);
	}
	
}
