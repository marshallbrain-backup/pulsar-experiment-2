package com.brain.pulsar.species.colonies;

import java.util.List;

import com.brain.pulsar.xml.elements.Resource;

public interface Constructible {

	List<Resource> getBuildCost();

	void queueBuild();

	void finnishBuild();
	
}
