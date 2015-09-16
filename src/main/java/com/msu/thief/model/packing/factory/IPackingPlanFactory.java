package com.msu.thief.model.packing.factory;

import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.PackingList;

public interface IPackingPlanFactory extends VariableFactory<PackingList<?>, TravellingThiefProblem>{
	
	public PackingList<?> create(TravellingThiefProblem p);

}
