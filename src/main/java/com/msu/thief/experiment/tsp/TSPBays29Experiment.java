package com.msu.thief.experiment.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.experiment.AbstractExperiment;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.thief.factory.AlgorithmFactory;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPVariable;

public class TSPBays29Experiment extends AbstractExperiment<TravellingThiefProblem> {

	@Override
	protected void setAlgorithms() {
		algorithms.add(AlgorithmFactory.createNSGAII());
	}

	@Override
	protected void setProblem() {
		com.msu.thief.model.Map m = new com.msu.thief.model.Map(29);
		m.setDistances(Bays29.MAP);
		problem = new TravellingThiefProblem(m, new ItemCollection<Item>(), 0);
		problem.setName("TSPBays29Problem");
	}

	@Override
	public NonDominatedSolutionSet getTrueFront(TravellingThiefProblem problem) {
		List<Integer> best = new ArrayList<>(Arrays.asList(Bays29.OPTIMAL));
		Tour<?> t = new StandardTour(best);
		PackingList<?> l = new BooleanPackingList(new ArrayList<Boolean>(Arrays.asList(false, false, false)));
		Solution s = problem.evaluate(new TTPVariable(Pair.create(t, l)));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		set.add(s);
		return set;
	}

	
	
}
