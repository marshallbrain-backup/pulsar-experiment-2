package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.types.BuildingType;
import com.brain.pulsar.xml.types.DistrictType;

public class Colony {
	
	private District[] districts;
	
	private final Body parent;

	private final List<Building> buildings;
	private final List<DistrictType> districtTypes;
	private final List<BuildingType> buildingTypes;
	
	private final ConstructionCoordinator constructionManager;
	
	public Colony(Body b, List<DistrictType> dt, List<BuildingType> bt) {
		
		parent = b;
		
		districts = new District[4];
		buildings = new ArrayList<>();
		districtTypes = Collections.unmodifiableList(dt);
		buildingTypes = Collections.unmodifiableList(bt);
		constructionManager = new ConstructionCoordinator();
		
		initDistricts();
		initBuildings();
		
		constructionManager.flush();
	}

	private void initDistricts() {
		
		for(int i = 0; i < districts.length; i++) {
			districts[i] = new District();
		}
		
		int i = 0;
		for(DistrictType dt: districtTypes) {
			if(districts[i].setRetoolingType(dt, parent)) {
				constructionManager.add(districts[i]);
				i++;
			}
		}
		
	}

	private void initBuildings() {
		
		for(int i = 0; i < 10; i++) {
			buildings.add(new Building());
		}
		
		int i = 0;
		for(BuildingType bt: buildingTypes) {
			if(buildings.get(i).setPendingType(bt, parent)) {
				constructionManager.add(buildings.get(i));
				i++;
			}
		}
		
	}
	
	public List<District> getDistricts(){
		return Arrays.asList(districts);
	}

	public List<Building> getBuildings() {
		
		return new ArrayList<>(buildings);
	}
	
}
