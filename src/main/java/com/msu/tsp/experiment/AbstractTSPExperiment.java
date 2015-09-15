package com.msu.tsp.experiment;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.OneProblemOneAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.thief.factory.AlgorithmFactory;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPVariable;

public abstract class AbstractTSPExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {

	protected abstract double[][] getPoints();
	protected abstract Integer[] getOptimum();
	

	
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
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = AlgorithmFactory.createNSGAIIBuilder();
		NSGAII<TTPVariable, TravellingThiefProblem> nsga = builder.create();
		nsga.setPopulationSize(1000);
		return nsga;
	}
	
	
	
	@Override
	protected TravellingThiefProblem getProblem() {
		// add all the cities
		List<Point2D> cities = new ArrayList<>();
		for (double[] p : getPoints()) {
			cities.add(new Point2D.Double(p[0], p[1]));
		}
		com.msu.thief.model.SymmetricMap m = new MapFactory().createFromDouble(cities);
		TravellingThiefProblem problem = new TravellingThiefProblem(m, new ItemCollection<Item>(), 0);
		problem.setName(this.getClass().getSimpleName());
		return problem;
	}
	
	
	
	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		List<Integer> best = new ArrayList<>(Arrays.asList(getOptimum()));
		Tour<?> t = new StandardTour(best);
		PackingList<?> l = new BooleanPackingList(new ArrayList<Boolean>());
		Solution s = problem.evaluate(new TTPVariable(Pair.create(t, l)));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		set.add(s);
		System.out.println(set.getSolutions().get(0).getObjectives());
		return set;
	}

	
}

