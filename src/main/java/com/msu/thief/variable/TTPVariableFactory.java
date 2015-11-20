package com.msu.thief.variable;

import com.msu.interfaces.IProblem;
import com.msu.model.AVariableFactory;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.APackingPlanFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.ATourFactory;
import com.msu.util.MyRandom;

public class TTPVariableFactory extends AVariableFactory {

	protected ATourFactory facTour;
	protected APackingPlanFactory facPlan;

	public TTPVariableFactory(ATourFactory facTour, APackingPlanFactory	 facPlan) {
		super();
		this.facTour = facTour;
		this.facPlan = facPlan;
	}

	@Override
	public TTPVariable next(IProblem problem, MyRandom rand) {
		Tour<?> tour = (Tour<?>) facTour.next(problem, rand);
		PackingList<?> list = (PackingList<?>) facPlan.next(problem, rand);
		return new TTPVariable(tour,list);
	}

}
