package com.brain.pulsar.empires.colonies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.brain.pulsar.empires.colonies.jobs.Job;
import com.brain.pulsar.empires.colonies.jobs.JobCollection;
import com.brain.pulsar.empires.colonies.types.BuildingType;
import com.brain.pulsar.empires.colonies.types.DistrictType;
import com.brain.pulsar.info_containers.TimeEntry;
import com.brain.pulsar.resources.ResourceCollection;
import com.brain.pulsar.species.PopCollection;
import com.brain.pulsar.universe.Body;

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
		
		districts = new District[5];
		buildings = new ArrayList<>();
		districtTypes = new ArrayList<>(dt);
		buildingTypes = Collections.unmodifiableList(bt);
		constructionManager = new ConstructionCoordinator();
		resourceCollection = new ResourceCollection("colony", "standard");
		pops = new PopCollection();
		jobs = new JobCollection(pops.getPopList());
		
		resourceCollection.addManagers(jobs);
		
		initDistricts();
		initBuildings();
		
		constructionManager.flush();
		resourceCollection.updateMap();
	}

	private void initDistricts() {
		
		for(int i = 0; i < districts.length; i++) {
			District d = new District(this);
			districts[i] = d;
			resourceCollection.addManagers(d);
		}
		
		int i = 0;
		List<DistrictType> remove = new ArrayList<>();
		for(DistrictType dt: districtTypes) {
			if(districts[i].setRetoolingType(dt, parent)) {
				constructionManager.add(districts[i]);
				remove.add(dt);
				i++;
			}
		}

		districtTypes.removeAll(remove);
		
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

	public String getDesignation() {
		
		return "Colony";
	}

	public int getRemainingDistricts() {
		
		return 10;
	}

	public List<DistrictType> getDistrictTypes() {
		return districtTypes;
	}
}
