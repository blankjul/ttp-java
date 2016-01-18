package com.msu.thief.algorithms.impl.local;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.tour.FixedTourEvolutionOnRelevantItems;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class LocalSwapTourAlgorithm extends AThiefSingleObjectiveAlgorithm {

	// ! starting solution for local optimization
	protected Solution<TTPVariable> best;

	
	public LocalSwapTourAlgorithm(Solution<TTPVariable> start) {
		super();
		this.best = start;
	}

	
	
	
	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {


		Tour currentTour = best.getVariable().getTour();
		Pack currentPack = best.getVariable().getPack();
		

		boolean improvement = true;

		while (improvement) {

			improvement = false;

			outer: for (int i = thief.numOfCities() - 1; i > 0; i--) {

				for (int k = i - 1; k > 0; k--) {

					
					Tour next = currentTour.copy();
					TourSwapMutation.swap(next, i, k);

					Solution<Pack> opt = new FixedTourEvolutionOnRelevantItems()
							.run(new ThiefProblemWithFixedTour(thief, next), evaluator, rand);

					if (new SolutionDominatorWithConstraints().isDominating(opt, best)) {
						currentTour = next;
						currentPack = opt.getVariable();
						best = new Solution<TTPVariable>(TTPVariable.create(next, currentPack), best.getObjectives(),
								best.getConstraintViolations());
						// System.out.println(String.format("%s %s %s",
						// best.getObjectives(), i, k));
						improvement = true;
						break outer;
					}

				}
			}

		}

		return best;
	}

}
