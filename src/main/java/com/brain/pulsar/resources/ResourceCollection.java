package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brain.pulsar.xml.elements.Modifier;

public class ResourceCollection implements ResourceManager {
	
	private String name;
	private String type;

	private Map<String, List<ResourceManager>> managerList;
	private List<List<Resource>> resources;
	private JobCollection jobs;

	public ResourceCollection(String type, String name) {
		
		this.name = name;
		this.type = type;
		
		managerList = new HashMap<>();
		resources = new ArrayList<>();
	}

	public void addManagers(ResourceManager... managers) {
		
		for(ResourceManager m: managers) {
			
			List<ResourceManager> list = managerList.get(m.getKey());
			if(list == null) {
				list = new ArrayList<>();
				managerList.put(m.getKey(), list);
			}
			
			list.add(m);
			
			resources.add(m.getResources());
			
			if(m instanceof JobManager) {
				jobs.addJobs((JobManager) m);
			}
			
		}
		
	}
	
	public void updateMap() {
		
		List<ResourceManager> updateList = new ArrayList<>();
		for(Entry<String, List<ResourceManager>> e: managerList.entrySet()) {
			
			List<ResourceManager> removeList = new ArrayList<>();
			for(ResourceManager r: e.getValue()) {
				
				if(!r.getKey().equals(e.getKey())) {
					removeList.add(r);
					updateList.add(r);
				}
				
			}
			
			e.getValue().removeAll(removeList);
			
		}
		
		addManagers(updateList.toArray(new ResourceManager[0]));
		
	}

	@Override
	public void applyModifiers(String chain, boolean match, Modifier modifier) {
		
		for(Entry<String, List<ResourceManager>> e: managerList.entrySet()) {
			
			StringBuilder bld = new StringBuilder(chain);
			
			if(!chain.isEmpty()) {
				bld.append(".");
			}
			bld.append(e.getKey());
			String newChain = bld.toString();
			
			boolean newMatch = modifierMatcher(newChain, modifier.getParent(), match);
			
			for(ResourceManager rm: e.getValue()) {
				rm.applyModifiers(newChain, newMatch, modifier);
			}
			
		}
		
	}

	@Override
	public String getType() {
		
		return type;
	}

	@Override
	public String getName() {
		
		return name;
	}
	
	@Override
	public List<Resource> getResources() {
		
		Map<String, Resource> o = new HashMap<>();
		
		for(List<Resource> l: resources) {
			for(Resource r: l) {
				
				Resource res = o.get(r.getId());
				if(res == null) {
					res = new Resource(r, 0);
				}
				
				Resource modRes = r.zip();
				res = res.combine(modRes);
				o.put(r.getId(), res);
				
			}
		}
		
		return new ArrayList<>(o.values());
	}

	public void addJobCollection(JobCollection jobs) {
		this.jobs = jobs;
		
	}
	
}
