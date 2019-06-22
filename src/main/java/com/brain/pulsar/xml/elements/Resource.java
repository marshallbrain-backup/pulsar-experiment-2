package com.brain.pulsar.xml.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Resource {
	
	@XmlValue
	private double amount;
	
	@XmlAttribute
	private final String id;
	
	public Resource() {
		id = null;
	}

	public Resource(String id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	public void combine(Resource r) {
		
		if(equals(r)) {
			
			amount += r.amount;
			
		}
		
	}

	public void makeNegitive() {
		
		amount *= -1;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof Resource)) {
			return false;
		}
		Resource other = (Resource) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}
