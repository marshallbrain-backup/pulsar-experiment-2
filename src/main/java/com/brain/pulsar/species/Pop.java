package com.brain.pulsar.species;

public class Pop {
	
	private boolean isEmployed;

	public boolean isUnimployed() {
		return !isEmployed;
	}
	
	public void setEmployment(boolean statis) {
		isEmployed = statis;
	}

	@Override
	public final int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + (isEmployed ? 1231 : 1237);
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
		if (!(obj instanceof Pop)) {
			return false;
		}
		Pop other = (Pop) obj;
		if (isEmployed != other.isEmployed) {
			return false;
		}
		return true;
	}
	
}
