package com.brain.pulsar.empires.colonies.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.resources.ResourceBase;

@XmlRootElement(name = "job_type")
public class JobType {
	
	@XmlElement
	private final String id;
	@XmlElement
	private final String type;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private final List<ResourceBase> upkeep;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private final List<ResourceBase> production;
	
	public JobType() {
		
		type = "worker";
		
		id = null;
		upkeep = new ArrayList<>();
		production = new ArrayList<>();
	}
	
	public JobType(String id, List<ResourceBase> upkeep, List<ResourceBase> production) {
		
		type = "worker";
		
		this.id = id;
		this.upkeep = upkeep;
		this.production = production;
	}
	
	public String getId() {
		return id;
	}

	public String getType() {
		
		return type;
	}
	
	public List<Resource> getUpkeep() {
		
		List<Resource> u = new ArrayList<>();
		
		for(ResourceBase r: upkeep) {
			u.add(new Resource(r, "upkeep", true));
		}
		
		return u;
	}
	
	public List<Resource> getProduction() {
		
		List<Resource> p = new ArrayList<>();
		
		for(ResourceBase r: production) {
			p.add(new Resource(r, "production"));
		}
		
		return p;
	}
	
}
