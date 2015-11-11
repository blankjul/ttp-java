package com.msu.algorithms;

import java.util.List;

import com.msu.moo.algorithms.moead.MOEADUtil;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.SingleObjectiveDecomposedProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.soo.algorithms.HillClimbing;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;

public class DecomposedAlgorithm extends AbstractAlgorithm {

	protected int n = 50;

	class Mutation extends AbstractMutation<Pair<Tour<?>, PackingList<?>>> {

		@Override
		protected void mutate_(Pair<Tour<?>, PackingList<?>> pair, Random rand) {

			if (rand.nextDouble() < 0.5) {
				pair.first = pair.first.getSymmetric();
			} else {
				PackingList<?> nextList = (PackingList<?>) new BitFlipMutation().mutate(pair.second.copy(),
						rand);
				pair.second = nextList;
			}
		}
	}

	@Override
	public NonDominatedSolutionSet run_(IEvaluator eval, Random rand) {
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		List<List<Double>> weights = MOEADUtil.getUniformDistributedWeights(rand, n);
		
		ThiefProblem ttp =(ThiefProblem) eval.getProblem();
		SalesmanProblem tsp = new SalesmanProblem(ttp.getMap());
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(new Evaluator(tsp));
		
		
		TTPVariable var = new TTPVariable(bestTour,  new EmptyPackingListFactory().next(ttp, rand));
		IAlgorithm algorithm = new HillClimbing(var, new Mutation());
		
		
		for(List<Double> w : weights) {
			
			IProblem p = new SingleObjectiveDecomposedProblem<>(eval.getProblem(), w);
			Evaluator singleEval = new Evaluator(p, eval.getMaxEvaluations());
			
			NonDominatedSolutionSet result = algorithm.run(singleEval);
			
			if (!result.getSolutions().isEmpty()) {
				Solution solutionToAdd = eval.evaluate(result.get(0).getVariable());
				set.add(solutionToAdd);
			} else {
				System.err.println("No feasible solution found!");
			}
			algorithm = new HillClimbing(new TTPVariable(bestTour,  new EmptyPackingListFactory().next(ttp, rand)), new Mutation());
		}
		
		return set;
		
	}
	
	

}
