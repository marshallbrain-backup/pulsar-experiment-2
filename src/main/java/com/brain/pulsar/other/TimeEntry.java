package com.brain.pulsar.other;

public class TimeEntry {
	
	private final int time;
	
	public TimeEntry(int time) {
		
		if(time < 0) {
			time = 0;
		}
		
		this.time = time;
	}

	public Entry<TimeEntry, Integer> remove(int buildTime) {
		
		return new Entry<>(new TimeEntry(time-buildTime), buildTime-time);
	}
	
}
