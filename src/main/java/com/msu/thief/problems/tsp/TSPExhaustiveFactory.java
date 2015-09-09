package com.msu.thief.problems.tsp;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.util.CombinatorialUtil;

public class TSPExhaustiveFactory implements VariableFactory<Tour<?>,TravellingSalesmanProblem> {

	
	//! all the permutations that exist
	protected Queue<List<Integer>> permutations = null;


	@Override
	public Tour<?> create(TravellingSalesmanProblem problem) {
		if (permutations == null) {
			LinkedList<Integer> initial = new LinkedList<Integer>();
			for (int i = 0; i < problem.numOfCities(); i++) {
				initial.add(i);
			}
			permutations = new LinkedList<>(CombinatorialUtil.permute(initial));
		}
		else if (permutations.isEmpty()) return null;
		List<Integer> list = permutations.poll();
		return new StandardTour(list);

	}



}
