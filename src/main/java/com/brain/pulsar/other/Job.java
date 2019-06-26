package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.brain.ion.other.Utils;
import com.brain.pulsar.xml.elements.JobBase;
import com.brain.pulsar.xml.elements.JobType;
import com.brain.pulsar.xml.elements.Modifier;

public class Job implements ResourceManager {
	
	private String id;
	private String type;
	
	private JobType jobType;
	
	private List<Resource> resourceList;

	public Job(JobType t) {
		
		jobType = t;
		id = jobType.getId();
		type = jobType.getType();
		
		resourceList = new ArrayList<>();
		
		Resource.addToList(resourceList, 1, Utils.concatenateArray(jobType.getUpkeep(), jobType.getProduction()));
		
	}

	@Override
	public String getName() {
		
		return id;
	}

	@Override
	public String getType() {
		
		return type;
	}

	@Override
	public List<Resource> getResources() {
		
		return resourceList;
	}

	@Override
	public void applyModifiers(String chain, boolean match, Modifier modifier) {
		
		if(!modifier.getParent().contains("job")) {
			return;
		}
		
		for(Resource res: resourceList) {
			
			StringBuilder bld = new StringBuilder(chain);
			
			if(!chain.isEmpty()) {
				bld.append(".");
			}
			bld.append(res.getType());
			String newChain = bld.toString();
			
			boolean newMatch = modifierMatcher(newChain, modifier.getParent(), match);
			
			if(newMatch) {
				res.applyModifier(modifier);
			}
			
		}
		
	}
	
}
