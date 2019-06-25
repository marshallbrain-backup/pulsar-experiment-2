package com.brain.pulsar.other;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.brain.pulsar.xml.elements.JobBase;
import com.brain.pulsar.xml.elements.JobType;

public class Job {
	
	private String id;
	private String type;
	
	private JobType jobType;

	public Job(JobType t) {
		
		jobType = t;
		id = jobType.getId();
		type = jobType.getType();
		
	}

	public String getType() {
		
		return type;
	}

	public String getId() {
		
		return id;
	}

	public ResourceManager getResources() {
		
		ResourceBucket operations = new ResourceBucket(type, id);
		
		operations.combine(jobType.getUpkeep());
		operations.combine(jobType.getProduction());
		
		return operations;
	}
	
}
