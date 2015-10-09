package com.msu.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.io.reader.KnapsackProblemReader;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.problems.KnapsackProblem;
import com.msu.thief.model.Item;
import com.msu.thief.variable.pack.PackingList;

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
		
		PackingList<?> pl = KnapsackCombo.getPackingList(new Evaluator(problem));
		assertEquals(Arrays.asList(false, true, true, true), pl.encode());
	}
	
	
	@Test
	public void testKNPScenarios() {
		for(String pathToFile : SCENARIOS) {
			KnapsackProblem problem = new KnapsackProblemReader().read(pathToFile);
			NonDominatedSolutionSet set = new KnapsackCombo().run(new Evaluator(problem));
			assertEquals(-problem.getOptimum().get(0).getObjectives(0), set.get(0).getObjectives(0), 0.01);
		}
	}
	

}
