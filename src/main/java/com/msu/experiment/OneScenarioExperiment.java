package com.msu.experiment;

import java.util.List;

import com.msu.AlgorithmFactory;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.scenarios.knp.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.scenarios.thief.RandomTTPScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.visualize.ThiefVisualizer;

public class OneScenarioExperiment extends AExperiment {

	
	@Override
	public void finalize() {
		new ThiefVisualizer<ThiefProblem>().show(this);
	}
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(AlgorithmFactory.createNSGAII());
		//algorithms.add(new ExhaustiveThief());
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		ThiefProblem problem = new RandomTTPScenario(7, 2, 0.5, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject();
		problem.setProfitEvaluator(new ExponentialProfitEvaluator(0.95, 10));
		problems.add(problem);
	}

}