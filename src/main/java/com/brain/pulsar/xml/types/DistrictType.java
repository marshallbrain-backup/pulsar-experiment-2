package com.brain.pulsar.xml.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.brain.ion.xml.StringTrimAdapter;
import com.brain.pulsar.other.Job;
import com.brain.pulsar.other.JobCollection;
import com.brain.pulsar.other.Resource;
import com.brain.pulsar.other.ResourceBucket;
import com.brain.pulsar.other.ResourceCollection;
import com.brain.pulsar.other.Scope;
import com.brain.pulsar.xml.elements.JobAdapter;
import com.brain.pulsar.xml.elements.JobBase;
import com.brain.pulsar.xml.elements.ResourceBase;
import com.brain.pulsar.xml.elements.Trigger;

@XmlRootElement(name = "district")
public class DistrictType {
	
	@XmlElement
	@XmlJavaTypeAdapter(value=StringTrimAdapter.class)
	private String name;
	
	@XmlElementWrapper
	@XmlElement(name = "trigger")
	private List<Trigger> potential;
	
	@XmlElementWrapper
	@XmlElement(name = "trigger")
	private List<Trigger> starting;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<ResourceBase> upkeep;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<ResourceBase> production;
	
	@XmlElementWrapper
	@XmlElement(name = "job")
	@XmlJavaTypeAdapter(value=JobAdapter.class)
	private List<JobBase> supply;

	public boolean isPotential(Scope bodyType) {
		
		for(Trigger t: potential) {
			if(t.isCondition(bodyType)) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isStarting(Scope bodyType) {
		
		for(Trigger t: potential) {
			if(t.isCondition(bodyType)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getName() {
		return name;
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
	
	public ResourceCollection getSupply() {
		
		return null;
	}
	
}
