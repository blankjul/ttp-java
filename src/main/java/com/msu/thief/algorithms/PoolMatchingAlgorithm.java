package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.thief.algorithms.bilevel.tour.BiLevelEvoluationaryAlgorithm;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class PoolMatchingAlgorithm extends ASingleObjectiveAlgorithm {

	final public int POOL_SIZE = 20;

	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		// select the best tour for the start
		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) p;
		BiLevelEvoluationaryAlgorithm algorithm = new BiLevelEvoluationaryAlgorithm();
		Evaluator start = evaluator.createChildEvaluator(evaluator.getMaxEvaluations() / 10);
		
		Solution best = algorithm.run__(problem, start, rand);
		TTPVariable var = (TTPVariable) best.getVariable();
		Tour<?> tour = var.getTour();
		PackingList<?> pack = var.getPackingList();

		while (evaluator.hasNext()) {

			// create the two pools
			List<Tour<?>> tourPool = new ArrayList<>();
			while (tourPool.size() < POOL_SIZE) {
				tourPool.add((Tour<?>) new SwapMutation<>().mutate(tour, problem, rand));
			}

			List<PackingList<?>> packPool = new ArrayList<>();
			while (packPool.size() < POOL_SIZE) {
				packPool.add((PackingList<?>) new BitFlipMutation().mutate(pack, problem, rand));
			}

			// select the best match of all that solutions
			NonDominatedSolutionSet set = new NonDominatedSolutionSet();
			for (int i = 0; i < tourPool.size(); i++) {
				for (int j = 0; j < packPool.size(); j++) {
					System.out.println(new TTPVariable(tourPool.get(i), packPool.get(j)));
					set.add(evaluator.evaluate(p, new TTPVariable(tourPool.get(i), packPool.get(j))));
				}
			}
			
			best = set.get(0);
			var = (TTPVariable) best.getVariable();
			var.getTour();
			var.getPackingList();
			
			System.out.println(best);

		}
		
		return best;
	}

}
