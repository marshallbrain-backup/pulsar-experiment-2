package com.brain.pulsar.species.colonies;

import com.brain.pulsar.species.ResourceBucket;

public interface Constructible {

	ResourceBucket getCost();

	int getBuildTime();

	void build();
	
}
