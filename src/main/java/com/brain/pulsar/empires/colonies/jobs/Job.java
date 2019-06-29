package com.brain.pulsar.empires.colonies.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.brain.ion.other.Utils;
import com.brain.pulsar.empires.colonies.types.JobType;
import com.brain.pulsar.resources.Modifier;
import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.resources.ResourceManager;
import com.brain.pulsar.species.Pop;

public class Job implements ResourceManager {
	
	private String id;
	private String type;
	
	private Pop worker;
	private JobType jobType;
	
	private List<Resource> resourceList;

	public Job(JobType t) {
		
		jobType = t;
		id = jobType.getId();
		type = jobType.getType();
		
		resourceList = new ArrayList<>();
		
		resourceList.addAll(jobType.getUpkeep());
		resourceList.addAll(jobType.getProduction());
		
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

	public boolean hasPop() {
		
		return worker != null;
	}

	public void setPop(Pop pop) {
		worker = pop;
		worker.setJob(this);
	}
	
}
