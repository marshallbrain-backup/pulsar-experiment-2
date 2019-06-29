package com.brain.pulsar.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Resource {
	
	private final double amount;
	
	private final String id;
	private final String type;
	
	private Map<Modifier, Double> modifierList;

	public Resource(ResourceBase r, String t) {
		this(r, t, false);
	}

	public Resource(ResourceBase r, String t, boolean upkeep) {
		
		id = r.getId();
		type = t;
		amount = r.getAmount() * ((upkeep)? -1: 1);
		
		modifierList = new HashMap<>();
	}

	public Resource(Resource r, double d) {
		
		id = r.id;
		type = "";
		amount = d;
		
	}

	public String getId() {
		
		return id;
	}

	public String getType() {
		
		return type;
	}

	public double getAmount() {
		
		return amount;
	}

	public Resource combine(Resource res) {
		
		if(id.equals(res.id)) {
			return new Resource(this, amount + res.amount);
		}
		
		return null;
	}
	
	public void applyModifier(Modifier mod) {
		
		if(modifierList != null) {
		
			Pattern p = Pattern.compile(mod.getTarget());
			Matcher m = p.matcher(id);
			boolean f = m.find();
	
			if(f) {
				modifierList.putIfAbsent(mod, amount * mod.getValue());			
			}
			
		}
		
	}

	public Resource zip() {
		
		double mod = 0;
		
		if(modifierList != null) {
			for(double d: modifierList.values()) {
				mod += d;
			}
		}
		
		return new Resource(this, amount + mod);
		
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modifierList == null) ? 0 : modifierList.hashCode());
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
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (modifierList == null) {
			if (other.modifierList != null) {
				return false;
			}
		} else if (!modifierList.equals(other.modifierList)) {
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
		
		return "Resource [amount=" + amount + ", id=" + id + ", modifierList=" + modifierList + ", type=" + type + "]";
	}
	
}
