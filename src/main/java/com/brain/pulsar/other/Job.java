package com.brain.pulsar.other;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.brain.pulsar.xml.elements.JobBase;
import com.brain.pulsar.xml.elements.JobType;

public class Job {
	
	private double amount;
	
	private String id;
	private String type;
	
	private JobType jobType;
	
	private ResourceBucket upkeep;
	private ResourceBucket production;

	public Job(JobBase r) {
		
		jobType = r.getType();
		amount = r.getAmount();
		id = jobType.getId();
		type = jobType.getType();
		
		upkeep = jobType.getUpkeep();
		production = jobType.getProduction();
		
	}

	public String getType() {
		
		return type;
	}

	public String getId() {
		
		return id;
	}
	
}
