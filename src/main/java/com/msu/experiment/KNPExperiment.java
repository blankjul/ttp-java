package com.msu.experiment;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.packing.factory.BooleanPackingListFactory;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.thief.scenarios.AScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;


/**
 * 
 * Scenarios:
 * 
 * KNP_13_20_1000_1
 * KNP_13_200_1000_1
 * KNP_13_1000_1000_1
 * KNP_13_2000_1000_1
 *
 */
public class KNPExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {

	
	//! the current scenario which is executed
	@SuppressWarnings("unchecked")
	protected final AScenario<Pair<List<Item>,Integer>, PackingList<?>> scenario = 
			(AScenario<Pair<List<Item>,Integer>, PackingList<?>>) ObjectFactory.create( "com.msu.knp.impl.scenarios.KNP_13_20_1000_1");
	
	
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
		Pair<List<Item>,Integer> obj = scenario.getObject();
		
		// add all to the first city
		ItemCollection<Item> items = new ItemCollection<>();
		for(Item i : obj.first) items.add(0, i);
		
		TravellingThiefProblem problem = new TravellingThiefProblem(new SymmetricMap(1), new ItemCollection<Item>(), obj.second);
		problem.setName(this.getClass().getSimpleName());
		
		return problem;
	}
	
	
	
	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		Tour<?> t = new StandardTour(new ArrayList<Integer>(0));
		
		Solution s = problem.evaluate(new TTPVariable(Pair.create(t,scenario.getOptimal())));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet(Arrays.asList(s));
		
		System.out.println(set.getSolutions().get(0).getObjectives());
		return set;
	}

	
}

