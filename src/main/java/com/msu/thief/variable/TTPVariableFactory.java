package com.msu.thief.variable;

import com.msu.knp.model.PackingList;
import com.msu.knp.model.factory.APackingPlanFactory;
import com.msu.moo.model.AVariableFactory;
import com.msu.moo.util.Pair;
import com.msu.thief.ThiefProblem;
import com.msu.tsp.model.Tour;
import com.msu.tsp.model.factory.ATourFactory;

public class TTPVariableFactory extends AVariableFactory<TTPVariable, ThiefProblem> {

	protected ATourFactory<ThiefProblem> facTour;
	protected APackingPlanFactory facPlan;

	public TTPVariableFactory(ATourFactory<ThiefProblem> facTour, APackingPlanFactory	 facPlan) {
		super();
		this.facTour = facTour;
		this.facPlan = facPlan;
	}

	@Override
	public TTPVariable next(ThiefProblem problem) {
		return new TTPVariable(new Pair<Tour<?>, PackingList<?>>(facTour.next(problem), facPlan.next(problem)));
	}

}
