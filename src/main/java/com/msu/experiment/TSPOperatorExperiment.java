package com.msu.experiment;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.ObjectFactory;
import com.msu.scenarios.AThiefScenario;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.Tour;
import com.msu.tsp.model.factory.NearestNeighbourFactory;

public class TSPOperatorExperiment extends AMultiObjectiveExperiment<TravellingSalesmanProblem> {

	protected final String[] SCENARIOS = new String[] { 
			"Bays29", 
			"Berlin52", 
			"Eil101", 
			"D198" 
			};

	@Override
	public void visualize() {
		for (IProblem problem : settings.getProblems()) {
			System.out.println(String.format("%s,%s,%s", problem, "Optimum", settings.getOptima().get(problem).get(0).getObjective().get(0)));
			for (IAlgorithm<NonDominatedSolutionSet, ?> algorithm : settings.getAlgorithms()) {
				for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
					if (set.size() != 1)
						throw new RuntimeException("Single Objective problem only one solution allowed.");
					System.out.println(String.format("%s,%s,%s", problem, algorithm, set.get(0).getObjectives(0)));
				}
			}
		}
	}

	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingSalesmanProblem, NonDominatedSolutionSet> settings) {
		NSGAIIBuilder<Tour<?>, TravellingSalesmanProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new NearestNeighbourFactory<TravellingSalesmanProblem>());
		builder.setMutation(new SwapMutation<>());
		builder.setProbMutation(0.3);
		builder.setPopulationSize(1000);
		settings.addAlgorithm(builder.setCrossover(new PMXCrossover<Integer>()).setName("PMX").create());
		settings.addAlgorithm(builder.setCrossover(new CycleCrossover<Integer>()).setName("CX").create());
		settings.addAlgorithm(builder.setCrossover(new OrderedCrossover<Integer>()).setName("OX").create());
		settings.addAlgorithm(builder.setCrossover(new EdgeRecombinationCrossover<Integer>()).setName("ERC").create());
	}

	@Override
	protected void setProblems(ExperimetSettings<TravellingSalesmanProblem, NonDominatedSolutionSet> settings) {
		for (String s : SCENARIOS) {
			@SuppressWarnings("unchecked")
			AThiefScenario<SymmetricMap, Tour<?>> scenario = (AThiefScenario<SymmetricMap, Tour<?>>) ObjectFactory.create("com.msu.tsp.scenarios.impl." + s);
			TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(scenario.getObject());
			tsp.setName(s);
			settings.addProblem(tsp);
			NonDominatedSolutionSet set = new NonDominatedSolutionSet();
			set.add(tsp.evaluate(scenario.getOptimal()));
			settings.addOptima(tsp, set);
		}
	}

	@Override
	protected void setOptima(ExperimetSettings<TravellingSalesmanProblem, NonDominatedSolutionSet> settings) {
		// otherwise all optima are set to zero!
	}
	
	

}