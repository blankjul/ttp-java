package com.msu.evolving.tsp;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariableFactory;
import com.msu.problems.factory.RandomSalesmanProblemFactory;
import com.msu.thief.model.CoordinateMap;

public class SalesmanProblemVariableFactory extends AVariableFactory {

	protected int numOfCities;

	public SalesmanProblemVariableFactory(int numOfCities) {
		super();
		this.numOfCities = numOfCities;
	}

	@Override
	public IVariable next(IProblem problem) {
		CoordinateMap map = (CoordinateMap) new RandomSalesmanProblemFactory().create(numOfCities).getMap();
		return new SalesmanProblemVariable(map.getCities());
	}

}
