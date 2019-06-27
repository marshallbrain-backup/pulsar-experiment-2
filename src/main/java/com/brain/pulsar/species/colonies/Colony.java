package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.types.DistrictType;

public class Colony {
	
	private District[] districts;
	
	private final Body parent;
	
	private final List<DistrictType> districtTypes;
	
	public Colony(Body b, List<DistrictType> dt) {
		
		parent = b;
		
		districts = new District[4];
		districtTypes = Collections.unmodifiableList(dt);
		
		initDistricts();
	}

	private void initDistricts() {
		
		for(int i = 0; i < districts.length; i++) {
			districts[i] = new District();
		}
		
		int i = 0;
		for(DistrictType dt: districtTypes) {
			if(districts[i].setDistrictType(dt, parent)) {
				i++;
			}
		}
		
	}
	
	public List<District> getDistricts(){
		return Arrays.asList(districts);
	}
	
}
