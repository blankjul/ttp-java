package com.moo.ttp.variable;

import com.moo.ttp.model.packing.IPackingPlanFactory;
import com.moo.ttp.model.packing.PackingList;
import com.moo.ttp.model.tour.ITourFactory;
import com.moo.ttp.model.tour.Tour;
import com.moo.ttp.util.Pair;
import com.msu.moo.model.interfaces.VariableFactory;

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
