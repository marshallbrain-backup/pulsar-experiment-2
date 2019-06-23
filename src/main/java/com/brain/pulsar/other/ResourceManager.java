package com.brain.pulsar.other;

import java.util.List;

public interface ResourceManager {

	String getType();
	String getName();
	
	List<Resource> getResources();
	
}
