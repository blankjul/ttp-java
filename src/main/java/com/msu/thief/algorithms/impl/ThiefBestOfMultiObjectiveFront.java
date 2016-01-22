package com.msu.thief.algorithms.impl;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.moo.ThiefMultiObjectiveAlgorithm;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class ThiefBestOfMultiObjectiveFront extends AThiefSingleObjectiveAlgorithm {

	
	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {
		
		// calculate multi objective set
		NonDominatedSolutionSet<TTPVariable> set = new ThiefMultiObjectiveAlgorithm().run(new MultiObjectiveThiefProblem(thief), evaluator, rand);
		
		// find best solution according single objective
		NonDominatedSolutionSet<TTPVariable> singleObjectiveSet = new NonDominatedSolutionSet<>();
		for (Solution<TTPVariable> solution : set) {
			Solution<TTPVariable> single = thief.evaluate(solution.getVariable());
			//System.out.println(single);
			singleObjectiveSet.add(single);
		}
		//System.out.println();
		Solution<TTPVariable> best = singleObjectiveSet.get(0);
		
		
		// optimize packing plan first
		
		/*
		Solution<Pack> opt = new FixedTourEvolutionOnRelevantItems()
				.run(new ThiefProblemWithFixedTour(thief, best.getVariable().getTour()), evaluator, rand);
		if (new SolutionDominatorWithConstraints().isDominating(opt, best)) {
			best = new Solution<TTPVariable>(TTPVariable.create(best.getVariable().getTour(), opt.getVariable()), opt.getObjectives(), opt.getConstraintViolations());
		}
		*/
		
		// optimize with swaps on tour
		//Solution<TTPVariable> local = new LocalSwapTourAlgorithm(best).run(thief, new Evaluator(Integer.MAX_VALUE), rand);
		
		return best;
	}

}
