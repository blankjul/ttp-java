package com.msu.thief.model.tour.factory;

import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.thief.model.tour.Tour;
import com.msu.tsp.ICityProblem;

public interface ITourFactory<P extends ICityProblem> extends VariableFactory<Tour<?>, P>{
	
	public Tour<?> create(P p);

}
