package com.msu.visualize;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.plots.ScatterPlot;
import com.msu.thief.variable.TTPVariable;

public class ColoredTourScatterPlot extends ScatterPlot {

	public ColoredTourScatterPlot(String title) {
		super(title);
	}

	public void add(SolutionSet set) {
		add(set, "");
	}

	public void add(SolutionSet set, String prefix) {
		Multimap<List<Integer>, Solution> multi = ArrayListMultimap.create();
		for (Solution s : set) {
			TTPVariable var = (TTPVariable) s.getVariable();
			multi.put(var.get().first.encode(), s);
		}

		int counter = 0;
		for (List<Integer> tour : multi.keySet()) {
			SolutionSet setToAdd = new SolutionSet(multi.get(tour));
			String lbl = "";
			lbl = prefix + " " + String.valueOf(counter);
			System.out.println(prefix + " " + String.format("[%1$3s] ", counter) + tour.toString());
			++counter;
			super.add(setToAdd, lbl);
		}

	}


}
