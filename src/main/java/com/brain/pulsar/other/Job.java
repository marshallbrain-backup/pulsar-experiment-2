package com.brain.pulsar.other;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.brain.pulsar.xml.elements.JobBase;

public class Job implements ResourceManager {
	
	private double amount;
	
	private String id;
	private String type;
	
	private ResourceBucket upkeep;
	private ResourceBucket production;

	public Job(JobBase r) {
		
		amount = r.getAmount();
		id = r.getId();
		type = "worker";
		
		upkeep = new ResourceBucket(r.getUpkeep(), type, id);
		upkeep = new ResourceBucket(r.getProduction(), type, id);
		
	}

	@Override
	public String getType() {
		
		return type;
	}

	@Override
	public String getName() {
		
		return id;
	}

	@Override
	public List<Resource> getResources() {
		
		return null;
	}
	
}
