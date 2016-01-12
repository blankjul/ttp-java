package com.msu.thief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.thief.algorithms.BestSoFarAlgorithm;
import com.msu.thief.algorithms.bilevel.tour.SolveKnapsackWithHeuristicValues;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.algorithms.recombinations.InverseSwapMutation;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class ThiefHillClimbing {

	public static StandardTour startingTour = new StandardTour(Arrays.asList(0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29,  1,  6, 41, 20, 16,  2, 17, 30, 21));

	public static IntegerSetPackingList startingPacking = new IntegerSetPackingList(
			new HashSet<>(Arrays.asList(0, 1, 2, 3, 42, 15, 16, 48, 17, 49, 18, 50, 19, 20, 29)), 51);

	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

		System.out.println(new BestSoFarAlgorithm().run__(thief, new Evaluator(500000), new MyRandom(123456)));
		
		/*SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_5_6_25.txt");
		*/
		
		MyRandom rand = new MyRandom(123456);
		ItemCollection<Item> c = thief.getItemCollection();

		Solution best = thief.evaluate(new TTPVariable(startingTour, startingPacking));
		System.out.println(best);
		
		Tour<?> currentTour = startingTour;
		
		
/*		
		Builder<ThiefSingleObjectiveEvolutionaryAlgorithm> eaPop = new Builder<>(
				ThiefSingleObjectiveEvolutionaryAlgorithm.class);
		eaPop
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover",new OrderedCrossover<>())
				.set("mutation", new TTPNeighbourSwapMutation());

		Solution s = eaPop.build().run__(thief, new Evaluator(500000), rand);

		System.out.println(s);
		
		if (true) return;*/

/*		while (true) {
			
			final int i = rand.nextInt(1, thief.numOfCities()-1);
			final int j= rand.nextInt(1, thief.numOfCities()-1);
			
			
			TTPVariable var = (TTPVariable) best.getVariable();
			
			List<Integer> nextList = new ArrayList<>(var.getTour().encode());
			Tour<?> nextTour = new StandardTour(new InverseSwapMutation().mutate_(nextList, thief, rand, null, i, j));
			
			PackingList<?> list = new BooleanPackingList(var.getPackingList().encode());
			
			Solution next = new OnePlusOneEAFixedTour().run___(new ThiefProblemWithFixedTour(thief, nextTour), new Evaluator(5000), rand);
			
			
			if (new SolutionDominatorWithConstraints().isDominating(next, best)) {
				best = thief.evaluate(new TTPVariable(nextTour, (PackingList<?>) next.getVariable()));
				System.out.print("-> ");
				System.out.println(next);
			}
			
		}
		*/
		
		//StandardTour tmp = new StandardTour(Arrays.asList(0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 39, 38, 36, 45, 15, 28, 29, 1, 6, 41, 20, 16, 2, 17, 30, 22, 19, 34, 43, 33, 35, 49, 21));
		
		
		PackingList<?> currentPack = new BooleanPackingList(new HashSet<>(startingPacking.get().first), thief.numOfItems());

		boolean improvement = true;
		
		while (improvement) {
		
			improvement = false;
			
			outer:
			for (int i = thief.numOfCities() - 2; i > 0; i--) {

				for (int k = i - 1; k > 0; k--) {

					// create the new tour
					List<Integer> nextList = new ArrayList<>(currentTour.encode());
					Tour<?> nextTour = new StandardTour(new InverseSwapMutation().mutate_(nextList, thief, rand, null, k, i));
					
					/*
					Solution next = new OnePlusOneEAFixedTour().run___(
							new ThiefProblemWithFixedTour(thief, nextTour), new Evaluator(5000), rand);
					*/
					
					Solution next = new SolveKnapsackWithHeuristicValues().run__(
							new ThiefProblemWithFixedTour(thief, nextTour), new Evaluator(5000), rand);
							
							
					if (new SolutionDominatorWithConstraints().isDominating(next, best)) {
						best = next;
						currentTour = nextTour;
						currentPack = (PackingList<?>) next.getVariable();
						System.out.print("-> ");
						System.out.println(String.format("%s %s swap %s", i, k, next));
						improvement = true;
						break outer;
					} 
					
				} 
			}
		}
		
		Solution next = new OnePlusOneEAFixedTour().run___(
				new ThiefProblemWithFixedTour(thief, currentTour), new Evaluator(50000), rand);
		
		System.out.println(next);
					
					
/*					double improve = nextTime - time;
					if (improve < bestImprove) {
						
						bestImprove = improve;
						currentTour = nextTour;
						
					}
					
					
					
					double uLoss = thief.getR() * (nextTime - time);
					
					double sum = 0;

					for (Integer idx : c.getItemsFromCityByIndex(i)) {

						PackingList<?> pack = new IntegerSetPackingList(idx, thief.numOfItems());

						// calculate objective to the next tour
						//Solution obj = thief.evaluate(new TTPVariable(currentTour, pack));
						Solution next = thief.evaluate(new TTPVariable(nextTour, pack));

						//if (new SolutionDominatorWithConstraints().isDominating(obj, next))
						sum += next.getObjectives(0);
						//sum += thief.getItem(idx).getProfit() / thief.getItem(idx).getWeight();

					}

					double improve = sum - uLoss;
					
					System.out.println(String.format("%s %s -> %f %f %f", i, k, uLoss, sum, improve));
					if (bestImprove < improve) {
						bestI = i;
						bestK = k;
						bestImprove = improve;
					}
					
				

				}
				*/
			//if (bestImprove <= 0) break;
			
			
			
			//Solution next = new OnePlusOneEAFixedTour().run___(
					//new ThiefProblemWithFixedTour(thief, currentTour), new Evaluator(50000), rand);
			//System.out.println(next);
			
		
			
			
			// create the new tour
			
				/*List<Integer> next = currentTour.encode();
			Util.swap(next, bestI, bestK);
			currentTour = new StandardTour(next);
			
			
			System.out.println(currentTour);
			System.out.println("-------------------------------");*/

			
		}

	

}
