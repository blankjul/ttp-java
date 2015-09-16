package com.msu.experiment;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.OneProblemOneAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.ObjectFactory;
import com.msu.moo.util.Pair;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.packing.factory.BooleanPackingListFactory;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.thief.scenarios.AScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;


/**
 * Execute the Traveling Thief Problem algorithms on a instance with no items. In principal 
 * it is a degenerated problem instance!
 * 
 * Scenarios:
 * Bays29, Berlin52, Eil101, D198
 *
 */
public class TSPExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {

	
	//! the current scenario which is executed
	@SuppressWarnings("unchecked")
	protected final AScenario<SymmetricMap, Tour<?>> scenario = 
			(AScenario<SymmetricMap, Tour<?>>) ObjectFactory.create( "com.msu.tsp.scenarios.impl.Bays29");
	
	
	public void report() {
		for (IAlgorithm<TravellingThiefProblem> a : algorithms) {
			Collection<NonDominatedSolutionSet> sets = expResult.get(problem, a);
			for(NonDominatedSolutionSet s : sets) {
				System.out.println(s.getSolutions().get(0).getObjectives().get(0));
			}
		}
	}
	
	
	@Override
	protected IAlgorithm<TravellingThiefProblem> getAlgorithm() {
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory<>(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<>()));
		builder.setProbMutation(0.3);
		builder.setPopulationSize(1000);
		return builder.create();
	}
	
	
	
	@Override
	protected TravellingThiefProblem getProblem() {
		TravellingThiefProblem problem = new TravellingThiefProblem(scenario.getObject(), new ItemCollection<Item>(), 0);
		problem.setName(this.getClass().getSimpleName());
		return problem;
	}
	
	
	
	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		PackingList<?> l = new BooleanPackingList(new ArrayList<Boolean>());
		
		Solution s = problem.evaluate(new TTPVariable(Pair.create(scenario.getOptimal(), l)));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet(Arrays.asList(s));
		
		System.out.println(set.getSolutions().get(0).getObjectives());
		return set;
	}

	
}

