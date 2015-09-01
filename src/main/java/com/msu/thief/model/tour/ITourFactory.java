package com.msu.thief.model.tour;

import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.thief.problems.ICityProblem;

public interface ITourFactory<P extends ICityProblem> extends VariableFactory<Tour<?>, P>{
	
	public Tour<?> create(P p);

}
