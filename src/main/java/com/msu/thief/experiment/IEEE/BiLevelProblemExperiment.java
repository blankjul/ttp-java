package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.BilevelAlgorithmsFixedTour;
import com.msu.thief.algorithms.bilevel.tour.GreedyPackingAlgorithm;
import com.msu.thief.algorithms.bilevel.tour.GreedyPackingAlgorithm.TYPE;
import com.msu.thief.algorithms.bilevel.tour.divide.DivideAndConquerAlgorithm;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.experiment.SingleObjectiveReport;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;

public class BiLevelProblemExperiment extends AExperiment {


	protected void initialize() {
		new SingleObjectiveReport("../ttp-results/bilevel.csv");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.addAll(IEEE.getProblems());
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {

		Builder<BilevelAlgorithmsFixedTour> builder = new Builder<>(BilevelAlgorithmsFixedTour.class);
		builder
			.set("algorithm", new GreedyPackingAlgorithm(TYPE.BEST))
			.set("name", "GREEDY-BEST");
		algorithms.add(builder.build());
		
		builder
			.set("algorithm", new GreedyPackingAlgorithm(TYPE.RANDOM))
			.set("name", "GREEDY-RANDOM");
		algorithms.add(builder.build());
		
		
		Builder<DivideAndConquerAlgorithm> divide = new Builder<>(DivideAndConquerAlgorithm.class);
		divide
			.set("name", "D&C");
		algorithms.add(divide.build());
		
		
		IAlgorithm a = new OnePlusOneEAFixedTour();
		a.setName("1+1-EA");
		algorithms.add(new BilevelAlgorithmsFixedTour(a));
		
		
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> eaPop = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		eaPop
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new OptimalPackingListFactory())
				.set("crossover", new UniformCrossover<>())
				.set("mutation", new BitFlipMutation())
				.set("name", "EA-OPT-UX");
		algorithms.add(new BilevelAlgorithmsFixedTour(eaPop.build()));

		
		Builder<SingleObjectiveEvolutionaryAlgorithm> changeTour = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		changeTour
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()))
				.set("mutation", new TTPMutation(new SwapMutation<>(), new BitFlipMutation()))
				.set("name", "EA-UX-SWAP");
		algorithms.add(changeTour.build());
		
		
		
		
	}

}