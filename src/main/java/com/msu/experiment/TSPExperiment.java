package com.msu.experiment;


import java.util.ArrayList;
import java.util.Arrays;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.knp.model.factory.RandomPackingListFactory;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.ObjectFactory;
import com.msu.moo.util.Pair;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.scenarios.AScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.Tour;
import com.msu.tsp.model.factory.RandomFactory;


/**
 * Execute the Traveling Thief Problem algorithms on a instance with no items. In principal 
 * it is a degenerated problem instance!
 * 
 * Scenarios:
 * Bays29, Berlin52, Eil101, D198
 *
 */
public class TSPExperiment extends AMultiObjectiveExperiment<TravellingThiefProblem> {

	

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

	
	//! the current scenario which is executed
	@SuppressWarnings("unchecked")
	protected final AScenario<SymmetricMap, Tour<?>> scenario = 
			(AScenario<SymmetricMap, Tour<?>>) ObjectFactory.create( "com.msu.tsp.scenarios.impl.Berlin52");
	
	
	
	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new RandomPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setCrossover(new TTPCrossover(new PMXCrossover<Integer>(), new SinglePointCrossover<>()));
		builder.setProbMutation(0.3);
		builder.setPopulationSize(100);
		settings.addAlgorithm(builder.create());
	}
	
	
	@Override
	protected void setProblems(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		TravellingThiefProblem problem = new TravellingThiefProblem(scenario.getObject(), new ItemCollection<Item>(), 0);
		problem.setName(this.getClass().getSimpleName());
		settings.addProblem(problem);
	}
	
	
	@Override
	protected void setOptima(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		TravellingThiefProblem problem = settings.getProblems().get(0);
		PackingList<?> l = new BooleanPackingList(new ArrayList<Boolean>());
		
		Solution s = problem.evaluate(new TTPVariable(Pair.create(scenario.getOptimal(), l)));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet(Arrays.asList(s));
		settings.addOptima(problem, set);
	}
	

	
}

