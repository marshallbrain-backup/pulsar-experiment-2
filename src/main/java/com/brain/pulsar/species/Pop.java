package com.brain.pulsar.other;


public class Pop {
	
	private Job job;

	public boolean isUnimployed() {
		
		return job == null;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
}
