package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobCollection{
	
	private Map<String, Map<String, List<Job>>> jobs;

	public JobCollection() {
		
		jobs = new HashMap<>();
	}
	
	public JobCollection(List<Job> jobList) {
		
		this();
		addJobs(jobList);
	}

	public void addJobs(List<Job> jobList) {
		
		for(Job j: jobList) {
			
			Map<String, List<Job>> type = new HashMap<>();
			Map<String, List<Job>> temp1 = jobs.putIfAbsent(j.getType(), type);
			
			if(temp1 != null) {
				type = temp1;
			}
			
			List<Job> ids = new ArrayList<>();
			List<Job> temp2 = type.putIfAbsent(j.getId(), ids);
			
			if(temp2 != null) {
				ids = temp2;
			}
			
			ids.add(j);
			
		}
	}

	public ResourceManager getResources() {
		
		List<ResourceManager> managers = new ArrayList<>();
		
		for(Map<String, List<Job>> types: jobs.values()) {
			for(List<Job> l: types.values()) {
				for(Job j: l) {
					managers.add(j.getResources());
				}
			}
		}
		
		ResourceCollection operations = new ResourceCollection("job", "");
		
		operations.addManager( managers.toArray(new ResourceManager[0]));
		
		return operations;
	}
	
}
