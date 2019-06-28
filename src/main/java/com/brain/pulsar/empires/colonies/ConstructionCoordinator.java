package com.brain.pulsar.empires.colonies;

import java.util.ArrayList;
import java.util.List;

import com.brain.pulsar.info_containers.Entry;
import com.brain.pulsar.info_containers.TimeEntry;

public class ConstructionCoordinator {
	
	private List<ConstructionEntity> constructionEntities;
	
	public ConstructionCoordinator() {
		
		constructionEntities = new ArrayList<>();
	}
	
	public void add(Constructible c) {
		constructionEntities.add(new ConstructionEntity(c));
	}
	
	public List<ConstructionEntity> getConstructionList() {
		
		return new ArrayList<>(constructionEntities);
	}

	public void tick(TimeEntry time) {
		
		if(constructionEntities.isEmpty()) {
			return;
		}
		
		time = constructionEntities.get(0).tick(time);
		if(constructionEntities.get(0).isBuilt()) {
			constructionEntities.remove(0);
			tick(time);
		}
		
	}

	public void flush() {
		
		for(ConstructionEntity e: constructionEntities) {
			e.getTarget().build();
		}
		
		constructionEntities.clear();
		
	}
	
}

class ConstructionEntity {
	
	private int buildTime;
	
	private final Constructible target;
	
	public ConstructionEntity(Constructible target) {
		
		this.target = target;
		buildTime = target.getBuildTime();
	}

	public boolean isBuilt() {
		
		return buildTime <= 0;
	}

	public Constructible getTarget() {
		
		return target;
	}

	public TimeEntry tick(TimeEntry time) {
		
		Entry<TimeEntry, Integer> e = time.remove(buildTime);
		buildTime = e.getValue();
		if(buildTime <= 0) {
			target.build();
		}
		
		return e.getKey();
	}
	
}
