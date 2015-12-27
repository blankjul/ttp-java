package com.msu.thief.experiment;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.thief.algorithms.TwoPhaseEvolution;
import com.msu.thief.algorithms.coevolution.CoevolutionAlgorithm;
import com.msu.thief.experiment.IEEE.IEEE;

public class FinalExperiment extends AExperiment {


	protected void initialize() {
		new SingleObjectiveReport("../ttp-results/bilevel_temp.csv");
	};

	
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.addAll(IEEE.getProblems());
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
/*
		Builder<OnePlusOneEA> builderOnePlusOne = new Builder<>(OnePlusOneEA.class);
		builderOnePlusOne.set("checkSymmetric", false).set("name", "1+1-EA");
		algorithms.add(builderOnePlusOne.build());

		builderOnePlusOne.set("checkSymmetric", true).set("name", "1+1-EA-SYM");
		algorithms.add(builderOnePlusOne.build());

		Builder<SingleObjectiveEvolutionaryAlgorithm> singleEAFrame = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		
		
		singleEAFrame
			.set("populationSize", 50)
			.set("probMutation", 0.3);
		
		
		for (String factory : Arrays.asList("EMPTY", "RANDOM", "OPT")) {

			APackingListFactory fac = null;
			
			if (factory.equals("EMPTY")) {
				fac = new EmptyPackingListFactory();
			} else if (factory.equals("RANDOM")) {
				fac = new RandomPackingListFactory();
			} else if (factory.equals("OPT")) {
				fac = new OptimalPackingListFactory();
			}
			
			for (String crossover : Arrays.asList("HUX", "UX", "SPX")) {

				AbstractCrossover<List<Boolean>> cross = null;
				
				if (crossover.equals("HUX")) {
					cross = new HalfUniformCrossover<>();
				} else if (crossover.equals("UX")) {
					cross = new UniformCrossover<>();
				} else if (crossover.equals("SPX")) {
					cross = new SinglePointCrossover<>();
				}
				
				for (String type : Arrays.asList("POOL", "BILEVEL")) {
					
					singleEAFrame .set("name", String.format("EA-%s-%s", factory, crossover));
					
					if (type.equals("POOL")) {
						
						singleEAFrame
							.set("factory", new TTPVariableFactory(new OptimalTourFactory(), fac))
							.set("crossover", new TTPCrossover(new NoCrossover<>(), cross))
							.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()));
						algorithms.add(singleEAFrame.build());
						
					} else if (type.equals("BILEVEL")) {
						
						singleEAFrame
							.set("factory", fac)
							.set("crossover", cross)
							.set("mutation", new BitFlipMutation());
							
						algorithms.add(new BiLevelAlgorithms(singleEAFrame.build()));
						
					}
				}
			}
		}

		
		
	
		IAlgorithm a = new OnePlusOneEAFixedTour();
		a.setName("1+1-EA");
		algorithms.add(new BiLevelAlgorithms(a));

		
		Builder<SingleObjectiveEvolutionaryAlgorithm> changeTour = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		changeTour.set("populationSize", 50).set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
				.set("mutation", new TTPMutation(new SwapMutation<>(), new BitFlipMutation()))
				.set("name", "EA-HUX-SWAP");
		algorithms.add(changeTour.build());

		Builder<GreedyPackingAlgorithm> greedy = new Builder<>(GreedyPackingAlgorithm.class);
		greedy.set("type", GreedyPackingAlgorithm.TYPE.BEST).set("name", "GREEDY-BEST");
		algorithms.add(new BiLevelAlgorithms(greedy.build()));

		greedy.set("type", GreedyPackingAlgorithm.TYPE.RANDOM).set("name", "GREEDY-RANDOM");
		algorithms.add(new BiLevelAlgorithms(greedy.build()));
		
*/
		
		algorithms.add(new Builder<CoevolutionAlgorithm>(CoevolutionAlgorithm.class)
				.set("mergeElementWise", true)
				.set("name", "COEVO-ELEMENT").build());
		
		algorithms.add(new Builder<CoevolutionAlgorithm>(CoevolutionAlgorithm.class)
				.set("mergeElementWise", false)
				.set("name", "COEVO-POOL").build());
		
		algorithms.add(new TwoPhaseEvolution());
		

	}

}