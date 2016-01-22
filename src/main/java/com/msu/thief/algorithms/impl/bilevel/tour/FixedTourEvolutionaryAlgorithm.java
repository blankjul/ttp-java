package com.msu.thief.algorithms.impl.bilevel.tour;

import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.interfaces.AFixedTourSingleObjectiveAlgorithm;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;

public class FixedTourEvolutionaryAlgorithm extends AFixedTourSingleObjectiveAlgorithm {


	@Override
	public Solution<Pack> run(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {
		
		final AbstractThiefProblem thief = problem.getProblem();
	

		// create the evolutionary algorithm
		Builder<SingleObjectiveEvolutionaryAlgorithm<Pack, ThiefProblemWithFixedTour>> b = 
				new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		
		b
			.set("populationSize", 100)
			.set("probMutation", 0.3)
			.set("factory", new PackOptimalFactory(thief))
			.set("crossover", new PackUniformCrossover(thief))
			.set("mutation", new PackBitflipMutation(thief));
			
		
		SingleObjectiveEvolutionaryAlgorithm<Pack, ThiefProblemWithFixedTour> ea = b.build();
		Solution<Pack> result = ea.run(problem, evaluator, rand);
		
		return result;
		
	}

}
