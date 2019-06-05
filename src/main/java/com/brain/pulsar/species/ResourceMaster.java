package com.brain.pulsar.species;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brain.pulsar.xml.elements.Resource;

public class ResourceMaster {
	
	private Map<Resource, Resource> bank;
	
	public ResourceMaster() {
		bank = new HashMap<>();
	}
	
	public boolean transaction(ResourceBucket bucket) {
		
		List<Resource> amounts = bucket.getRefinedTotal();
		
		for(Resource r: amounts) {
			
			Resource total = bank.getOrDefault(r, new Resource(0, r.getType()));
			Resource newTotal = total.append(r);
			if(newTotal.getAmount() < 0) {
				return false;
			}
			
		}
		
		for(Resource r: amounts) {
			mergeResource(bank, r);
		}
		
		return true;
		
	}
	
	private static void mergeResource(Map<Resource, Resource> map, Resource r) {
		
		if(map.containsKey(r)) {
			Resource base = map.remove(r);
			base = base.append(r);
			if(base.getAmount() != 0) {
				map.put(base, base);
			}
		} else {
			map.put(r, r);
		}
		
	}
	
}
