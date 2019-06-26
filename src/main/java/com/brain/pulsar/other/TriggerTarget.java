package com.brain.pulsar.other;


public enum TriggerTarget {
	
	HAS_TYPE_TAG(ScopeType.BODY_TYPE);
	
	private ScopeType scopeType;
	
	private TriggerTarget(ScopeType t) {
		scopeType = t;
	}
	
	public ScopeType getScopeType() {
		return scopeType;
	}
	
}
