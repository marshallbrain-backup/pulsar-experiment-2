package com.brain.pulsar.xml.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.brain.ion.xml.StringTrimAdapter;
import com.brain.pulsar.buckets.ResourceBucket;
import com.brain.pulsar.other.Scope;
import com.brain.pulsar.xml.elements.Job;
import com.brain.pulsar.xml.elements.Resource;
import com.brain.pulsar.xml.elements.Trigger;

@XmlRootElement(name = "district")
public class DistrictType {
	
	@XmlElement
	private String name;
	
	@XmlElementWrapper
	@XmlElement(name = "trigger")
	private List<Trigger> potential;
	
	@XmlElementWrapper
	@XmlElement(name = "trigger")
	private List<Trigger> starting;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<Resource> upkeep;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<Resource> production;
	
	@XmlElementWrapper
	@XmlElement(name = "job")
	private List<Job> supply;

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
		
		ResourceBucket bucket = new ResourceBucket(null, "upkeep");
		
		for(Resource r: upkeep) {
			r.makeNegitive();
		}
		
		bucket.addResource(upkeep);
		
		return bucket;
	}
	
	public ResourceBucket getProduction() {
		
		ResourceBucket bucket = new ResourceBucket(null, "production");
		
		bucket.addResource(production);
		
		return bucket;
	}
	
}
