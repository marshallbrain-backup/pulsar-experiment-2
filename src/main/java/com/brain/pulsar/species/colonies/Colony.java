package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.brain.pulsar.other.Job;
import com.brain.pulsar.other.JobCollection;
import com.brain.pulsar.other.PopCollection;
import com.brain.pulsar.other.ResourceCollection;
import com.brain.pulsar.other.TimeEntry;
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
	private final ResourceCollection resourceCollection;
	private final JobCollection jobs;
	private final PopCollection pops;
	
	public Colony(Body b, List<DistrictType> dt, List<BuildingType> bt) {
		
		parent = b;
		
		districts = new District[4];
		buildings = new ArrayList<>();
		districtTypes = Collections.unmodifiableList(dt);
		buildingTypes = Collections.unmodifiableList(bt);
		constructionManager = new ConstructionCoordinator();
		resourceCollection = new ResourceCollection("colony", "standard");
		pops = new PopCollection();
		jobs = new JobCollection(pops.getPopList());
		
		resourceCollection.addJobCollection(jobs);
		
		initDistricts();
		initBuildings();
		
		constructionManager.flush();
		resourceCollection.updateMap();
	}

	private void initDistricts() {
		
		for(int i = 0; i < districts.length; i++) {
			District d = new District();
			districts[i] = d;
			resourceCollection.addManagers(d);
		}
		
		int i = 0;
		for(DistrictType dt: districtTypes) {
			if(districts[i].setRetoolingType(dt, parent)) {
				constructionManager.add(districts[i]);
				constructionManager.add(districts[i]);
				i++;
			}
		}
		
	}

	private void initBuildings() {
		
		for(int i = 0; i < 10; i++) {
			Building b = new Building();
			buildings.add(b);
			resourceCollection.addManagers(b);
		}
		
		int i = 0;
		for(BuildingType bt: buildingTypes) {
			if(buildings.get(i).setPendingType(bt, parent)) {
				constructionManager.add(buildings.get(i));
				i++;
			}
		}
		
	}
	
	public void tick(TimeEntry time) {
		pops.tick(time);
		jobs.tick();
	}
	
	public List<District> getDistricts(){
		return Arrays.asList(districts);
	}

	public List<Building> getBuildings() {
		return new ArrayList<>(buildings);
	}

	public Map<String, List<Job>> getJobs() {
		return jobs.getJobs();
	}
	
}
