package com.msu.thief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.thief.algorithms.heuristic.TourHeuristic;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.algorithms.recombinations.TTPNeighbourSwapMutation;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.factory.RandomTourFactory;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class SwapTester {

	
	  public static List<Integer> startingTour = Arrays.asList(0, 48, 31, 44,
	  18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3,
	  5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22,
	  29, 1, 6, 41, 20, 16, 2, 17, 30, 21);
	 

	//public static List<Integer> startingTour = Arrays.asList(0, 8, 7, 11, 6, 2, 39, 46, 13, 32, 9, 19, 21, 48, 45, 26, 27, 42, 24, 28, 29, 20, 17, 43, 35, 38, 23, 36, 34, 31, 44, 14, 4, 22, 1, 41, 16, 30, 33, 25, 12, 51, 10, 50, 15, 49, 5, 3, 47, 37, 40, 18);

	public static List<Boolean> startingPacking = new IntegerSetPackingList(
			new HashSet<>(Arrays.asList(0, 1, 2, 3, 42, 15, 16, 48, 17, 49, 18, 50, 19, 20, 29)), 51).encode();

	
	
	
	
	
	public static void main(String[] args) {

		BasicConfigurator.configure();

		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

		MyRandom rand = new MyRandom(123456);

		Solution test = new OnePlusOneEAFixedTour().run___(
				new ThiefProblemWithFixedTour(thief, new StandardTour(startingTour)), new Evaluator(500000), rand);
		System.out.println(test);

		// startingTour = new TwoOptFactory().next(thief, rand).encode();

		TTPVariable var = new TTPVariable(new StandardTour(startingTour), new BooleanPackingList(startingPacking));
		Solution best = thief.evaluate(var);

		/*
		 * for (int i = 1; i < thief.numOfCities() - 1; i++) { for (int k = i +
		 * 2; k < thief.numOfCities() - 1; k++) {
		 * 
		 * List<Integer> nextTour = new ArrayList<>(var.getTour().encode());
		 * nextTour = TwoOptFactory.twoOptSwap(nextTour, i, k, thief.getMap(),
		 * 0).first;
		 * 
		 * 
		 * Solution next = new OnePlusOneEAFixedTour().run___( new
		 * ThiefProblemWithFixedTour(thief, new StandardTour(nextTour)), new
		 * Evaluator(50000), rand);
		 * 
		 * 
		 * if (new SolutionDominatorWithConstraints().isDominating(next, best))
		 * {
		 * 
		 * System.out.println(best); System.out.println(next);
		 * System.out.println(String.format("%s -> swap(%s,%s)",
		 * next.getObjectives(0), i, k));
		 * System.out.println("----------------------------");
		 * 
		 * best = next; var = new TTPVariable(new StandardTour(nextTour),
		 * (PackingList<?>) next.getVariable()); System.out.println(best); }
		 * 
		 * 
		 * } }
		 */
		
		StandardTour bestTour = (StandardTour) new RandomTourFactory().next(thief, rand);
		double bestHeuristic = TourHeuristic.calcHeuristic(thief, bestTour);

		
		while (true) {

			// randomly select 2 cities
			int firstIdx = rand.select(Util.createIndex(1, thief.numOfCities() - 1));
			List<Integer> neighbours = TTPNeighbourSwapMutation.getNeighboursOfCity(thief.getMap(), firstIdx, 5);
			int other = rand.select(neighbours);
			int secondIdx = var.getTour().encode().indexOf(other);

			// swap the cities
			List<Integer> tmp = new ArrayList<>(bestTour.encode());
			Util.swap(tmp, firstIdx, secondIdx);
			StandardTour nextTour = new StandardTour(tmp);

			double nextHeuristic  = TourHeuristic.calcHeuristic(thief, nextTour);
			
			if (nextHeuristic > bestHeuristic) {
				
				
				System.out.println(bestHeuristic);
				System.out.println(nextHeuristic);
				System.out.println(String.format("swap(%s,%s)", firstIdx, secondIdx));
				System.out.println("----------------------------");
				
				
				bestTour = nextTour;
				bestHeuristic = nextHeuristic;
				
				
				System.out.println(bestTour);
			}

		}

	}

}
