package com.msu.meta;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariableFactory;
import com.msu.moo.util.Random;
import com.msu.scenarios.thief.RandomTTPScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;

public class FactoryThiefVariableFactory extends AVariableFactory {

	protected int numOfCities;

	public FactoryThiefVariableFactory(int numOfCities) {
		super();
		this.numOfCities = numOfCities;
	}

	@Override
	public IVariable next(IProblem problem) {
		ThiefProblem result = new RandomTTPScenario(numOfCities, 1, Random.getInstance().nextDouble()).getObject();
		result.setStartingCityIsZero(true);
		result.setProfitEvaluator(new NoDroppingEvaluator());
		return new FactoryThiefVariable(result);
	}

}
