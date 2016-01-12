package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.thief.algorithms.BestSoFarAlgorithm;
import com.msu.thief.experiment.SingleObjectiveReport;

public class ProcessExperiment extends AExperiment {


	protected void initialize() {
		//new SingleObjectiveReport("/home/julesy/Dropbox/result.csv");
		new SingleObjectiveReport("../ttp-results/local_.csv");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		
		//problems.addAll(IEEE.getFirstInstancesProblems());
		problems.addAll(IEEE.getProblems());
		
/*		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/SingleObjective/50", "50_75_6_25.txt", new BonyadiSingleObjectiveReader());
		problems.addAll(fcp.collect());*/
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		
/*		
		IAlgorithm localSearch = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()))
				.set("mutation", new TTPMutation(new InverseSwapMutation(), new BitFlipMutation()))
				.set("name", "EA-NEIGHBOUR").build();
		algorithms.add(localSearch);*/
		
/*
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
		*/
		
		algorithms.add(new BestSoFarAlgorithm());
		
		//algorithms.add(new TwoPhaseEvolution());

	}

}