package com.msu.thief.evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.knp.model.PackingList;
import com.msu.moo.util.Pair;
import com.msu.moo.util.exceptions.EvaluationException;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.ProfitEvaluator;
import com.msu.thief.evaluator.time.TimeEvaluator;
import com.msu.tsp.model.Tour;

public class Evaluator implements IEvaluator<Pair<Tour<?>,PackingList<?>>, List<Double>>{
	
	// the problem instance
	protected ThiefProblem problem = null;

	// evaluate the profit according ProfitEvaluator
	protected ProfitEvaluator evalProfit = null;

	// evaluate the time according TimeEvaluator
	protected TimeEvaluator evalTime = null;

	
	public Evaluator(ThiefProblem problem, ProfitEvaluator evalProfit, TimeEvaluator evalTime) {
		super();
		this.problem = problem;
		this.evalProfit = evalProfit;
		this.evalTime = evalTime;
	}


	@Override
	public List<Double> evaluate(Pair<Tour<?>, PackingList<?>> input) {
		
		// evaluate the time
		Double time = evalTime.evaluate(input);
		
		// check if the maximal weight constraint is violated
		if (evalTime.getWeight() > problem.getMaxWeight()) {
			return new ArrayList<Double>(Arrays.asList(evalTime.getTime(), 0d));
		}
		Double profit = evalProfit.evaluate(evalTime.getItemMap());
		if (profit < 0) throw new EvaluationException("Profit has to be larger than 0! But it is " + profit);

		return new ArrayList<Double>(Arrays.asList(time, -profit));
	}
	
	
	

}
