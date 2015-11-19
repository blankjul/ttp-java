package com.msu.thief.evolving;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.AVariableFactory;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.problems.factory.AKnapsackProblemFactory;
import com.msu.thief.problems.factory.ASalesmanProblemFactory;
import com.msu.thief.problems.factory.ClusteredSalesmanProblemFactory;
import com.msu.thief.problems.factory.RandomKnapsackProblemFactory;
import com.msu.thief.problems.factory.RandomThiefProblemFactory;
import com.msu.thief.problems.factory.RandomKnapsackProblemFactory.CORRELATION_TYPE;
import com.msu.util.Random;

public class ThiefProblemVariableFactory extends AVariableFactory {

	protected int numOfCities;
	
	

	public ThiefProblemVariableFactory(int numOfCities) {
		super();
		this.numOfCities = numOfCities;
	}

	@Override
	public IVariable next(IProblem problem, Random rand) {
		
		ASalesmanProblemFactory facSalesman = new ClusteredSalesmanProblemFactory(5);
		//ASalesmanProblemFactory facSalesman = new ClusteredSalesmanProblemFactory(6);
		AKnapsackProblemFactory facKnp = new RandomKnapsackProblemFactory().setCorrType(CORRELATION_TYPE.WEAKLY_CORRELATED);
		RandomThiefProblemFactory facThief = new RandomThiefProblemFactory(facSalesman, facKnp);
		ThiefProblem result = facThief.create(numOfCities, 1, rand.nextDouble(), rand);
		
		/*
		ThiefProblem cluster = new JsonThiefProblemReader().read("../ttp-benchmark/max.ttp");
		if (cluster.getMap().getSize() != numOfCities) throw new RuntimeException("Wrong city size of file.");
		result.setMap(cluster.getMap());
		*/
		
		
		result.setStartingCityIsZero(true);
		result.setProfitEvaluator(new NoDroppingEvaluator());
		return new ThiefProblemVariable(result);
	}

}
