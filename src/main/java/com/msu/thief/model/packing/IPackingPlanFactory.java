package com.msu.thief.model.packing;

import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.thief.problems.TravellingThiefProblem;

public interface IPackingPlanFactory extends VariableFactory<PackingList<?>, TravellingThiefProblem>{
	
	public PackingList<?> create(TravellingThiefProblem p);

}
