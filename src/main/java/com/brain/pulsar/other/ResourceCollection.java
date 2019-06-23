package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceCollection implements ResourceManager {
	
	private String name;
	private String type;

	private Map<String, Map<String, ResourceManager>> managers;

	public ResourceCollection(String type, String name) {
		
		this.name = name;
		this.type = type;
		
		managers = new HashMap<>();
	}

	public void addManager(ResourceManager m) {
		
		Map<String, ResourceManager> ids = managers.getOrDefault(m.getType(), new HashMap<>());
		ids.put(m.getName(), m);
		managers.putIfAbsent(m.getType(), ids);
		
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
		
		List<Resource> l = new ArrayList<>();
		
		for(Map<String, ResourceManager> i: managers.values()) {
			for(ResourceManager j: i.values()) {
				for(Resource r: j.getResources()) {
					
					int n = l.indexOf(r);
					
					if(n == -1) {
						l.add(r);
					} else {
						r.combine(l.set(n, r));
					}
					
				}
			}
		}
		
		List<Resource> resourceList = new ArrayList<>();
		
		while(!l.isEmpty()) {

			List<Resource> addList = new ArrayList<>(l);
			List<Resource> removeList = new ArrayList<>();
			
			for(Resource r: l) {
				
				Resource newRes = r.trim();
				
				if(newRes.isChainEmpty()) {
					resourceList.add(newRes);
				} else {
					
					int n = addList.indexOf(newRes);
					
					if(n == -1) {
						addList.add(newRes);
					} else {
						newRes.combine(addList.set(n, newRes));
					}
					
				}
				
				removeList.add(r);
				
			}
			
			l.addAll(addList);
			l.removeAll(removeList);
			
		}
		
		return resourceList;
	}
	
}
