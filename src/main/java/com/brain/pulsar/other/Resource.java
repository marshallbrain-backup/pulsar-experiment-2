package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.brain.pulsar.xml.elements.ResourceBase;

public class Resource {
	
	private double amount;
	
	private String id;
	
	private List<String> idChain;
	private List<String> typeChain;
	
	public Resource() {
		
		idChain = new ArrayList<>();
		typeChain = new ArrayList<>();
	}

	public Resource(String id, double amount) {
		
		this();
		
		this.id = id;
		this.amount = amount;
	}

	public Resource(ResourceBase r) {
		
		this();
		
		id = r.getId();
		amount = r.getAmount();
	}

	public Resource(ResourceBase r, boolean b) {
		
		this();
		
		id = r.getId();
		
		if(b) {
			amount = r.getAmount() * -1;
		} else {
			amount = r.getAmount();
		}
	}
	
	private Resource(Resource r, List<String> typeChain, List<String> idChain) {
		
		amount = r.amount;
		id = r.id;
		this.typeChain = new ArrayList<>(typeChain);
		this.idChain = new ArrayList<>(idChain);
		
	}

	public void combine(Resource r) {
		
		if(equals(r)) {
			amount += r.amount;
		}
	}

	public void appendChain(String type, String name) {

		typeChain.add(type);
		idChain.add(name);
	}

	public Resource trim() {
		
		List<String> newIdChain = new ArrayList<>(idChain.subList(1, idChain.size()));
		List<String> newTypeChain = new ArrayList<>(typeChain.subList(1, typeChain.size()));
		
		return new Resource(this, newTypeChain, newIdChain);
		
	}

	public boolean isChainEmpty() {
		
		return typeChain.isEmpty() && idChain.isEmpty();
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idChain == null) ? 0 : idChain.hashCode());
		result = prime * result + ((typeChain == null) ? 0 : typeChain.hashCode());
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
		if (idChain == null) {
			if (other.idChain != null) {
				return false;
			}
		} else if (!idChain.equals(other.idChain)) {
			return false;
		}
		if (typeChain == null) {
			if (other.typeChain != null) {
				return false;
			}
		} else if (!typeChain.equals(other.typeChain)) {
			return false;
		}
		return true;
	}
	
}