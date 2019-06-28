package com.brain.pulsar.conditions;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.brain.ion.xml.StringTrimAdapter;
import com.brain.pulsar.universe.types.BodyType;

@XmlRootElement(name = "trigger")
public class Trigger {

	@XmlAttribute(name = "name")
	private String target;
	@XmlValue
	private String value;
	
	public boolean isCondition(Scope s) {
		
		TriggerTarget t = TriggerTarget.valueOf(target.toUpperCase());
		
		switch(t.getScopeType()) {
			case BODY_TYPE:
				return targetBodyType(t, s.getBodyType());
		}
		
		return false;
		
	}
	
	private boolean targetBodyType(TriggerTarget targ, BodyType type) {
		
		if(type == null) {
			return false;
		}
		
		switch(targ) {
			case HAS_TYPE_TAG:
				return type.hasTag(value);
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
		if (!(obj instanceof Trigger)) {
			return false;
		}
		Trigger other = (Trigger) obj;
		if (target == null) {
			if (other.target != null) {
				return false;
			}
		} else if (!target.equals(other.target)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
	
}
