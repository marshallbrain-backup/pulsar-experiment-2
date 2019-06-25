package com.brain.pulsar.other;

import java.util.List;

import com.brain.pulsar.xml.elements.Modifier;

public interface ResourceManager {

	String getType();
	String getName();
	
	List<Resource> getResources();
	
	void applyModifiers(List<Modifier> modifiers);
	
}
