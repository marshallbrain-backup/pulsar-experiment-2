package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.List;

import com.brain.pulsar.xml.elements.Modifier;

public class ResourceBucket implements ResourceManager {
	
	private String name;
	private String type;
	
	private List<Resource> resources;

	public ResourceBucket(String type, String name) {
		
		this.name = name;
		this.type = type;
		
		resources = new ArrayList<>();
		
	}

	public ResourceBucket(List<Resource> r, String type, String name) {
		
		this(type, name);
		
		resources.addAll(r);
	}

	public void addResource(List<Resource> list, String type, String name) {
		
		for(Resource r: list) {
			
			r.appendChain(type, name);
			
			int i = resources.indexOf(r);
			
			if(i == -1) {
				resources.add(r);
			} else {
				r.combine(resources.set(i, r));
			}
			
		}
		
	}

	public void combine(ResourceBucket b) {
		
		addResource(b.resources, b.type, b.name);
	}

	@Override
	public String getType() {
		
		return type;
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public List<Resource> getResources() {
		
		return resources;
	}
	
}
