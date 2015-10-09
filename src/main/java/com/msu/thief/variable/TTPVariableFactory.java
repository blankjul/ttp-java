package com.msu.thief.variable;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AVariableFactory;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.APackingPlanFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.ATourFactory;

public class TTPVariableFactory extends AVariableFactory {

	protected ATourFactory facTour;
	protected APackingPlanFactory facPlan;

	public TTPVariableFactory(ATourFactory facTour, APackingPlanFactory	 facPlan) {
		super();
		this.facTour = facTour;
		this.facPlan = facPlan;
	}

	@Override
	public TTPVariable next(IProblem problem) {
		Tour<?> tour = (Tour<?>) facTour.next(problem);
		PackingList<?> list = (PackingList<?>) facPlan.next(problem);
		return new TTPVariable(tour,list);
	}

}
