package com.msu.evolving;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariableFactory;
import com.msu.moo.util.Random;
import com.msu.problems.ThiefProblem;
import com.msu.problems.factory.AKnapsackProblemFactory;
import com.msu.problems.factory.ASalesmanProblemFactory;
import com.msu.problems.factory.ClusteredSalesmanProblemFactory;
import com.msu.problems.factory.RandomKnapsackProblemFactory;
import com.msu.problems.factory.RandomKnapsackProblemFactory.CORRELATION_TYPE;
import com.msu.problems.factory.RandomThiefProblemFactory;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;

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
