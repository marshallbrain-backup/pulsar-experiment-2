package com.brain.pulsar.empires.colonies.jobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.brain.pulsar.resources.Modifier;
import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.resources.ResourceManager;
import com.brain.pulsar.species.Pop;

public class JobCollection implements ResourceManager {
	
	private Map<String, List<Job>> jobs;
	
	private List<Pop> pops;

	public JobCollection(List<Pop> pops) {
		
		this.pops = pops;
		
		jobs = new HashMap<>();
	}

	public void tick() {
		
		List<Pop> unimployed = pops.stream().filter(Pop::isUnimployed).collect(Collectors.toList());
		
		if(!unimployed.isEmpty()) {
			
			int i = 0;
			
			for(List<Job> l: jobs.values()) {
				
				for(Job j: l) {
				
					if(!j.hasPop()) {
						j.setPop(unimployed.get(i));
						i++;
					}
					
					if(i >= unimployed.size()) {
						break;
					}
					
				}
				
				if(i >= unimployed.size()) {
					break;
				}
				
			}
			
		}
		
	}

	private void addJobs(Job... jobArray) {
		
		for(Job j: jobArray) {
			
			List<Job> list = jobs.get(j.getKey());
			if(list == null) {
				list = new ArrayList<>();
				jobs.put(j.getKey(), list);
			}
			
			list.add(j);
			
		}
	}

	public void addJobs(JobManager manager) {
		addJobs(manager.getJobs().toArray(new Job[0]));
	}

	@Override
	public void applyModifiers(String chain, boolean match, Modifier modifier) {
		

		
		for(List<Job> l: jobs.values()) {
			for(Job j: l) {
			
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
		
		for(List<Job> l: jobs.values()) {
			for(Job j: l) {
			
				for(Resource r: j.getResources()) {
					res.add(r);
				}
			
			}
			
		}
		
		return res;
	}

	public Map<String, List<Job>> getJobs() {
		
		return jobs;
	}
	
}
