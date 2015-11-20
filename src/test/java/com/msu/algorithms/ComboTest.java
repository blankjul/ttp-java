package com.msu.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.algorithms.KnapsackCombo;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.thief.reader.KnapsackProblemReader;
import com.msu.thief.model.Item;
import com.msu.thief.problems.KnapsackProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.MyRandom;

public class ComboTest {
	
	
	protected final String[] SCENARIOS = new String[] { 
			"resources/knapPI_13_0020_1000.csv",
			"resources/knapPI_13_0200_1000.csv",
			"resources/knapPI_13_1000_1000.csv",
			"resources/knapPI_13_2000_1000.csv"
			};
	
	
	@Test
	public void testCorrectResult() {
		List<Item> items = new ArrayList<>();
		items.add(new Item(100, 3));
		items.add(new Item(200, 3));
		items.add(new Item(300, 3));
		items.add(new Item(400, 3));
		
		KnapsackProblem problem = new KnapsackProblem(10, items);
		
		PackingList<?> pl = KnapsackCombo.getPackingList(problem, new Evaluator(Integer.MAX_VALUE));
		assertEquals(Arrays.asList(false, true, true, true), pl.encode());
	}
	
	
	@Test
	public void testKNPScenarios() {
		for(String pathToFile : SCENARIOS) {
			KnapsackProblem problem = new KnapsackProblemReader().read(pathToFile);
			NonDominatedSolutionSet set = new KnapsackCombo().run(problem, new Evaluator(Integer.MAX_VALUE), new MyRandom());
			assertEquals(problem.getOptimum().get(0).getObjectives(0), set.get(0).getObjectives(0), 0.01);
		}
	}
	
	@Test
	public void testLargeScaleProblem() {
		ThiefProblem problem = new BonyadiSingleObjectiveReader().read("resources/100_150_7_25.txt");
		KnapsackProblem knp = new KnapsackProblem(problem.getMaxWeight(), problem.getItems());
		new KnapsackCombo().run(knp, new Evaluator(Integer.MAX_VALUE),  new MyRandom());
	}
	

}
