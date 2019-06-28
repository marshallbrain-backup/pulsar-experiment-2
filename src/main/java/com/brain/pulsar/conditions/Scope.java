package com.brain.pulsar.other;

import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.types.BodyType;

public class Scope {
	
	private Body body;
	private BodyType bodyType;
	
	public Scope(Body b) {
		
		setBody(b);
		setBodyType(b.getType());
	}

	public Scope(BodyType bt) {
		
		setBodyType(bt);
	}
	
	private void setBody(Body b) {
		body = b;
	}
	
	private void setBodyType(BodyType bt) {
		bodyType = bt;
	}

	public BodyType getBodyType() {
		return bodyType;
	}
	
}
