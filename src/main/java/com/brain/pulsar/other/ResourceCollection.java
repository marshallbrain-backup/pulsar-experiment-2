package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brain.pulsar.xml.elements.Modifier;

public class ResourceCollection implements ResourceManager {
	
	private String name;
	private String type;

	private Map<String, Map<String, List<ResourceManager>>> managers;

	public ResourceCollection(String type, String name) {
		
		this.name = name;
		this.type = type;
		
		managers = new HashMap<>();
	}

	public void addManager(ResourceManager... man) {
		
		for(ResourceManager m: man) {
			
			Map<String, List<ResourceManager>> ids = managers.getOrDefault(m.getType(), new HashMap<>());
			List<ResourceManager> list = ids.getOrDefault(m.getName(), new ArrayList<>());
			
			list.add(m);
			
			ids.putIfAbsent(m.getName(), list);
			managers.putIfAbsent(m.getType(), ids);
			
		}
		
	}

	@Override
	public void applyModifiers(List<Modifier> modifiers) {
		
		for(Modifier m: modifiers) {
			
			String[] pathList = m.getParent().split("\\.");
			for(String p: Arrays.copyOfRange(pathList, 0, pathList.length-1)) {
				for(ResourceManager rm: managers.get(p.split(":")[0]).get(p.split(":")[1])) {
					rm.applyModifiers(modifiers);
				}
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
		
		List<Resource> l = new ArrayList<>();
		
		for(Map<String, List<ResourceManager>> ids: managers.values()) {
			for(List<ResourceManager> list: ids.values()) {
				for(ResourceManager j: list) {
					for(Resource r: j.getResources()) {
						
						r = r.colapse();
						
						int n = l.indexOf(r);
						
						if(n == -1) {
							l.add(r);
						} else {
							r.combine(l.set(n, r));
						}
						
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
					
					int n = resourceList.indexOf(newRes);
					
					if(n == -1) {
						resourceList.add(newRes);
					} else {
						newRes.combine(resourceList.set(n, newRes));
					}
					
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
