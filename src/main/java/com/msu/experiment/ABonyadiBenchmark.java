package com.msu.experiment;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.algorithms.RandomLocalSearch;
import com.msu.knp.model.factory.EmptyPackingListFactory;
import com.msu.knp.model.factory.FullPackingListFactory;
import com.msu.knp.model.factory.RandomPackingListFactory;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.ThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.factory.NearestNeighbourFactory;
import com.msu.tsp.model.factory.OptimumFactory;
import com.msu.tsp.model.factory.RandomFactory;

public abstract class ABonyadiBenchmark extends AMultiObjectiveExperiment<ThiefProblem> {

	@Override
	public void visualize() {
	}

	@Override
	protected void report_(ThiefProblem problem, IAlgorithm<NonDominatedSolutionSet, ThiefProblem> algorithm, NonDominatedSolutionSet set) {
		System.out.println(String.format("%s,%s,%s,%s", problem, algorithm, set.getSolutions().get(0).getObjectives(0), set.getSolutions().get(0) ));
	}

	@Override
	protected void setAlgorithms(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		NSGAIIBuilder<TTPVariable, ThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<>(), new HalfUniformCrossover<>()));
		builder.setProbMutation(0.3);
		builder.setPopulationSize(100);
		
		builder.setFactory(new TTPVariableFactory(new OptimumFactory<>(), new FullPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[OPT-FULL]-[ERX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new OptimumFactory<>(), new EmptyPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[OPT-EMPTY]-[ERX-SWAP]-[HUX-BFM]").create());
		
		/*
		builder.setFactory(new TTPVariableFactory(new OptimumFactory<>(), new RandomPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[OPT-RANDOM]-[ERX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new FullPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[RANDOM-FULL]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new EmptyPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[RANDOM-EMPTY]-[ERX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new RandomPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[RANDOM-RANDOM]-[ERX-SWAP]-[HUX-BFM]").create());
		
		
		builder.setFactory(new TTPVariableFactory(new NearestNeighbourFactory<>(), new FullPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[NEAREST-FULL]-[ERX-SWAP]-[HUX-BFM]").create());
		*/
		builder.setFactory(new TTPVariableFactory(new NearestNeighbourFactory<>(), new EmptyPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[NEAREST-EMPTY]-[ERX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new NearestNeighbourFactory<>(), new RandomPackingListFactory()));
		settings.addAlgorithm(builder.setName("NSGAII-[NEAREST-RANDOM]-[ERX-SWAP]-[HUX-BFM]").create());
		
		
		
		//settings.addAlgorithm(new OnePlusOneEA());
		//settings.addAlgorithm(new RandomLocalSearch());
	}

	protected String getPath(String prefix, String name) {
		return String.format("%s/%s/%s.txt", prefix, name.split("_")[0], name);
	}

}
