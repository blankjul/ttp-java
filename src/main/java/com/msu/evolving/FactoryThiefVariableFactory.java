package com.msu.evolving;

import com.msu.io.reader.JsonThiefProblemReader;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariableFactory;
import com.msu.moo.util.Random;
import com.msu.problems.ThiefProblem;
import com.msu.problems.factory.RandomThiefScenario;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;

public class FactoryThiefVariableFactory extends AVariableFactory {

	protected int numOfCities;

	public FactoryThiefVariableFactory(int numOfCities) {
		super();
		this.numOfCities = numOfCities;
	}

	@Override
	public IVariable next(IProblem problem) {
		ThiefProblem result = new RandomThiefScenario(numOfCities, 1, Random.getInstance().nextDouble()).getObject();
		
		//ThiefProblem cluster = new JsonThiefProblemReader().read("../ttp-benchmark/cluster.ttp");
		//result.setMap(cluster.getMap());
		
		
		result.setStartingCityIsZero(true);
		result.setProfitEvaluator(new NoDroppingEvaluator());
		return new FactoryThiefVariable(result);
	}

}
