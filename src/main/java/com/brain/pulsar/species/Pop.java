package com.brain.pulsar.species;

import com.brain.pulsar.empires.colonies.jobs.Job;

public class Pop {
	
	private Job job;

	public boolean isUnimployed() {
		
		return job == null;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
}
