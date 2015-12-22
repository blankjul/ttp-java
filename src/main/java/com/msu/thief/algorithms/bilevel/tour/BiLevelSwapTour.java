package com.msu.thief.algorithms.bilevel.tour;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.factory.FixedTourFactory;
import com.msu.util.MyRandom;

public class BiLevelSwapTour extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {


	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {
		Builder<SingleObjectiveEvolutionaryAlgorithm> packBuilder = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new FixedTourFactory(problem.getTour()), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()))
				.set("mutation", new TTPMutation(new SwapMutation<>(), new BitFlipMutation()))
				.set("name", "EA-HUX");
		return packBuilder.build().run__(problem.getProblem(), evaluator, rand);
	}
	
	

}
