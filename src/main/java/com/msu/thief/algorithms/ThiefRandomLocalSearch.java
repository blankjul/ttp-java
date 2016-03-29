package com.msu.thief.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.ASingleObjectiveAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.MyRandom;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;

public class ThiefRandomLocalSearch extends ASingleObjectiveAlgorithm<TTPVariable, SingleObjectiveThiefProblem> {

	// when is the starting point reseted
	protected int numOfNoImprovements = 1000;

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {

		int counter = 0;
		Solution<TTPVariable> current = ThiefOnePlusOneEA.calcStartingSolution(problem, evaluator, rand);
		Solution<TTPVariable> best = current;

		while (evaluator.hasNext()) {

			// reset the starting point when no improvement for N iterations
			if (counter > numOfNoImprovements) {

				// save current if this run found a new best
				if (SolutionDominator.isDominating(current, best))
					best = current;

				// reset everything
				current = ThiefOnePlusOneEA.calcStartingSolution(problem, evaluator, rand);
				counter = 0;
			}

			// create next variable by doing one bitflip
			Pack nextPack = current.getVariable().getPack().copy();

			int idx = rand.nextInt(problem.numOfItems());
			if (nextPack.isPicked(idx))
				nextPack.remove(idx);
			else
				nextPack.add(idx);

			Solution<TTPVariable> next = evaluator.evaluate(problem, TTPVariable.create(current.getVariable().getTour(), nextPack));

			// if hill climbing was successfully reset counter to 0
			if (SolutionDominator.isDominating(next, current)) {
				current = next;
				counter = 0;
			}
			// else increase non improved counter
			else
				counter++;

		}

		return best;
	}

}
