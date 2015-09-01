package com.msu.thief.experiment.knp;

import com.msu.moo.algorithms.ExhaustiveSolver;
import com.msu.moo.experiment.AbstractExperiment;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.factory.AlgorithmFactory;
import com.msu.thief.factory.ThiefProblemFactory;
import com.msu.thief.factory.items.ItemFactory;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.problems.knp.KnapsackExhaustiveFactory;
import com.msu.thief.problems.knp.KnapsackProblem;
import com.msu.thief.problems.knp.KnapsackVariable;

public class KnpReducedExperiment extends AbstractExperiment<TravellingThiefProblem> {

	@Override
	protected void setAlgorithms() {
		algorithms.add(AlgorithmFactory.createNSGAII());
	}

	@Override
	protected void setProblem() {
		ItemFactory facItems = new ItemFactory(ItemFactory.CORRELATION_TYPE.UNCORRELATED);
		ThiefProblemFactory fac = new ThiefProblemFactory(new MapFactory(), facItems, 0.5, "KnpReducedProblem");
		fac.setDropType(ThiefProblemFactory.DROPPING_TYPE.RANDOM);
		problem = fac.create(1, 10);
	}

	@Override
	public NonDominatedSolutionSet getTrueFront(TravellingThiefProblem problem) {
		KnapsackProblem knp = new KnapsackProblem(problem.getMaxWeight(), problem.getItems().getItems());
		ExhaustiveSolver<KnapsackVariable, KnapsackProblem> solver = new ExhaustiveSolver<>(new KnapsackExhaustiveFactory());
		NonDominatedSolutionSet set = solver.run(knp);
		for (Solution s : set.getSolutions()) {
			s.getObjectives().add(0, 0.0);
			// System.out.println(String.format("Optimum: %s", s));
		}
		return set;
	}

}
