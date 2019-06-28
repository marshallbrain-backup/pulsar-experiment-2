package com.brain.pulsar.xml.adapters;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.brain.pulsar.empires.colonies.jobs.JobBase;

public class JobAdapter extends XmlAdapter<JobBase, JobBase> {
	
	private List<JobBase> jobList;
	
	public JobAdapter() {
		
		jobList = new ArrayList<>();
	}
	
	public List<JobBase> getJobList() {
		
		return new ArrayList<>(jobList);
	}
	
	@Override
	public JobBase unmarshal(JobBase v) throws Exception {
		
		jobList.add(v);
		return v;
		
	}
	
	@Override
	public JobBase marshal(JobBase v) throws Exception {
		
		return v;
		
	}
}
