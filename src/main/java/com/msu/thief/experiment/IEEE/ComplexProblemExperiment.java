package com.msu.thief.experiment.IEEE;

import java.util.Arrays;
import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.SinglePointCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.crossover.permutation.CycleCrossover;
import com.msu.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.crossover.permutation.PMXCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.experiment.SingleObjectiveReport;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.APackingListFactory;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.pack.factory.RandomPackingListFactory;
import com.msu.thief.variable.tour.factory.ATourFactory;
import com.msu.thief.variable.tour.factory.NearestNeighbourFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.thief.variable.tour.factory.RandomTourFactory;
import com.msu.thief.variable.tour.factory.TwoOptFactory;

public class ComplexProblemExperiment extends AExperiment {


	protected void initialize() {
		new SingleObjectiveReport("../ttp-results/complex.csv");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.addAll(IEEE.getProblems());
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {

		Builder<SingleObjectiveEvolutionaryAlgorithm> singleEAFrame = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);

		singleEAFrame.set("populationSize", 50).set("probMutation", 0.3);

		for (String facTourStr : Arrays.asList("RANDOM", "NEAREST", "2OPT", "OPT")) {

			ATourFactory fackTour = null;

			if (facTourStr.equals("RANDOM")) {
				fackTour = new RandomTourFactory();
			} else if (facTourStr.equals("NEAREST")) {
				fackTour = new NearestNeighbourFactory();
			} else if (facTourStr.equals("2OPT")) {
				fackTour = new TwoOptFactory();
			} else if (facTourStr.equals("OPT")) {
				fackTour = new OptimalTourFactory();
			}

			for (String facPackStr : Arrays.asList("EMPTY", "RANDOM", "OPT")) {

				APackingListFactory facPack = null;

				if (facPackStr.equals("EMPTY")) {
					facPack = new EmptyPackingListFactory();
				} else if (facPackStr.equals("RANDOM")) {
					facPack = new RandomPackingListFactory();
				} else if (facPackStr.equals("OPT")) {
					facPack = new OptimalPackingListFactory();
				}
				for (String crossoverTourStr : Arrays.asList("NO", "OX", "CX", "PMX", "ERX")) {

					AbstractCrossover<List<Boolean>> crossTour = null;

					if (crossoverTourStr.equals("NO")) {
						crossTour = new NoCrossover<>();
					} else if (crossoverTourStr.equals("OX")) {
						crossTour = new OrderedCrossover<>();
					} else if (crossoverTourStr.equals("CX")) {
						crossTour = new CycleCrossover<>();
					} else if (crossoverTourStr.equals("PMX")) {
						crossTour = new PMXCrossover<>();
					} else if (crossoverTourStr.equals("ERX")) {
						crossTour = new EdgeRecombinationCrossover<>();
					}

					for (String crossoverPackStr : Arrays.asList("NO", "HUX", "UX", "SPX")) {

						AbstractCrossover<List<Boolean>> crossPack = null;

						if (crossoverPackStr.equals("HUX")) {
							crossPack = new HalfUniformCrossover<>();
						} else if (crossoverPackStr.equals("UX")) {
							crossPack = new UniformCrossover<>();
						} else if (crossoverPackStr.equals("SPX")) {
							crossPack = new SinglePointCrossover<>();
						}else if (crossoverPackStr.equals("NO")) {
							crossPack = new NoCrossover<>();
						}

						singleEAFrame.set("name",
								String.format("%s,%s,%s,%s", facTourStr, facPackStr, crossoverTourStr, crossoverPackStr));

						singleEAFrame.set("factory", new TTPVariableFactory(fackTour, facPack))
								.set("crossover", new TTPCrossover(crossTour, crossPack))
								.set("mutation", new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
						algorithms.add(singleEAFrame.build());

					}

				}
			}
		}

	}

}