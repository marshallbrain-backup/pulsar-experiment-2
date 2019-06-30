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
	
	private final String id;
	private final String type;
	
	private Pop worker;
	private final JobType jobType;
	
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
		worker.setEmployment(true);
	}

	@Override
	public final int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobType == null) ? 0 : jobType.hashCode());
		result = prime * result + ((resourceList == null) ? 0 : resourceList.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((worker == null) ? 0 : worker.hashCode());
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
		if (!(obj instanceof Job)) {
			return false;
		}
		Job other = (Job) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (jobType == null) {
			if (other.jobType != null) {
				return false;
			}
		} else if (!jobType.equals(other.jobType)) {
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
		if (worker == null) {
			if (other.worker != null) {
				return false;
			}
		} else if (!worker.equals(other.worker)) {
			return false;
		}
		return true;
	}
	
}
