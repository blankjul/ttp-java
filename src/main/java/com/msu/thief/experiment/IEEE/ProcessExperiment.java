package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.recombinations.TTPNeighbourSwapMutation;
import com.msu.thief.experiment.SingleObjectiveReport;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;

public class ProcessExperiment extends AExperiment {


	protected void initialize() {
		//new SingleObjectiveReport("/home/julesy/Dropbox/result.csv");
		new SingleObjectiveReport("../ttp-results/local.csv");
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
		
		IAlgorithm localSearch = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()))
				.set("mutation", new TTPMutation(new TTPNeighbourSwapMutation(), new BitFlipMutation()))
				.set("name", "EA-NEIGHBOUR").build();

		algorithms.add(localSearch);
		
		//algorithms.add(new TwoPhaseEvolution());

	}

}