package com.brain.pulsar.empires.colonies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brain.ion.other.Utils;
import com.brain.pulsar.conditions.Scope;
import com.brain.pulsar.empires.colonies.jobs.Job;
import com.brain.pulsar.empires.colonies.jobs.JobManager;
import com.brain.pulsar.empires.colonies.types.DistrictType;
import com.brain.pulsar.resources.BuildTime;
import com.brain.pulsar.resources.Modifier;
import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.resources.ResourceManager;
import com.brain.pulsar.universe.Body;

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

	public DistrictType getDistrictType() {
		
		return type;
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
		
		if(retoolingType != null) {
			setDistrictType();
		} else {
			
			amount++;
			
			resourceList.clear();
			jobList.clear();
			
			resourceList.addAll(type.getUpkeep());
			resourceList.addAll(type.getProduction());
			for(int i = 0; i < amount; i++) {
				jobList.addAll(type.getSupply());
			}
			
		}
		
	}

	@Override
	public final int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((buildTime == null) ? 0 : buildTime.hashCode());
		result = prime * result + ((jobList == null) ? 0 : jobList.hashCode());
		result = prime * result + ((resourceList == null) ? 0 : resourceList.hashCode());
		result = prime * result + ((retoolingType == null) ? 0 : retoolingType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof District)) {
			return false;
		}
		District other = (District) obj;
		if (amount != other.amount) {
			return false;
		}
		if (buildTime == null) {
			if (other.buildTime != null) {
				return false;
			}
		} else if (!buildTime.equals(other.buildTime)) {
			return false;
		}
		if (jobList == null) {
			if (other.jobList != null) {
				return false;
			}
		} else if (!jobList.equals(other.jobList)) {
			return false;
		}
		if (resourceList == null) {
			if (other.resourceList != null) {
				return false;
			}
		} else if (!resourceList.equals(other.resourceList)) {
			return false;
		}
		if (retoolingType == null) {
			if (other.retoolingType != null) {
				return false;
			}
		} else if (!retoolingType.equals(other.retoolingType)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}
	
}
