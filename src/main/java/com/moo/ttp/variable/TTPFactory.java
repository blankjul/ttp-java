package com.moo.ttp.variable;

import com.moo.ttp.model.packing.PackingList;
import com.moo.ttp.model.tour.Tour;
import com.moo.ttp.util.Pair;
import com.msu.moo.model.interfaces.IFactory;

public class TTPFactory implements IFactory<TTPVariable> {

	protected IFactory<Tour<?>> facTour;
	protected IFactory<PackingList<?>> facPlan;
	
	
	
	public TTPFactory(IFactory<Tour<?>> facTour, IFactory<PackingList<?>> facPlan) {
		super();
		this.facTour = facTour;
		this.facPlan = facPlan;
	}


	@Override
	public TTPVariable create() {
		return new TTPVariable(new Pair<Tour<?>, PackingList<?>>(facTour.create(), facPlan.create()));
	}

}
