package com.msu.thief.problems;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.msu.moo.algorithms.ExhaustiveSolver;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.model.Item;
import com.msu.thief.problems.knp.KnapsackExhaustiveFactory;
import com.msu.thief.problems.knp.KnapsackProblem;
import com.msu.thief.problems.knp.KnapsackVariable;

public class KnapsackExhaustiveSolverTest {

	
	
	@Test
	public void testCorrectResult() {
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			items.add(new Item(5, 4));
		}
		items.add(new Item(6, 3));
		KnapsackProblem knp = new KnapsackProblem(3, items);
		
		ExhaustiveSolver<KnapsackVariable, KnapsackProblem> solver = new ExhaustiveSolver<>(new KnapsackExhaustiveFactory());
		NonDominatedSolutionSet set = solver.run(knp);
		
		assertEquals(1, set.size());
		assertEquals(- 6, set.getSolutions().get(0).getObjectives().get(0), 0.01);
	}
	
}
