package com.brain.pulsar.xml.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Resource {
	
	@XmlValue
	private final double amount;

	@XmlAttribute
	private final String type;
	
	public Resource() {
		amount = 0;
		type = null;
	}
	
	public Resource(double amount, String type) {
		
		this.amount = amount;
		this.type = type;
	}

	public static Resource append(Resource res1, Resource res2) {
		
		if(!res1.equals(res2)) {
			return null;
		}
		
		double amount = res1.amount + res2.amount;
		String type = res1.type;
		
		return new Resource(amount, type);
	}

	public String getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}
	
	public boolean equalsExact(Object obj) {
		
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
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
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

	@Override
	public String toString() {
		
		return "Resource [amount=" + amount + ", type=" + type + "]";
	}
	
}
