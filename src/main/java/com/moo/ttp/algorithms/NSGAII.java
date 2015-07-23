package com.moo.ttp.algorithms;

import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import com.moo.ttp.jmetal.jCrossover;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jMutation;

public class NSGAII extends org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII<jISolution> {

	protected int evaluations;
	protected int maxEvaluations;

	public NSGAII(Problem<jISolution> p, int maxEvaluations, int populationSize) {
		super(p, -1, populationSize, new jCrossover(), new jMutation(), new BinaryTournamentSelection<jISolution>(),
				new SequentialSolutionListEvaluator<jISolution>());
		this.maxEvaluations = maxEvaluations;
	}

	public String toString() {
		return "NSGAII-" + maxEvaluations + "-" + populationSize;
	}

	@Override
	protected void initProgress() {
		evaluations = populationSize;
	}

	@Override
	protected void updateProgress() {
		evaluations += populationSize;
	}

	@Override
	protected boolean isStoppingConditionReached() {
		return evaluations >= maxEvaluations;
	}

}
