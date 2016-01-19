package com.msu.thief.algorithms.impl;

import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ILocalOptimization;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.evaluator.UnlimitedEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.bilevel.pack.FixedPackSwapTour;
import com.msu.thief.algorithms.impl.bilevel.tour.FixedTourEvolutionOnRelevantItems;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.ea.operators.TourOrderedCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPack;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefEvolutionaryLocalPackAlgorithm extends AThiefSingleObjectiveAlgorithm {

	
	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {
		
		class BankToBankLocalOptimization implements ILocalOptimization<TTPVariable, AbstractThiefProblem> {
			@Override
			public Solution<TTPVariable> run(AbstractThiefProblem problem, IEvaluator l, TTPVariable var) {
				if (new MyRandom().nextDouble() < 1 / (double) 50) {
						Solution<Tour> localTour = new FixedPackSwapTour(var.getTour()).run(new ThiefProblemWithFixedPack(thief, var.getPack()), new UnlimitedEvaluator(), rand);
						Solution<Pack> localPack = new FixedTourEvolutionOnRelevantItems().run(new ThiefProblemWithFixedTour(problem, localTour.getVariable()), new StandardEvaluator(50000), rand);
					return problem.evaluate(TTPVariable.create(localTour.getVariable(), localPack.getVariable()));
				} else {
					return problem.evaluate(var);
				}
			}
		}
		
		
		Builder<SingleObjectiveEvolutionaryAlgorithm<TTPVariable, AbstractThiefProblem>> b = 
				new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("verbose",	true)
			.set("local", new BankToBankLocalOptimization())
			.set("factory", new ThiefFactory(new TourOptimalFactory(thief), new PackOptimalFactory(thief)))
			.set("crossover", new ThiefCrossover(new TourOrderedCrossover(), new PackUniformCrossover(thief)))
			.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(thief)));
		
		SingleObjectiveEvolutionaryAlgorithm<TTPVariable, AbstractThiefProblem> a = b.build();
		Solution<TTPVariable> s = a.run(thief, evaluator, rand);
		
		return s;
		
	}

}
