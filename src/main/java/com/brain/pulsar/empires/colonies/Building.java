package com.brain.pulsar.empires.colonies;

import java.util.ArrayList;
import java.util.List;

import com.brain.ion.other.Utils;
import com.brain.pulsar.conditions.Scope;
import com.brain.pulsar.empires.colonies.jobs.Job;
import com.brain.pulsar.empires.colonies.jobs.JobManager;
import com.brain.pulsar.empires.colonies.types.BuildingType;
import com.brain.pulsar.resources.BuildTime;
import com.brain.pulsar.resources.Modifier;
import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.resources.ResourceManager;
import com.brain.pulsar.universe.Body;

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
		
		resourceList.addAll(type.getUpkeep());
		resourceList.addAll(type.getProduction());
		jobList.addAll(type.getSupply());
		
	}

	public void setBuildingType(BuildingType buildingType) {
			
		type = buildingType;
		buildTime = null;
		
		resourceList.addAll(type.getUpkeep());
		resourceList.addAll(type.getProduction());
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

	public BuildingType getPendingType() {
		
		return pendingType;
	}

	public BuildingType getBuildingType() {
		
		return type;
	}

	@Override
	public String getType() {
		
		return "building";
	}

	@Override
	public String getName() {
		
		return (type == null)? "null": type.getName();
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
		
		for(Resource r: resourceList) {
			
			StringBuilder bld = new StringBuilder(chain);
			
			if(!chain.isEmpty()) {
				bld.append(".");
			}
			bld.append(r.getType());
			String newChain = bld.toString();
			
			boolean newMatch = modifierMatcher(newChain, modifier.getParent(), match);
			
			if(newMatch) {
				r.applyModifier(modifier);
			}
			
		}
			
		StringBuilder bld = new StringBuilder(chain);
		
		if(!chain.isEmpty()) {
			bld.append(".");
		}
		bld.append("job");
		String newChain = bld.toString();
		
		boolean newMatch = modifierMatcher(newChain, modifier.getParent(), match);
		
		for(Job j: jobList) {
			j.applyModifiers(newChain, newMatch, modifier);
		}
		
	}

	@Override
	public int getBuildTime() {
		
		return (buildTime == null)? 0: buildTime.getTime();
	}

	@Override
	public void build() {
		
		setBuildingType();
		
	}

	@Override
	public final int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildTime == null) ? 0 : buildTime.hashCode());
		result = prime * result + ((jobList == null) ? 0 : jobList.hashCode());
		result = prime * result + ((pendingType == null) ? 0 : pendingType.hashCode());
		result = prime * result + ((resourceList == null) ? 0 : resourceList.hashCode());
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
		if (!(obj instanceof Building)) {
			return false;
		}
		Building other = (Building) obj;
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
		if (pendingType == null) {
			if (other.pendingType != null) {
				return false;
			}
		} else if (!pendingType.equals(other.pendingType)) {
			return false;
		}
		if (resourceList == null) {
			if (other.resourceList != null) {
				return false;
			}
		} else if (!resourceList.equals(other.resourceList)) {
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
