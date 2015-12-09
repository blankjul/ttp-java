package com.msu.thief.algorithms.divide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.KnapsackCombo;
import com.msu.thief.algorithms.util.HeuristicUtil;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.model.Item;
import com.msu.thief.problems.KnapsackProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class DeltaItemHeuristicAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {

		List<Tour<?>> tours = new ArrayList<>();
		Tour<?> best = AlgorithmUtil.calcBestTour(problem);
		tours.add(best);
		//tours.add(best.getSymmetric());

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		for (Tour<?> t : tours) {

			double empty = eval
					.evaluate(problem,
							new TTPVariable(t, new BooleanPackingList(new HashSet<>(), problem.numOfItems())))
					.getObjectives(0);

			List<Item> items = new ArrayList<>();
			Map<Integer, Double> next = DivideAndConquerUtil.calcObjectiveWhenAdded(problem, eval, t,
					new HashSet<>());

			for (int i = 0; i < problem.numOfItems(); i++) {
				double deltaSingleObj = next.get(i);
				double weight = problem.getItems().get(i).getWeight();
				
				double weightWithItem = DivideAndConquerUtil.calcHowLongItemCarried(problem, t, i);
				
				double heuristic = (empty - deltaSingleObj);
				//if (heuristic < 0) heuristic = 0;
		
				System.out.println(String.format("%s %s %s %s", i, heuristic, weight, weightWithItem));
				
				Item item = new Item(heuristic, weight);
				items.add(item);
			}

			KnapsackProblem knp = new KnapsackProblem(problem.getMaxWeight(), items);
			Solution solKnp = new KnapsackCombo().run(knp, eval, rand).get(0);

			Set<Integer> b = ((PackingList<?>) solKnp.getVariable()).toIndexSet();

			DivideAndConquerUtil.pruneUntilNoImprovement(problem, eval, t, b);

			Solution s = eval.evaluate(problem, new TTPVariable(t, new BooleanPackingList(b, problem.numOfItems())));
			set.add(s);
		}
		
		
		//TTPVariable var = (TTPVariable) set.get(0).getVariable();
		//DivideAndConquerUtil.reportFinalState(problem, eval, var.getTour(), var.getPackingList().toIndexSet());
		
		return set.get(0);
	}
	

	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
		DeltaItemHeuristicAlgorithm heuristic = new DeltaItemHeuristicAlgorithm();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(500000), new MyRandom(123456));

		System.out.println(set);
		System.out.println(
				Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

		BooleanPackingList bpl = new BooleanPackingList(new HashSet<Integer>(Arrays.asList(32, 64, 35, 36, 37, 22, 23, 39, 29, 14)), p.numOfItems());
		
		
		Tour<?> bestTour = AlgorithmUtil.calcBestTour(p);
		for (Integer idx : bpl.toIndexSet()) {
			List<Double> deltaObj = HeuristicUtil.calcDeltaObjectives(p, new Evaluator(Integer.MAX_VALUE), bestTour,
					bpl, idx);
			System.out.println(String.format("%s %s", idx, deltaObj.get(0)));
		}
		System.out.println(p.evaluate(new TTPVariable(bestTour, bpl)));
		System.out.println(Arrays.toString(bpl.toIndexSet().toArray()));
	}

}
