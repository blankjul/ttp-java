package com.msu.thief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.algorithms.recombinations.TTPNeighbourSwapMutation;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class SwapTester {

	public static List<Integer> startingTour = Arrays.asList(0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12,
			46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29, 1,
			6, 41, 20, 16, 2, 17, 30, 21);
	public static List<Boolean> startingPacking = new IntegerSetPackingList(
			new HashSet<>(Arrays.asList(0, 1, 2, 3, 42, 15, 16, 48, 17, 49, 18, 50, 19, 20, 29)), 51).encode();

	public static void main(String[] args) {

		BasicConfigurator.configure();

		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

		MyRandom rand = new MyRandom(123456);
		ItemCollection<Item> c = thief.getItemCollection();

		TTPVariable var = new TTPVariable(new StandardTour(startingTour), new BooleanPackingList(startingPacking));
		Solution best = thief.evaluate(var);
		
		IEvaluator eval = new Evaluator(Integer.MAX_VALUE);
		
		while (true) {

			
			// randomly select 2 cities
			int firstIdx = rand.select(Util.createIndex(1, thief.numOfCities()-1));
			List<Integer> neighbours = TTPNeighbourSwapMutation.getNeighboursOfCity(thief.getMap(), firstIdx, 5);
			int other = rand.select(neighbours);
			int secondIdx = var.getTour().encode().indexOf(other);

			
			// swap the cities
			List<Integer> nextTour = new ArrayList<>(var.getTour().encode());
			Util.swap(nextTour, firstIdx, secondIdx);

			// for every combinations caused by this swap - 2 ^ (2 * m)
			Set<Integer> items = new HashSet<>();
			items.addAll(c.getItemsFromCityByIndex(nextTour.get(firstIdx)));
			items.addAll(c.getItemsFromCityByIndex(nextTour.get(secondIdx)));

			// best solution without cities to investigate
			Set<Integer> currentPacking = var.getPackingList().toIndexSet();
			currentPacking.removeAll(items);

			
			Set<Set<Integer>> power = powerSet(items);
			
			for (Set<Integer> n : power) {

				Set<Integer> nextPacking = new HashSet<>(currentPacking);
				nextPacking.addAll(n);

				TTPVariable nextVariable = new TTPVariable(new StandardTour(nextTour),
						new BooleanPackingList(nextPacking, thief.numOfItems()));
				
				//Solution next = thief.evaluate(nextVariable);
				
				Solution next = new OnePlusOneEAFixedTour().run___(new ThiefProblemWithFixedTour(thief, new StandardTour(nextTour)), new Evaluator(1000), rand);
				
				System.out.println(best);
				System.out.println(next);
				System.out.println(String.format("%s -> swap(%s,%s) -> pick(%s)", next.getObjectives(0), firstIdx, secondIdx, n.toString()));
				System.out.println("----------------------------");
				
				if (new SolutionDominatorWithConstraints().isDominating(next, best)) {
					best = next;
					var = nextVariable;
					System.out.println(best);
				}

			}

		}

	}

	public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
		Set<Set<T>> sets = new HashSet<Set<T>>();
		if (originalSet.isEmpty()) {
			sets.add(new HashSet<T>());
			return sets;
		}
		List<T> list = new ArrayList<T>(originalSet);
		T head = list.get(0);
		Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
		for (Set<T> set : powerSet(rest)) {
			Set<T> newSet = new HashSet<T>();
			newSet.add(head);
			newSet.addAll(set);
			sets.add(newSet);
			sets.add(set);
		}
		return sets;
	}

}
