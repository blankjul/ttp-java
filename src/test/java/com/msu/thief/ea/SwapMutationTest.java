package com.msu.thief.ea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.msu.thief.ea.tour.mutation.ThiefSwapMutation;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.Tour;

public class SwapMutationTest extends Operator {

	@Test
	public void testSwapAll() {
		Tour t = Tour.createFromString("[0,1,2,3,4,5,6,7,8,9]");
		ThiefSwapMutation.swap(t, 1, 9);
		assertEquals(Tour.createFromString("[0, 9, 8, 7, 6, 5, 4, 3, 2, 1]"), t);
	}

	@Test
	public void testOnlyLastTwo() {
		Tour t = Tour.createFromString("[0,1,2,3,4,5,6,7,8,9]");
		ThiefSwapMutation.swap(t, 8, 9);
		assertEquals(Tour.createFromString("[0,1,2,3,4,5,6,7,9,8]"), t);
	}

	@Test
	public void test() {
		Tour t = Tour.createFromString("[0,1,2,3,4,5,6,7,8,9]");
		ThiefSwapMutation.swap(t, 3, 5);
		System.out.println(t);
	}

	@Test
	public void testMutation() {
		for (int i = 0; i < 50; i++) {
			Tour p = Tour.createFromString("[0,1,2,3]");
			Tour org = p.copy();
			new ThiefSwapMutation().mutate(thief, rand, p);
			assertTrue(!org.equals(p));
		}
	}

	@Test
	public void testCalculateTimeDifference() {
		for (int i = 0; i < 4; i++) {
			for (int j = i + 1; j < 4; j++) {
				Tour t = Tour.createFromString("[0,1,2,3]");
				double time = new StandardTimeEvaluator().evaluate(thief, t, Pack.empty());
				
				double nextTime = ThiefSwapMutation.swapDeltaTime(t, i, j, time, thief.getMap());
				ThiefSwapMutation.swap(t, i, j);
				
				double expected = new StandardTimeEvaluator().evaluate(thief, t, Pack.empty());
				assertEquals(expected, nextTime, 0.01);
			}
		}
	}
	
	@Test
	public void testCalculateTimeDifferenceBerlin() {
		
		BasicConfigurator.configure();
		
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");
		Tour tour = Tour.createFromString(
				"0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29, 1, 6, 41, 20, 16, 2, 17, 30, 21]");
		
		for (int i = 0; i < tour.encode().size(); i++) {
			for (int j = i + 1; j < tour.encode().size(); j++) {
				Tour t = tour.copy();
				double time = new StandardTimeEvaluator().evaluate(thief, t, Pack.empty());
				
				double nextTime = ThiefSwapMutation.swapDeltaTime(t, i, j, time, thief.getMap());
				ThiefSwapMutation.swap(t, i, j);
				
				double expected = new StandardTimeEvaluator().evaluate(thief, t, Pack.empty());
				assertEquals(expected, nextTime, 0.01);
			}
		}
	}
	


}
