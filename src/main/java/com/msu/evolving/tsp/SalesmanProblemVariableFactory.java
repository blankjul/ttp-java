package com.msu.evolving.tsp;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariableFactory;
import com.msu.moo.util.Random;
import com.msu.problems.factory.RandomSalesmanProblemFactory;
import com.msu.thief.model.CoordinateMap;

public class SalesmanProblemVariableFactory extends AVariableFactory {

	protected int numOfCities;

	public SalesmanProblemVariableFactory(int numOfCities) {
		super();
		this.numOfCities = numOfCities;
	}

	@Override
	public IVariable next(IProblem problem, Random rand) {
		CoordinateMap map = (CoordinateMap) new RandomSalesmanProblemFactory().create(numOfCities, rand).getMap();
		return new SalesmanProblemVariable(map.getCities());
	}

}
