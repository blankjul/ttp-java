package com.msu.thief.model.packing;

import com.msu.thief.variable.TravellingThiefProblem;

public interface IPackingPlanFactory {
	
	public PackingList<?> create(TravellingThiefProblem p);

}
