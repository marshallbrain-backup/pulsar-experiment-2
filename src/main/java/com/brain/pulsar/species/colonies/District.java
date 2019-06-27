package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brain.ion.other.Utils;
import com.brain.pulsar.other.Job;
import com.brain.pulsar.other.JobCollection;
import com.brain.pulsar.other.JobManager;
import com.brain.pulsar.other.Resource;
import com.brain.pulsar.other.ResourceManager;
import com.brain.pulsar.other.Scope;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.elements.BuildTime;
import com.brain.pulsar.xml.elements.Modifier;
import com.brain.pulsar.xml.types.DistrictType;

public class District implements ResourceManager, JobManager, Constructible {
	
	private int amount;
	
	private BuildTime buildTime;
	
	private DistrictType type;
	private DistrictType retoolingType;
	
	private List<Resource> resourceList;
	private List<Job> jobList;

	public District() {
		
		resourceList = new ArrayList<>();
		jobList = new ArrayList<>();
	}

	public void setDistrictType() {
		
		type = retoolingType;
		retoolingType = null;
		buildTime = type.getBuildTime();
		
	}

	public void setDistrictType(DistrictType districtType) {
		
		type = districtType;
		retoolingType = null;
		buildTime = type.getBuildTime();
		
	}

	public boolean setRetoolingType(DistrictType districtType, Body body) {
		
		if(districtType.isPotential(new Scope(body.getType()))) {
			retoolingType = districtType;
			buildTime = retoolingType.getBuildTime();
			return true;
		}
		
		return false;
		
	}

	public int getAmount() {
		return amount;
	}

	public boolean isAssined() {
		return type != null;
	}

	@Override
	public String getType() {
		
		return "district";
	}

	@Override
	public String getName() {
		
		return type.getName();
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
		
		if(retoolingType != null) {
			setDistrictType();
		} else {
			
			amount++;
			
			resourceList.clear();
			jobList.clear();
			
			Resource.addToList(resourceList, amount, Utils.concatenateArray(type.getUpkeep(), type.getProduction()));
			jobList.addAll(type.getSupply());
			
		}
		
	}
	
}
