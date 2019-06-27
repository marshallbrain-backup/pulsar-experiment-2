package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.List;

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

	public void tick() {
		
		constructionEntities.get(0).tick();
		
	}
	
}

class ConstructionEntity {
	
	private int buildTime;
	
	private final Constructible target;
	
	public ConstructionEntity(Constructible target) {
		
		this.target = target;
		buildTime = target.getBuildTime();
	}

	public Constructible getTarget() {
		
		return target;
	}

	public void tick() {
		target.build();
	}
	
}
