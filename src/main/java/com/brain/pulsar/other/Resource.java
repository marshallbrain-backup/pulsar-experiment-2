package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.brain.pulsar.xml.elements.Modifier;
import com.brain.pulsar.xml.elements.ResourceBase;

public class Resource {
	
	private final double amount;
	
	private final String id;
	private final String type;
	
	private Map<Modifier, Double> modifierList;

	public Resource(ResourceBase r, String t) {
		this(r, t, false);
	}

	public Resource(ResourceBase r, String t, boolean upkeep) {
		
		id = r.getId();
		type = t;
		amount = r.getAmount() * ((upkeep)? -1: 1);
		
		modifierList = new HashMap<>();
	}

	public Resource(Resource r, double d) {
		
		id = r.id;
		type = "";
		amount = d;
		
	}

	public String getId() {
		
		return id;
	}

	public String getType() {
		
		return type;
	}

	public double getAmount() {
		
		return amount;
	}

	public Resource combine(Resource res) {
		
		if(id.equals(res.id)) {
			return new Resource(this, amount + res.amount);
		}
		
		return null;
	}

	public static void addToList(List<Resource> master, int i, Resource[] resources) {
		
		for(Resource r: resources) {
			
			int index = master.indexOf(r);
			
			if(index == -1) {
				master.add(r);
			} else {
				master.set(index, r.combine(master.get(i)));
			}
			
		}
	}
	
	public void applyModifier(Modifier mod) {
		
		Pattern p = Pattern.compile(mod.getTarget());
		Matcher m = p.matcher(id);
		boolean f = m.find();

		if(f) {
			modifierList.putIfAbsent(mod, amount * mod.getValue());			
		}
	}

	public Resource zip() {
		
		double mod = 0;
		for(double d: modifierList.values()) {
			mod += d;
		}
		
		return new Resource(this, amount + mod);
		
	}
	
}
