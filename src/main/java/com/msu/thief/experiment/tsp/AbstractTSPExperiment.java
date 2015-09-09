package com.msu.thief.experiment.tsp;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.MultiObjectiveExperiment;
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

public abstract class AbstractTSPExperiment extends MultiObjectiveExperiment<TravellingThiefProblem> {

	protected abstract double[][] getPoints();
	protected abstract Integer[] getOptimum();
	
	@Override
	protected void setAlgorithms() {
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = AlgorithmFactory.createNSGAIIBuilder();
		NSGAII<TTPVariable, TravellingThiefProblem> nsga = builder.create();
		nsga.setPopulationSize(1000);
		algorithms.add(nsga);
	}
	
	@Override
	public void setTrueFront(TravellingThiefProblem problem) {
		List<Integer> best = new ArrayList<>(Arrays.asList(getOptimum()));
		Tour<?> t = new StandardTour(best);
		PackingList<?> l = new BooleanPackingList(new ArrayList<Boolean>());
		Solution s = problem.evaluate(new TTPVariable(Pair.create(t, l)));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		set.add(s);
		System.out.println(set.getSolutions().get(0).getObjectives());
		trueFront = set;
	}
	
	@Override
	protected void setProblem() {

		// add all the cities
		List<Point2D> cities = new ArrayList<>();
		for (double[] p : getPoints()) {
			cities.add(new Point2D.Double(p[0], p[1]));
		}
		com.msu.thief.model.Map m = new MapFactory().createFromDouble(cities);
		// create ttp with zero maximal weight
		problem = new TravellingThiefProblem(m, new ItemCollection<Item>(), 0);
		problem.setName(this.getClass().getSimpleName());

		
		for (int p : getOptimum()) {
			System.out.print((p - 1) + ",");
		}
		System.out.println();
		

	}
	
	public void visualize() {

		//Map<IAlgorithm<P>, List<NonDominatedSolutionSet>>
		
		for (IAlgorithm<TravellingThiefProblem> a : algorithms) {
			List<NonDominatedSolutionSet> sets = fronts.get(a);
			for(NonDominatedSolutionSet s : sets) {
				System.out.println(s.getSolutions().get(0).getObjectives().get(0));
			}
		}
		

	}

	
}

