package com.msu.algorithms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.msu.moo.model.solution.Solution;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.tour.StandardTour;

public class MATLValueTest {
/*
	@Test
	public void objectiveValueTest() {
		List<Integer> l = Arrays.asList(1,49,32,45,19,41,8,9,10,43,33,51,11,52,13,14,47,26,27,28,12,25,4,6,15,5,24,48,46,16,29,30,42,2,7,17,3,18,31,21,23,20,50,44,37,38,40,39,36,35,34,22);
		for (int i = 0; i < l.size(); i++) {
			l.set(i, l.get(i) - 1);
		}
		
		
		List<Integer> items =  Arrays.asList(2,3,4,18,19,20,21,33,34,35,43,49,50,51);
		for (int i = 0; i < items.size(); i++) {
			items.set(i, items.get(i) - 1);
		}
		Set<Integer> b = new HashSet<>(items);
		
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader().read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");
		
		Solution s = thief.evaluate(new TTPVariable(new StandardTour(l), new IntegerSetPackingList(b,thief.numOfItems())));
		System.out.println(s);
	}
	*/
	@Test
	public void objectiveValueMyPublicationTest() {
		List<Integer> l = Arrays.asList(1,4,3,2);
		for (int i = 0; i < l.size(); i++) {
			l.set(i, l.get(i) - 1);
		}
		
		
		List<Integer> items =  Arrays.asList(1,2,4);
		for (int i = 0; i < items.size(); i++) {
			items.set(i, items.get(i) - 1);
		}
		
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader().read("../ttp-benchmark/TSPLIB/my_publication.ttp");
		TTPVariable var = new TTPVariable(new StandardTour(l), new IntegerSetPackingList(new HashSet<>(items),thief.numOfItems()));
		Solution s = thief.evaluate(var);
		System.out.println(s);
	}
	
}
