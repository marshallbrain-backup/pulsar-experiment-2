package com.brain.pulsar.empires.colonies.jobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.brain.pulsar.empires.colonies.types.JobType;
import com.brain.pulsar.resources.Resource;

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

	public JobType getType() {
		
		// TODO Auto-generated method stub
		return jobType;
	}

	public List<Job> createJobs() {
		
		List<Job> jobs = new ArrayList<>();
		
		for(int i = 0; i < amount; i++) {
			jobs.add(new Job(jobType));			
		}
		
		return jobs;
	}
	
}
