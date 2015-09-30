package com.msu.experiment;

import java.util.List;

import com.msu.algorithms.ExhaustiveSalesman;
import com.msu.algorithms.ExhaustiveThief;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.report.SolutionSetReport;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.FinishedProblemExecution;
import com.msu.scenarios.knp.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.scenarios.thief.RandomTTPScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.visualize.ThiefVisualizer;

public class OneScenarioExperiment extends AExperiment {

	
	final public boolean SHOW_ALL = false;
	
	final public boolean STARTING_CITY_IS_ZERO = true;
	
	@Override
	public void finalize() {
		
		ThiefProblem ttp = (ThiefProblem) problems.get(0);
		TravellingSalesmanProblem p = new TravellingSalesmanProblem(ttp.getMap());
		new ExhaustiveSalesman().run(new Evaluator(p));
		
		new ThiefVisualizer<ThiefProblem>().show(this);
		
		
	}
	
	
	@Override
	protected void initialize() {
		SolutionSetReport report = new SolutionSetReport();
		report.set("experiment/scenario.csv");
		EventDispatcher.getInstance().register(FinishedProblemExecution.class, report);
	}


	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		//algorithms.add(AlgorithmFactory.createNSGAII());
		algorithms.add(new ExhaustiveThief().setOnlyNonDominatedPoints(!SHOW_ALL).setStartingCityIsZero(STARTING_CITY_IS_ZERO));
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		ThiefProblem problem = new RandomTTPScenario(6, 2, 0.5, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject();
		problem.setProfitEvaluator(new NoDroppingEvaluator());
		problem.setStartingCityIsZero(STARTING_CITY_IS_ZERO);
		problems.add(problem);
	}

}