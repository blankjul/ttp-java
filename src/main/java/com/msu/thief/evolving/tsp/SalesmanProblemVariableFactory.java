package com.msu.thief.evolving.tsp;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.AVariableFactory;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.problems.factory.RandomSalesmanProblemFactory;
import com.msu.util.Random;

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
