package com.msu.thief.algorithms.divide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.heuristic.HeuristicUtil;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class DivideAndConquerAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	protected List<Integer> shuffle;
	
	
	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {

		Tour<?> tour = AlgorithmUtil.calcBestTour(problem);
		
		shuffle = new ArrayList<>();
		for (int i = 0; i < problem.numOfItems(); i++) {
			shuffle.add(i);
		}
		rand.shuffle(shuffle);
		
		
		Set<Integer> indices = solve(problem, eval, tour, 0, problem.numOfItems() - 1, 0);

		
		//Set<Integer> indices = new HashSet<>();
		//DivideAndConquerUtil.packGreedy(problem, eval, tour, indices);
		
		Solution s = eval.evaluate(problem,
				new TTPVariable(tour, new BooleanPackingList(indices, problem.numOfItems())));

		//DivideAndConquerUtil.reportFinalState(problem, eval, tour, indices);

		return s;
	}
	
	

	protected Set<Integer> solve(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour, int start,
			int end, int level) {


		if (start == end) {
			return new HashSet<>(Arrays.asList(shuffle.get(start)));
		}

		int middle = start + (end - start) / 2;
		Set<Integer> left = solve(problem, eval, tour, start, middle, level +1);
		Set<Integer> right = solve(problem, eval, tour, middle + 1, end, level + 1);
		
		Pair<Set<Integer>, Double> mergedA = merge(problem, eval, tour, left, right);
		Pair<Set<Integer>, Double> mergedB = merge(problem, eval, tour, right, left);
		
		Pair<Set<Integer>, Double> merged = (mergedA.second < mergedB.second) ? mergedA : mergedB;
		
		for (int i = 0; i < level; i++) {
			System.out.print("----");
		}
		System.out.println(String.format("%s", Arrays.toString(merged.first.toArray())));
		
		//DivideAndConquerUtil.pruneUntilNoImprovement(problem, eval, tour, merged);

		return merged.first;
	}

	
	protected Pair<Set<Integer>, Double> merge(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour,
			Set<Integer> left, Set<Integer> right) {
		Set<Integer> merged = new HashSet<>(left);
		double best = eval
				.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(merged, problem.numOfItems())))
				.getObjectives(0);

		for (Integer next : right) {
			merged.add(next);
			double fitnessMerged = eval
					.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(merged, problem.numOfItems())))
					.getObjectives(0);
			if (best <= fitnessMerged)
				merged.remove(next);
			else
				best = fitnessMerged;
		}
		return Pair.create(merged, best);
	}
	

	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
		DivideAndConquerAlgorithm heuristic = new DivideAndConquerAlgorithm();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(500000), new MyRandom(123456));

		System.out.println(set);
		System.out.println(
				Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

		BooleanPackingList bpl = new BooleanPackingList(
				"[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0]");

		Tour<?> bestTour = AlgorithmUtil.calcBestTour(p);
		System.out.println(p.evaluate(new TTPVariable(bestTour, bpl)));
		System.out.println(Arrays.toString(bpl.toIndexSet().toArray()));
		for (Integer idx : bpl.toIndexSet()) {
			List<Double> deltaObj = HeuristicUtil.calcDeltaObjectives(p, new Evaluator(Integer.MAX_VALUE), bestTour,
					bpl, idx);
			System.out.println(String.format("%s %s", idx, deltaObj.get(0)));
		}
	}

}
