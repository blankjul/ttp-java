package com.moo.ttp.model.packing;

import com.moo.ttp.variable.TravellingThiefProblem;

public interface IPackingPlanFactory {
	
	public PackingList<?> create(TravellingThiefProblem p);

}
