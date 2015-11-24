package com.msu.thief.algorithms.heuristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.model.Item;
import com.msu.thief.problems.KnapsackProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class HeuristicGreedyAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {


	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {

		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);
		
		Map<Integer, Double> mBestCaseDelta = new HashMap<>();
		Map<Integer, Double> mWorstCaseDelta = new HashMap<>();
		
		List<Item> items = problem.getItems();
		
		for (int i = 0; i < items.size(); i++) {
			double deltaProfit = items.get(i).getProfit();
			
			double deltaBestTime = HeuristicUtil.calcDeltaTime(problem, bestTour, i);
			
			double calcDeltaSpeed = HeuristicUtil.calcDeltaVelocity(problem, i);
			double deltaWorstTime = HeuristicUtil.calcDeltaTime(problem, bestTour, i, problem.getMinSpeed() + calcDeltaSpeed);
			
			List<Integer> pi = bestTour.encode();
			int tourIndexOfPick = pi.indexOf(problem.getItemCollection().getCityOfItem(i));
			double bestDelta = HeuristicUtil.calcDeltaSingleObjective(problem, deltaBestTime, deltaProfit);
			
			mBestCaseDelta.put(i, bestDelta / (problem.numOfCities() - tourIndexOfPick));
			
			mWorstCaseDelta.put(i, HeuristicUtil.calcDeltaSingleObjective(problem, deltaWorstTime, deltaProfit));
		}
		
		for (int i = 0; i < items.size(); i++) {
			System.out.println(String.format("%s %s %s", i, mWorstCaseDelta.get(i), mBestCaseDelta.get(i)));
		}
		
		
		
		
		List<Item> sub = new ArrayList<>();
		
		double min = Collections.min(mWorstCaseDelta.values());
		double max = Collections.max(mWorstCaseDelta.values());
		
		System.out.println(Collections.min(mBestCaseDelta.values()));
		
		for (int i = 0; i < items.size(); i++) {
			//double profit = 10000 - ((mWorstCaseDelta.get(i) - min) /  (max - min)) * 10000;
			double profit = (mBestCaseDelta.get(i) < 0) ? - mBestCaseDelta.get(i) : 0;
			System.out.println(String.format("%s %s", i, profit));
			sub.add(new Item(profit, items.get(i).getWeight()));
		}
		
		PackingList<?> b = AlgorithmUtil.calcBestPackingPlan(new KnapsackProblem(problem.getMaxWeight(), sub));
		
		System.out.println(Arrays.toString(b.toIndexSet().toArray()));
		
		return evaluator.evaluate(problem, new TTPVariable(bestTour, b));
	}




	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		BooleanPackingList bpl = new BooleanPackingList("[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0]");
		
		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
		HeuristicGreedyAlgorithm heuristic = new HeuristicGreedyAlgorithm();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(10000), new MyRandom());
		
		System.out.println(set);
	}

}
