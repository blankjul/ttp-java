package com.msu.thief.experiment;

import com.msu.moo.algorithms.ExhaustiveSolver;
import com.msu.moo.experiment.MultiObjectiveExperiment;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.factory.AlgorithmFactory;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.problems.tsp.TSPExhaustiveFactory;
import com.msu.thief.problems.tsp.TravellingSalesmanProblem;

public class ReducedToTSPExperiment extends MultiObjectiveExperiment<TravellingThiefProblem> {

	@Override
	protected void setAlgorithms() {
		algorithms.add(AlgorithmFactory.createNSGAII());
	}

	@Override
	public void setTrueFront(TravellingThiefProblem problem) {
		TSPExhaustiveFactory fac = new TSPExhaustiveFactory();
		ExhaustiveSolver<Tour<?>, TravellingSalesmanProblem> exhaustive = new ExhaustiveSolver<>(fac);
		NonDominatedSolutionSet set = exhaustive.run(new TravellingSalesmanProblem(problem.getMap()));
		set.getSolutions().get(0).getObjectives().add(0.0);
		System.out.println(String.format("Optimum: %s", set.getSolutions().get(0).getObjectives()));
		trueFront = set;
	}

	@Override
	protected void setProblem() {
		com.msu.thief.model.Map small = new MapFactory(1000).create(10);
		problem = new TravellingThiefProblem(small, new ItemCollection<Item>(), 0);
		problem.setProfitEvaluator(new ExponentialProfitEvaluator());
		problem.setName("TSPReducedProblem");
	}


}
