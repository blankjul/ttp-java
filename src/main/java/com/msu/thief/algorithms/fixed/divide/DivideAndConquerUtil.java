package com.msu.thief.algorithms.fixed.divide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.util.ThiefUtil;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.tour.Tour;

public class DivideAndConquerUtil {

	
	public static int calcHowLongItemCarried(SingleObjectiveThiefProblem problem, Tour<?> tour, Integer itemIdx) {
		
		ItemCollection<Item> c = problem.getItemCollection();
		
		List<Integer> pi = tour.encode();
		int i = 0;
		for (; i < pi.size(); i++) {
			if (c.isItemAtCity(i, itemIdx)) break;
		}
		int time = 0;
		for (; i < pi.size(); i++) {
			time += problem.getMap().get(pi.get(i), pi.get((i + 1) % pi.size()) );
		}
		return time;
		
	}
	
	public static boolean addNextBest(SingleObjectiveThiefProblem problem, IEvaluator eval,
			Tour<?> tour, Set<Integer> set) {
		double current = eval.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(set, problem.numOfItems())))
				.getObjectives(0);
		Map<Integer, Double> next = calcObjectiveWhenAdded(problem, eval, tour, set);
		if ( next.entrySet().iterator().hasNext()) {
			Entry<Integer, Double> entry = next.entrySet().iterator().next();
			if (entry.getValue() < current) {
				set.add(entry.getKey());
				return true;
			}
		}
		return false;
	}
	
	
	public static void packGreedy(SingleObjectiveThiefProblem problem, IEvaluator eval,
			Tour<?> tour, Set<Integer> set) {
		while(addNextBest(problem, eval, tour, set));
	}
	
	
	
	
	public static Map<Integer, Double> calcObjectiveWhenAdded(SingleObjectiveThiefProblem problem, IEvaluator eval,
			Tour<?> tour, Set<Integer> set) {
		
		// hash map with the final values
		Map<Integer, Double> hash = new HashMap<>();

		for (int idx = 0; idx < problem.numOfItems(); idx++) {
				// you can't add an item which is included
				if (set.contains(idx)) continue;
				BooleanPackingList b = new BooleanPackingList(set, problem.numOfItems());
				b.get().set(idx, true);
				double fitness = eval.evaluate(problem, new TTPVariable(tour, b)).getObjectives(0);
				hash.put(idx, fitness);
			
		}
		return ThiefUtil.sortByValue(hash);
	}

	
	
	public static Map<Integer, Double> calcObjectiveWhenRemoved(SingleObjectiveThiefProblem problem, IEvaluator eval,
			Tour<?> tour, Set<Integer> set) {
		Map<Integer, Double> hash = new HashMap<>();
		for (Integer idx : set) {
			BooleanPackingList b = new BooleanPackingList(set, problem.numOfItems());
			b.get().set(idx, false);
			double fitness = eval.evaluate(problem, new TTPVariable(tour, b)).getObjectives(0);
			hash.put(idx, fitness);
		}
		return ThiefUtil.sortByValue(hash);
	}

	
	
	/**
	 * prunes a given set of indices until no improvement is made
	 */
	public static void pruneUntilNoImprovement(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour,
			Set<Integer> set) {
		Integer idx = 0;
		while (!set.isEmpty() && idx != -1) {
			idx = DivideAndConquerUtil.prune(problem, eval, tour, set);
		}
	}

	/**
	 * @return the index of the pruned item or -1 otherwise.
	 */
	public static Integer prune(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour, Set<Integer> set) {

		double best = eval.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(set, problem.numOfItems())))
				.getObjectives(0);

		List<Double> l = new ArrayList<>();
		List<Integer> indices = new ArrayList<>(set);

		for (Integer idx : indices) {
			BooleanPackingList b = new BooleanPackingList(set, problem.numOfItems());
			b.get().set(idx, false);
			double fitness = eval.evaluate(problem, new TTPVariable(tour, b)).getObjectives(0);
			l.add(fitness - best);
		}
		double min = Collections.min(l);
		if (min < 0) {
			int idx = indices.get(l.indexOf(min));
			set.remove(idx);
			return idx;
		}
		return -1;
	}

	public static void reportFinalState(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour,
			Set<Integer> indices) {

		double best = eval
				.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(indices, problem.numOfItems())))
				.getObjectives(0);

		System.out.println("--------------------");
		System.out.println("Packed items");
		System.out.println("--------------------");

		System.out.println(String.format("%s - %s", best, Arrays.toString(indices.toArray())));
		

		System.out.println("--------------------");
		System.out.println("If remove Item i");
		System.out.println("--------------------");
		
		
		for (Entry<Integer, Double> entry : calcObjectiveWhenRemoved(problem, eval, tour, indices).entrySet()) {
			System.out.println(String.format("%s %s", entry.getKey(), entry.getValue()));
		}

		System.out.println("--------------------");
		System.out.println("If add Item i");
		System.out.println("--------------------");
		for (Entry<Integer, Double> entry : calcObjectiveWhenAdded(problem, eval, tour, indices).entrySet()) {
			System.out.println(String.format("%s %s", entry.getKey(), entry.getValue()));
		}

		System.out.println("--------------------");

	}

}
