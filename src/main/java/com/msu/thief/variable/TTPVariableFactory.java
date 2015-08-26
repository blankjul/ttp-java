package com.msu.thief.variable;

import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.moo.util.Pair;
import com.msu.thief.model.packing.IPackingPlanFactory;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.ITourFactory;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TravellingThiefProblem;

public class TTPVariableFactory implements VariableFactory<TTPVariable, TravellingThiefProblem> {

	protected ITourFactory facTour;
	protected IPackingPlanFactory facPlan;

	public TTPVariableFactory(ITourFactory facTour, IPackingPlanFactory	 facPlan) {
		super();
		this.facTour = facTour;
		this.facPlan = facPlan;
	}

	@Override
	public TTPVariable create(TravellingThiefProblem problem) {
		return new TTPVariable(new Pair<Tour<?>, PackingList<?>>(facTour.create(problem), facPlan.create(problem)));
	}

}
