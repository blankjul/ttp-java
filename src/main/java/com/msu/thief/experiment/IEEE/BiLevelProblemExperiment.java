package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.BilevelAlgorithmsFixedTour;
import com.msu.thief.algorithms.bilevel.tour.BiLevelSwapTour;
import com.msu.thief.algorithms.bilevel.tour.GreedyPackingAlgorithm;
import com.msu.thief.algorithms.bilevel.tour.SolveKnapsackWithHeuristicValues;
import com.msu.thief.algorithms.bilevel.tour.GreedyPackingAlgorithm.TYPE;
import com.msu.thief.algorithms.bilevel.tour.divide.DivideAndConquerAlgorithm;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.experiment.SingleObjectiveReport;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.util.FileCollectorParser;

public class BiLevelProblemExperiment extends AExperiment {


	protected void initialize() {
		new SingleObjectiveReport("../ttp-results/coevo.csv");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();

		fcp.add("../ttp-benchmark/SingleObjective/10", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "*_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100","*_*_1_*.txt", new BonyadiSingleObjectiveReader());

		problems.addAll(fcp.collect());
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
/*
		Builder<BilevelAlgorithmsFixedTour> builder = new Builder<>(BilevelAlgorithmsFixedTour.class);
		builder
			.set("algorithm", new GreedyPackingAlgorithm(TYPE.BEST))
			.set("name", "BILEVEL-GREEDY-BEST");
		algorithms.add(builder.build());
		
		builder
			.set("algorithm", new GreedyPackingAlgorithm(TYPE.RANDOM))
			.set("name", "BILEVEL-GREEDY-RANDOM");
		algorithms.add(builder.build());
		
		builder
			.set("algorithm", new SolveKnapsackWithHeuristicValues())
			.set("name", "BILEVEL-KNP-HEURISTIC");
		algorithms.add(builder.build());
		
		
		Builder<DivideAndConquerAlgorithm> divide = new Builder<>(DivideAndConquerAlgorithm.class);
		divide
			.set("name", "D&C");
		algorithms.add(new BilevelAlgorithmsFixedTour(divide.build()));
		
		
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
				.set("name", "EA-NO-UX");
		algorithms.add(new BilevelAlgorithmsFixedTour(eaPop.build()));

		builder
			.set("algorithm", new BiLevelSwapTour())
			.set("name", "BILEVEL-EA-SWAP-UX");
		algorithms.add(builder.build());
	
	
		Builder<SingleObjectiveEvolutionaryAlgorithm> changeTour = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		changeTour
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()))
				.set("mutation", new TTPMutation(new SwapMutation<>(), new BitFlipMutation()))
				.set("name", "EA-SWAP-UX");
		algorithms.add(changeTour.build());
		
		*/
		Builder<SingleObjectiveEvolutionaryAlgorithm> changeTourNO = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		changeTourNO
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()))
				.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()))
				.set("name", "EA-NO-UX");
		algorithms.add(changeTourNO.build());
		

		
	}

}