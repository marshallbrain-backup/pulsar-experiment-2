package com.brain.pulsar.xml.elements;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.brain.pulsar.other.Resource;
import com.brain.pulsar.other.ResourceBucket;

@XmlRootElement(name = "job_type")
public class JobType {
	
	@XmlElement
	private final String id;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private final List<ResourceBase> upkeep;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private final List<ResourceBase> production;
	
	public JobType() {
		
		id = null;
		upkeep = null;
		production = null;
	}
	
	public String getId() {
		return id;
	}
	
	public ResourceBucket getUpkeep() {
		
		List<Resource> u = new ArrayList<>();
		
		for(ResourceBase r: upkeep) {
			u.add(new Resource(r, true));
		}
		
		return new ResourceBucket(u, "upkeep", null);
	}
	
	public ResourceBucket getProduction() {
		
		List<Resource> p = new ArrayList<>();
		
		for(ResourceBase r: production) {
			p.add(new Resource(r));
		}
		
		return new ResourceBucket(p, "production", null);
	}
	
}
