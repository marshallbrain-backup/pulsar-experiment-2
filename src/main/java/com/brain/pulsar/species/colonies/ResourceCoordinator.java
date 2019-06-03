package com.brain.pulsar.species.colonies;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.brain.pulsar.xml.elements.Resource;

public class ResourceCoordinator {

	public static Map<Resource, Resource> calculateIncome(Transactional... transactionals) {
		
		Map<Resource, Resource> income = new HashMap<>();
		
		for(Transactional t: transactionals) {
			for(Resource r: t.getIncome()) {
				mergeResource(income, r);
			}
		}
		
		return income;
	}

	public static Map<Resource, Resource> calculateTotal(Map<Resource, Resource> total, Map<Resource, Resource> income) {
		
		Map<Resource, Resource> newTotal = new HashMap<>(total);
		
		for(Resource r: income.keySet()) {
			mergeResource(newTotal, r);
		}
		
		return newTotal;
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
