package com.msu.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.NProblemNAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.factory.BooleanPackingListFactory;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.thief.scenarios.BonyadiFactory;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;

public class BonyadiExperiment extends NProblemNAlgorithmExperiment<TravellingThiefProblem> {

	final public static String[] INSTANCES = new String[] { 
			"../ttp-benchmark/10/10_15_10_75.txt",
			"../ttp-benchmark/20/20_25_10_75.txt",
			"../ttp-benchmark/50/50_75_10_75.txt",
			"../ttp-benchmark/100/100_150_10_75.txt",
	};
	
	/*
	@Override
	public void report() {
		for(TravellingThiefProblem problem : problems.keySet()) {
			for(IAlgorithm<?> algorithm : algorithms) {
				for(NonDominatedSolutionSet set : expResult.get(problem, algorithm)) {
					ColoredTourScatterPlot sp = new ColoredTourScatterPlot(problem.toString());
					sp.add(set.getSolutions());
					System.out.println(set);
					sp.show();
				}
			}
		}
	}
	*/


	@Override
	protected List<IAlgorithm<TravellingThiefProblem>> getAlgorithms() {
		List<IAlgorithm<TravellingThiefProblem>> algorithms = new ArrayList<>();
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory<>(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		
		/*
		algorithms.add(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[SPX-BFM]").create());
		*/
		algorithms.add(builder.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[CYC-SWAP]-BP[SPX-BFM]").create());
		
		/*
		TTPExhaustiveFactory fac = new TTPExhaustiveFactory();
		ExhaustiveSolver<TTPVariable, TravellingThiefProblem> exhaustive = new ExhaustiveSolver<>(fac);
		algorithms.add(exhaustive);
		*/
		
		return algorithms;
	}

	@Override
	protected Map<TravellingThiefProblem, NonDominatedSolutionSet> getProblems() {
		Map<TravellingThiefProblem, NonDominatedSolutionSet> map = new HashMap<>();
		for(String s : INSTANCES) {
			TravellingThiefProblem ttp = new BonyadiFactory().create(s);
			ttp.setName(s);
			map.put(ttp, null);
		}
		return map;
	}

}
