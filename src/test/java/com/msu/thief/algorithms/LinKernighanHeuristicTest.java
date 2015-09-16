package com.msu.thief.algorithms;

import org.junit.Test;

import com.msu.algorithms.LinKernighanHeuristic;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.experiment.Bays29Experiment;

public class LinKernighanHeuristicTest {
	
	
	@Test
	public void testNoError() {
		final int length = Bays29Experiment.MAP.length;
		SymmetricMap m = new SymmetricMap(length);
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				m.set(i, j, Bays29Experiment.MAP[i][j]);
			}
		}
		
		LinKernighanHeuristic lkh = new LinKernighanHeuristic();
		NonDominatedSolutionSet set = lkh.run(new TravellingSalesmanProblem(m));
		System.out.println(set);
	}
	
	

}
