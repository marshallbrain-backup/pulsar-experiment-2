package com.brain.pulsar.species.colonies;


public class ConstrictionEntity {
	
	private int time;
	
	private final Constructible entity;

	public ConstrictionEntity(Constructible district) {
		entity = district;
		time = district.getBuildTime();
	}

	public int removeTime(int i) {
		
		time -= i;
		if(time < 0) {
			i = time*-1;
		} else {
			i = 0;
		}
		
		return i;
	}

	public boolean isFinnished() {
		return time <= 0;
	}

	public Constructible getEntity() {
		return entity;
	}
	
	
	
}
