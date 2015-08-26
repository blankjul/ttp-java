package com.msu.thief.model.packing;

import com.msu.thief.problems.TravellingThiefProblem;

public interface IPackingPlanFactory {
	
	public PackingList<?> create(TravellingThiefProblem p);

}
