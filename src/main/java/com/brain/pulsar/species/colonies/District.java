package com.brain.pulsar.species.colonies;

import java.util.ArrayList;
import java.util.List;

import com.brain.pulsar.other.Scope;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.elements.Operations;
import com.brain.pulsar.xml.elements.Resource;
import com.brain.pulsar.xml.types.DistrictType;

public class District implements Transactional, Constructible {
	
	private int amount;
	private int queuedAmount;
	
	private DistrictType type;

	public static District create(DistrictType districtType, Body body) {
		
		if(districtType.isPotential(new Scope(body.getType()))) {
			return new District(districtType);
		}
		
		return null;
	}

	private District(DistrictType type) {
		this.type = type;
	}

	public boolean isType(DistrictType t) {
		
		return type.equals(t);
	}

	@Override
	public List<Resource> getIncome() {
		
		List<Resource> income = new ArrayList<>();
		
		Operations ops = type.getOperations();
		
		income.addAll(ops.getUpkeep());
		income.addAll(ops.getProduction());
		
		return income;
	}

	public int getQueuedAmount() {
		
		return queuedAmount;
	}

	public int getAmount() {
		
		return amount;
	}

	@Override
	public List<Resource> getBuildCost() {
		
		Operations ops = type.getOperations();
		
		return new ArrayList<>(ops.getCost());
	}

	@Override
	public void queueBuild() {
		
		queuedAmount++;
		
	}

	@Override
	public void finnishBuild() {
		
		if(queuedAmount > 0) {
			amount++;
			queuedAmount--;
		}
		
	}
	
	
	
}
