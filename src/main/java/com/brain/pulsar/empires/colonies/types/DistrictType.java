package com.brain.pulsar.empires.colonies.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.brain.ion.xml.StringTrimAdapter;
import com.brain.pulsar.conditions.Scope;
import com.brain.pulsar.conditions.Trigger;
import com.brain.pulsar.empires.colonies.jobs.Job;
import com.brain.pulsar.empires.colonies.jobs.JobBase;
import com.brain.pulsar.empires.colonies.jobs.JobCollection;
import com.brain.pulsar.resources.BuildTime;
import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.resources.ResourceBase;
import com.brain.pulsar.resources.ResourceCollection;
import com.brain.pulsar.xml.adapters.JobAdapter;

@XmlRootElement(name = "district")
public class DistrictType {
	
	@XmlElement
	@XmlJavaTypeAdapter(value=StringTrimAdapter.class)
	private String name;
	
	@XmlElement(name = "base_buildtime")
	private BuildTime defaultBuildTime;
	
	@XmlElementWrapper
	@XmlElement(name = "trigger")
	private List<Trigger> potential;
	
	@XmlElementWrapper
	@XmlElement(name = "trigger")
	private List<Trigger> starting;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<ResourceBase> upkeep;
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<ResourceBase> production;
	
	@XmlElementWrapper
	@XmlElement(name = "job")
	@XmlJavaTypeAdapter(value=JobAdapter.class)
	private List<JobBase> supply;

	public boolean isPotential(Scope bodyType) {
		
		for(Trigger t: potential) {
			if(t.isCondition(bodyType)) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isStarting(Scope bodyType) {
		
		for(Trigger t: potential) {
			if(t.isCondition(bodyType)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public Resource[] getUpkeep() {
		
		List<Resource> u = new ArrayList<>();
		
		for(ResourceBase r: upkeep) {
			u.add(new Resource(r, "upkeep", true));
		}
		
		return u.toArray(new Resource[0]);
	}
	
	public Resource[] getProduction() {
		
		List<Resource> p = new ArrayList<>();
		
		for(ResourceBase r: production) {
			p.add(new Resource(r, "production"));
		}
		
		return p.toArray(new Resource[0]);
	}
	
	public List<Job> getSupply() {
		
		List<Job> j = new ArrayList<>();
		
		for(JobBase r: supply) {
			j.addAll(r.createJobs());
		}
		
		return j;
	}

	public BuildTime getBuildTime() {
		
		return defaultBuildTime;
	}
	
}
