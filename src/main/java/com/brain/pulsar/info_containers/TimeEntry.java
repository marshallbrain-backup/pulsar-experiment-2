package com.brain.pulsar.info_containers;

public class TimeEntry {
	
	private final int time;
	
	public TimeEntry(int time) {
		
		if(time < 0) {
			time = 0;
		}
		
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}

	public Entry<TimeEntry, Integer> remove(int buildTime) {
		
		return new Entry<>(new TimeEntry(time-buildTime), buildTime-time);
	}
	
}
