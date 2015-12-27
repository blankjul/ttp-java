package com.msu.algorithms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.TwoPhaseEvolution;
import com.msu.thief.algorithms.coevolution.CoevolutionAlgorithm;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.util.MyRandom;

public class MATLValueTest {
/*
	@Test
	public void objectiveValueTest() {
		BasicConfigurator.configure();
		List<Integer> l = Arrays.asList(1,49,32,45,19,41,8,9,10,43,33,51,11,52,14,13,47,26,27,28,12,25,4,6,5,15,48,24,38,40,37,46,16,29,30,2,7,42,21,17,3,18,31,23,20,50,44,34,39,36,35,22);
		for (int i = 0; i < l.size(); i++) {
			l.set(i, l.get(i) - 1);
		}
		
		
		List<Integer> items =  Arrays.asList(2,3,4,18,19,20,21,33,34,35,43,49,50,51);
		for (int i = 0; i < items.size(); i++) {
			items.set(i, items.get(i) - 1);
		}
		Set<Integer> b = new HashSet<>(items);
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader().read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");
		
		System.out.println("-----------");
		Solution s = thief.evaluate(new TTPVariable(new StandardTour(l), new IntegerSetPackingList(b,thief.numOfItems())));
		System.out.println("-----------");
		System.out.println(s);
		
		Solution ea = new AlternatingPoolingEvolution().run(thief, new Evaluator(500000), new MyRandom(123456)).get(0);
		System.out.println(ea);
		thief.setToMultiObjective(true);
		System.out.println(thief.evaluate(ea.getVariable()));
	}
	
	*/
	
	
	
	@Test
	public void objectiveValueMyPublicationTest() {
		List<Integer> l = Arrays.asList(1,4,3,2);
		for (int i = 0; i < l.size(); i++) {
			l.set(i, l.get(i) - 1);
		}
		
		
		List<Integer> items =  Arrays.asList(1,3);
		for (int i = 0; i < items.size(); i++) {
			items.set(i, items.get(i) - 1);
		}
		
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader().read("../ttp-benchmark/TSPLIB/my_publication.ttp");
		TTPVariable var = new TTPVariable(new StandardTour(l), new IntegerSetPackingList(new HashSet<>(items),thief.numOfItems()));
		Solution s = thief.evaluate(var);
		System.out.println(s);
		
		Solution ea = new CoevolutionAlgorithm().run(thief, new Evaluator(500000), new MyRandom(123456)).get(0);
		System.out.println(ea);
		thief.setToMultiObjective(true);
		System.out.println(thief.evaluate(ea.getVariable()));
		
	}
	
}
