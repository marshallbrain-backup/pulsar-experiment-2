package com.brain.pulsar.species;

import java.util.Collections;
import java.util.List;

import com.brain.pulsar.xml.elements.Resource;

public class ResourceBucket {
	
	private final int amount;
	
	private final String id;
	
	private final List<Resource> upkeep;
	private final List<Resource> production;
	
	public ResourceBucket(String id, List<Resource> upkeep, List<Resource> production) {
		this(id, upkeep, production, 1);
		
	}
	
	public ResourceBucket(String id, List<Resource> upkeep, List<Resource> production, int amount) {
		
		this.id = id;
		this.amount = amount;
		
		this.upkeep = Collections.unmodifiableList(upkeep);
		this.production = Collections.unmodifiableList(production);
		
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((production == null) ? 0 : production.hashCode());
		result = prime * result + ((upkeep == null) ? 0 : upkeep.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ResourceBucket)) {
			return false;
		}
		ResourceBucket other = (ResourceBucket) obj;
		if (amount != other.amount) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (production == null) {
			if (other.production != null) {
				return false;
			}
		} else if (!production.equals(other.production)) {
			return false;
		}
		if (upkeep == null) {
			if (other.upkeep != null) {
				return false;
			}
		} else if (!upkeep.equals(other.upkeep)) {
			return false;
		}
		return true;
	}
	
}
