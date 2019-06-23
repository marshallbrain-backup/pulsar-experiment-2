package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.List;

import com.brain.pulsar.xml.elements.Modifier;

public class ResourceBucket {
	
	private final String name;
	private final String type;
	
	private List<ResourceBucket> buckets;
	private List<Resource> resources;

	public ResourceBucket(String type, String name) {
		
		this.name = name;
		this.type = type;
		
		buckets = new ArrayList<>();
		resources = new ArrayList<>();
		
	}

	public void addBucket(ResourceBucket bucket) {
		
		buckets.add(bucket);
	}

	public void merge() {
		
		for(ResourceBucket b: buckets) {
			
			b.merge();
			
			for(Resource r: b.getResources()) {
				
				if(resources.contains(r)) {
					int i = resources.indexOf(r);
					r.combine(resources.set(i, r));
					
				} else {
					resources.add(r);
				}
				
			}
			
		}
		
	}

	public void addResource(List<Resource> list) {
		
		resources.addAll(list);
	}

	public List<Resource> getResources() {
		
		return resources;
	}
	
}
