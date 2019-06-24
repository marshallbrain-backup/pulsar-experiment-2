package com.brain.pulsar.xml.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.brain.pulsar.other.Resource;

@XmlRootElement(name = "modifier")
public class JobBase {
	
	@XmlValue
	private final double amount;
	
	@XmlAttribute
	private final String id;
	
	private JobType jobType;
	
	public JobBase() {
		id = null;
		amount = 0;
	}

	public void setType(List<JobType> jobTypes) {
		
		for(JobType t: jobTypes) {
			if(t.getId().equals(id)) {
				jobType = t;
			}
		}
		
	}

	public String getId() {
		
		return id;
	}

	public double getAmount() {
		
		return amount;
	}

	public List<Resource> getUpkeep() {
		
		// TODO Auto-generated method stub
		return null;
	}

	public List<Resource> getProduction() {
		
		// TODO Auto-generated method stub
		return null;
	}
	
}
