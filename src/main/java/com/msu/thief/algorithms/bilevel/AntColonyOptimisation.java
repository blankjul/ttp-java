package com.msu.thief.algorithms.bilevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.util.MyRandom;
import com.msu.util.Pair;
import com.msu.util.Range;

public class AntColonyOptimisation extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {

	protected double evaporationRate = 0.80;

	protected double alpha = 0.5;

	protected double beta = 0.5;

	protected int batchSize = 100;

	public Double[] heuristic;

	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {

		Range<Double> range = new Range<>();
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		PackingList<?> emptyList = new EmptyPackingListFactory().next(problem, rand);
		Solution empty = evaluator.evaluate(problem, emptyList);
		set.add(empty);

		heuristic = calcItemHeuristic(problem, evaluator, rand);
		heuristic = normalize(heuristic);

		// in the beginning the best is the empty one
		Solution best = empty;

		range.add(best.getObjective());

		// initialize pheromone matrix with 0's
		Double[][] mPheromone = new Double[problem.numOfItems()][problem.numOfItems()];
		for (int i = 0; i < problem.numOfItems(); i++) {
			for (int j = 0; j < problem.numOfItems(); j++) {
				mPheromone[i][j] = 0.001;
			}
		}
		Double[] mStart = new Double[problem.numOfItems()];
		for (int j = 0; j < problem.numOfItems(); j++) {
			mStart[j]  = 0.001;
		}

		while (evaluator.hasNext()) {

			List<Pair<Solution, List<Integer>>> ants = new ArrayList<>();

			for (int a = 0; a < batchSize; a++) {

				if (!evaluator.hasNext()) break;
				
				List<Integer> packingIndex = new ArrayList<>();
				List<Boolean> packingPlan = new ArrayList<>(emptyList.encode());

				// hash with all further available nodes
				Set<Integer> hash = new HashSet<>();
				for (int i = 0; i < mStart.length; i++) {
					hash.add(i);
				}

				// virtual last node is first decision
				int currentIndex = selectNext(mStart, rand, hash);
				//int currentIndex = selectNext(heuristic, rand, hash);
				//int currentIndex = rand.nextInt(problem.numOfItems());

				packingIndex.add(currentIndex);
				hash.remove(currentIndex);

				// int currentIndex = rand.nextInt(problem.numOfItems());

				Solution currentSolution = null;
				Solution nextSolution = null;

				// create a new picking plan
				do {

					currentSolution = (currentSolution == null) ? empty : nextSolution;
					
					if (hash.isEmpty()) break;
					
					// first selection random, else look at pheromone matrix
					int nextIndex = selectNext(mPheromone[currentIndex], rand, hash);

					packingIndex.add(nextIndex);
					packingPlan.set(nextIndex, true);
					hash.remove(nextIndex);

					nextSolution = evaluator.evaluate(problem, new BooleanPackingList(packingPlan));

					// while not getting worse
				} while (nextSolution.getObjectives(0) < currentSolution.getObjectives(0));

				// System.out.println("------------------");

				// check if new best solution
				set.add(currentSolution);

				ants.add(Pair.create(currentSolution, packingIndex));

			}

			for (int i = 0; i < problem.numOfItems(); i++) {
				mStart[i] = evaporationRate * mStart[i];
				for (int j = 0; j < problem.numOfItems(); j++) {
					mPheromone[i][j] = evaporationRate * mPheromone[i][j];
				}
			}
			for (Pair<Solution, List<Integer>> pair : ants) {

				Solution currentSolution = pair.first;
				List<Integer> packingIndex = pair.second;

				range.add(currentSolution.getObjective());
				double fitness = 1 - currentSolution.normalize(range.get()).getObjectives(0);

				if (packingIndex.size() > 0)
					mStart[packingIndex.get(0)] += fitness;
				if (packingIndex.size() > 1) {
					for (int i = 0; i < packingIndex.size() - 1; i++) {
						mPheromone[packingIndex.get(i)][packingIndex.get(i + 1)] += fitness;
					}
				}
				
				

				
				//System.out.println(Arrays.toString(packingIndex.toArray()));

			}
/*			
			for (int i = 0; i < problem.numOfItems(); i++) {
				if (mStart[i] < 0.001) System.out.print("0 ");
				else System.out.print(mStart[i] + " ");
			}
			*/
	
			/*
			 * 
			 * System.out.println(Arrays.toString(mStart)); for (int i = 0; i <
			 * problem.numOfItems(); i++) {
			 * System.out.println(Arrays.toString(mPheromone[i])); }
			 * System.out.println("--------------");
			 */
		}

/*		
		for (int i = 0; i < problem.numOfItems(); i++) {
			if (mStart[i] < 0.001) System.out.print("0 ");
			else System.out.print(mStart[i] + " ");
		}
		for (int i = 0; i < problem.numOfItems(); i++) {
			for (int j = 0; j < problem.numOfItems(); j++) {
				if (mPheromone[i][j] < 0.001) System.out.print("0 ");
				else System.out.print(mPheromone[i][j] + " ");
			}
			System.out.println();
		}
		
		
		System.out.println("--------------------------------");
		*/
		return set.get(0);
	}

	private int selectNext(Double[] mPheromone, MyRandom rand, Set<Integer> hash) {

		List<Integer> nodes = new ArrayList<>(hash);

		double[] v = new double[nodes.size()];

		for (int i = 0; i < nodes.size(); i++) {
			int idx = nodes.get(i);
			v[i] = Math.pow(mPheromone[idx], alpha) * Math.pow(heuristic[idx], beta);
		}

		double sum = 0;
		for (int i = 0; i < nodes.size(); i++) {
			sum += v[i];
		}

		for (int i = 0; i < nodes.size(); i++) {
			v[i] /= sum;
		}

		double[] acc = new double[nodes.size()];
		acc[0] = v[0];
		for (int i = 1; i < nodes.size(); i++) {
			acc[i] = acc[i - 1] + v[i];
		}
		acc[acc.length - 1] = 1;

		double r = rand.nextDouble();
		int index = 0;
		while (index < acc.length && r > acc[index]) {
			++index;
		}

		return nodes.get(index);

	}

	public Double[] calcItemHeuristic(ThiefProblemWithFixedTour p, IEvaluator evaluator, MyRandom rand) {

		Solution empty = evaluator.evaluate(p, new EmptyPackingListFactory().next(p, rand));
		
		Double[] h = new Double[p.numOfItems()];

		for (int i = 0; i < p.numOfItems(); i++) {
			List<Boolean> b = new ArrayList<Boolean>();
			for (int j = 0; j < p.numOfItems(); j++) {
				b.add(false);
			}
			b.set(i, true);
			
			Solution sol = evaluator.evaluate(p, new BooleanPackingList(b));
			
			h[i] = empty.getObjectives(0) - sol.getObjectives(0);
			
/*
			p.setToMultiObjective(true);
			Solution empty = evaluator.evaluate(p, new TTPVariable(t, new BooleanPackingList(b)));
			h[i] = -empty.getObjectives(1) / empty.getObjectives(0);
			p.setToMultiObjective(false);*/

		}

		return h;
	}

	private Double[] normalize(Double[] array) {

		Double min = Collections.min(Arrays.asList(array));
		Double max = Collections.max(Arrays.asList(array));

		Double[] result = new Double[array.length];

		for (int j = 0; j < array.length; j++) {

			// if (min == max) throw new RuntimeException("Error when
			// normalizing: Min is equal to Max!");
			if (min == max)
				min = min - 0.0001;

			double value = array[j];
			result[j] = (value - min) / (max - min);

			// even if max or min are not fitting get the values in range
			if (result[j] > 1)
				result[j] = 1d;
			if (result[j] < 0)
				result[j] = 0d;

		}
		return result;

	}

}
