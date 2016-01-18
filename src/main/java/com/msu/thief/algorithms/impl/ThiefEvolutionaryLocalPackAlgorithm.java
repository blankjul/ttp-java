package com.msu.thief.algorithms.impl;

import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ILocalOptimization;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.tour.FixedTourOnePlusOneEA;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.TourOrderedCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;

public class ThiefEvolutionaryLocalPackAlgorithm extends AThiefSingleObjectiveAlgorithm {

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {
		
		Builder<SingleObjectiveEvolutionaryAlgorithm<TTPVariable, AbstractThiefProblem>> b = 
				new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("verbose",	true)
			.set("factory", new ThiefFactory(new TourOptimalFactory(thief), new PackOptimalFactory(thief)))
			.set("crossover", new ThiefCrossover(new TourOrderedCrossover(), new PackUniformCrossover(thief)))
			.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(thief)))
			.set("local", new ILocalOptimization<TTPVariable, AbstractThiefProblem>() {
				@Override
				public Solution<TTPVariable> run(AbstractThiefProblem problem, IEvaluator evaluator, TTPVariable var) {
					
					ThiefProblemWithFixedTour thief = new ThiefProblemWithFixedTour(problem, var.getTour());
					Solution<Pack> local = new FixedTourOnePlusOneEA(var.getPack()).run(thief, evaluator.createChildEvaluator(100), rand);
					return evaluator.evaluate(problem, TTPVariable.create(var.getTour(), local.getVariable()));
					
					/*					
	 				if (new MyRandom().nextDouble() < 1 / (double) 50) {
						ThiefProblemWithFixedTour thief = new ThiefProblemWithFixedTour(problem, var.getTour());
						Solution<Pack> local = new FixedTourEvolutionOnRelevantItems().run(thief, evaluator, rand);
						return evaluator.evaluate(problem, TTPVariable.create(var.getTour(), local.getVariable()));
					} else {
						return evaluator.evaluate(problem, var);
					}
					*/
					
				}
			});
		
		SingleObjectiveEvolutionaryAlgorithm<TTPVariable, AbstractThiefProblem> a = b.build();
		Solution<TTPVariable> s = a.run(thief, evaluator, rand);
		
		return s;
		
	}

}
