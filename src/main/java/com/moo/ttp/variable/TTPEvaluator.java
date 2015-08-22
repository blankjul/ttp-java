package com.moo.ttp.variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.model.Evaluator;

public class TTPEvaluator extends Evaluator<TTPVariable, TravellingThiefProblem> {

	public TTPEvaluator(TravellingThiefProblem problem) {
		super(problem);
	}

	@Override
	protected <T> List<Double> evaluate(TTPVariable variable) {
		
		List<Integer> tour = variable.get().first.encode();
		List<Boolean> packing = variable.get().second.encode();
		
		problem.getTimeCalculator().run(problem, tour, packing);
		
		if (problem.getTimeCalculator().getWeight() > problem.settings.getMaxWeight()) {
			return new ArrayList<Double>(Arrays.asList(problem.getTimeCalculator().getTime(), 0d));
		}
		
		double profit = problem.getProfitCalculator().run(problem.settings.getItems().getItems(), problem.getTimeCalculator().getItemTimes());
		
		if (profit < 0) throw new RuntimeException("Profit has to be larger than 0! But it is " + profit);

		return new ArrayList<Double>(Arrays.asList(problem.getTimeCalculator().getTime(), - profit));
		
	}

}
