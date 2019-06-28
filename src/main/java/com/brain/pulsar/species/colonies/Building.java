package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.List;

import com.brain.ion.other.Utils;
import com.brain.pulsar.other.Job;
import com.brain.pulsar.other.JobManager;
import com.brain.pulsar.other.Resource;
import com.brain.pulsar.other.ResourceManager;
import com.brain.pulsar.other.Scope;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.elements.BuildTime;
import com.brain.pulsar.xml.elements.Modifier;
import com.brain.pulsar.xml.types.BuildingType;

public class Building implements ResourceManager, JobManager, Constructible {
	
	private BuildTime buildTime;
	
	private BuildingType type;
	private BuildingType pendingType;
	
	private List<Resource> resourceList;
	private List<Job> jobList;

	public Building() {
		
		resourceList = new ArrayList<>();
		jobList = new ArrayList<>();
	}

	public void setBuildingType() {
			
		type = pendingType;
		buildTime = null;
		
		Resource.addToList(resourceList, 1, Utils.concatenateArray(type.getUpkeep(), type.getProduction()));
		jobList.addAll(type.getSupply());
		
	}

	public void setBuildingType(BuildingType buildingType) {
			
		type = buildingType;
		buildTime = null;
		
		Resource.addToList(resourceList, 1, Utils.concatenateArray(type.getUpkeep(), type.getProduction()));
		jobList.addAll(type.getSupply());
		
	}
	
	public boolean setPendingType(BuildingType buildingType, Body body) {
		
		if(buildingType.isPotential(new Scope(body.getType()))) {
			
			pendingType = buildingType;
			buildTime = pendingType.getBuildTime();
			
			return true;
			
		}
		
		return false;
	}

	public boolean isAssined() {
		return type != null;
	}

	@Override
	public String getType() {
		
		return "building";
	}

	@Override
	public String getName() {
		
		return (type == null)?"null":type.getName();
	}

	@Override
	public List<Resource> getResources() {
		
		return resourceList;
	}

	@Override
	public List<Job> getJobs() {
		
		return jobList;
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

	@Override
	public int getBuildTime() {
		
		return buildTime.getTime();
	}

	@Override
	public void build() {
		
		setBuildingType();
		
	}
	
}
