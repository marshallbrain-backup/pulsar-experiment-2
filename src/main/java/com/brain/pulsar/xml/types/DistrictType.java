package com.brain.pulsar.xml.types;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "district")
public class DistrictType {
	
	@XmlElement(name = "base_buildtime")
	private int baseBuildtime;

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DistrictType other = (DistrictType) obj;
		if (baseBuildtime != other.baseBuildtime)
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "DistrictType [baseBuildtime=" + baseBuildtime + "]";
	}
	
}
