package com.msu.thief.variable;

import com.msu.knp.model.PackingList;
import com.msu.knp.model.factory.APackingPlanFactory;
import com.msu.moo.model.AVariableFactory;
import com.msu.moo.util.Pair;
import com.msu.thief.TravellingThiefProblem;
import com.msu.tsp.model.Tour;
import com.msu.tsp.model.factory.ATourFactory;

public class TTPVariableFactory extends AVariableFactory<TTPVariable, TravellingThiefProblem> {

	protected ATourFactory<TravellingThiefProblem> facTour;
	protected APackingPlanFactory facPlan;

	public TTPVariableFactory(ATourFactory<TravellingThiefProblem> facTour, APackingPlanFactory	 facPlan) {
		super();
		this.facTour = facTour;
		this.facPlan = facPlan;
	}

	@Override
	public TTPVariable next(TravellingThiefProblem problem) {
		return new TTPVariable(new Pair<Tour<?>, PackingList<?>>(facTour.next(problem), facPlan.next(problem)));
	}

}
