package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.brain.pulsar.xml.elements.Modifier;

public class JobCollection implements ResourceManager {
	
	private Map<String, Map<String, List<Job>>> jobs;

	public JobCollection() {
		
		jobs = new HashMap<>();
	}

	private void addJobs(List<Job> list) {
		
		for(Job j: list) {
			
			Map<String, List<Job>> type = new HashMap<>();
			Map<String, List<Job>> temp1 = jobs.putIfAbsent(j.getType(), type);
			
			if(temp1 != null) {
				type = temp1;
			}
			
			List<Job> ids = new ArrayList<>();
			List<Job> temp2 = type.putIfAbsent(j.getName(), ids);
			
			if(temp2 != null) {
				ids = temp2;
			}
			
			ids.add(j);
			
		}
	}

	public void addJobs(JobManager manager) {
		addJobs(manager.getJobs());
	}

	@Override
	public void applyModifiers(String chain, boolean match, Modifier modifier) {
		
		for(Job j: jobs.values().stream()
				.flatMap(order -> order.values().stream())
				.flatMap(List::stream)
				.toArray(Job[]::new)) {
			
			StringBuilder bld = new StringBuilder(chain);
			
			if(!chain.isEmpty()) {
				bld.append(".");
			}
			bld.append(j.getType());
			bld.append(".");
			bld.append(j.getName());
			String newChain = bld.toString();
			
			boolean newMatch = modifierMatcher(newChain, modifier.getParent(), match);
			
			j.applyModifiers(newChain, newMatch, modifier);
			
		}
		
	}

	@Override
	public String getType() {
		
		return "job";
	}

	@Override
	public String getName() {
		
		return "";
	}
	
	@Override
	public List<Resource> getResources() {
		
		List<Resource> res = new ArrayList<>();
		
		for(Job j: jobs.values().stream()
				.flatMap(order -> order.values().stream())
				.flatMap(List::stream)
				.toArray(Job[]::new)) {
			
			for(Resource r: j.getResources()) {
				res.add(r);
			}
			
		}
		
		return res;
	}
	
}
