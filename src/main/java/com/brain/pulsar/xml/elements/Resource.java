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

	public Resource append(Resource res) {
		return append(res, 1);
	}

	public Resource append(Resource res, int i) {
		
		if(!equals(res)) {
			return null;
		}
		
		double newAmount = amount + res.amount;
		String newType = type;
		
		return new Resource(newAmount, newType);
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
