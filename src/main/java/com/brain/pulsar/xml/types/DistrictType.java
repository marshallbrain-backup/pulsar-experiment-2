package com.brain.pulsar.xml.types;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.brain.ion.xml.StringTrimAdapter;
import com.brain.pulsar.other.Scope;
import com.brain.pulsar.xml.elements.Operations;
import com.brain.pulsar.xml.elements.Trigger;

@XmlRootElement(name = "district")
public class DistrictType {
	
	@XmlElement(name = "base_buildtime")
	private int baseBuildtime;
	
	@XmlElementWrapper(name="potential")
	@XmlElement(name = "trigger")
	private List<Trigger> potential;
	
	@XmlElementWrapper(name="potential")
	@XmlElement(name = "trigger")
	private List<Trigger> starting;
	
	@XmlElement
	private Operations operations;

	public boolean isPotential(Scope bodyType) {
		
		for(Trigger t: potential) {
			if(t.isCondition(bodyType)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DistrictType)) {
			return false;
		}
		DistrictType other = (DistrictType) obj;
		if (baseBuildtime != other.baseBuildtime) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		
		return "DistrictType [baseBuildtime=" + baseBuildtime + "]";
	}
	
}
