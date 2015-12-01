package com.msu.thief.algorithms.heuristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.model.Item;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class HeuristicAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {

		Tour<?> pi = AlgorithmUtil.calcBestTour(problem);

		List<Tour<?>> tours = new ArrayList<>();
		tours.add(pi);
		tours.add(pi.getSymmetric());

		/*
		 * for (int i = 0; i < 8; i++) { tours.add(new
		 * TwoOptFactory().next(problem, rand)); }
		 */
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		for (Tour<?> tour : tours) {
/*
			for (int i = 0; i < 4; i++) {

				// PackingList<?> b = new
				// BooleanPackingList(Util.createListWithDefault(problem.numOfItems(),true));

				PackingList<?> b = new FullPackingListFactory().next(problem, rand);

				while (prunePackingPlan(problem, evaluator, tour, b)) {
				}
				set.add(evaluator.evaluate(problem, new TTPVariable(tour, b)));
			}*/
			
			
			set.add(addItemsGreedy(problem, evaluator, tour));
		}
		return set.get(0);
	}

	protected boolean prunePackingPlan(SingleObjectiveThiefProblem problem, IEvaluator evaluator, Tour<?> pi,
			PackingList<?> b) {

		boolean pruned = false;

		// remove all items which are not bringing any profit
		while (!b.toIndexSet().isEmpty()) {
			List<Double> lDeltaRemove = new ArrayList<>();

			for (Integer idxPacked : b.toIndexSet()) {
				double delta = HeuristicUtil
						.calcDeltaObjectives(problem, new Evaluator(Integer.MAX_VALUE), pi, b, idxPacked).get(0);
				lDeltaRemove.add(delta);
			}

			double bestDelta = Collections.min(lDeltaRemove);

	/*		if (bestDelta >= 0)
				break;
			else {
				int idx = b.toIndexSet().get(lDeltaRemove.indexOf(bestDelta));
				b.get().set(idx, false);
				pruned = true;
			}*/
		}
		return pruned;
	}

	protected Solution addItemsGreedy(SingleObjectiveThiefProblem problem, IEvaluator evaluator, Tour<?> pi) {

		PackingList<?> b = new EmptyPackingListFactory().next(problem, null);
		List<Item> items = problem.getItems();
		Set<Integer> blacklist = new HashSet<>();

		while (blacklist.size() < problem.numOfItems()) {

			// add the next best item which seems to be good
			List<Double> lDelta = new ArrayList<>();
			for (int i = 0; i < items.size(); i++) {

				if (blacklist.contains(i))
					lDelta.add(Double.MAX_VALUE);
				else {
					double delta = HeuristicUtil
							.calcDeltaObjectives(problem, new Evaluator(Integer.MAX_VALUE), pi, b, i).get(0);
					
					lDelta.add(delta);
					if (delta > 0)
						blacklist.add(i);
				}
			}
			double bestDelta = Collections.min(lDelta);

			if (bestDelta >= 0)
				break;
			else {
				int idx = lDelta.indexOf(bestDelta);
				b.get().set(idx, true);
				blacklist.add(idx);
			}

		}
		return evaluator.evaluate(problem, new TTPVariable(pi, b));
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();

		
		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
		HeuristicAlgorithm heuristic = new HeuristicAlgorithm();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(50000), new MyRandom());

		System.out.println(set);
		System.out.println(Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

		
		BooleanPackingList bpl = new BooleanPackingList(
				"[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0]");

		Tour<?> bestTour = AlgorithmUtil.calcBestTour(p);
		System.out.println(p.evaluate(new TTPVariable(bestTour, bpl)));
		System.out.println(Arrays.toString(bpl.toIndexSet().toArray()));
		for (Integer idx : bpl.toIndexSet()) {
			List<Double> deltaObj = HeuristicUtil.calcDeltaObjectives(p, new Evaluator(Integer.MAX_VALUE), bestTour, bpl,  idx);
			System.out.println(String.format("%s %s", idx, deltaObj.get(0)));
		}
	}

}
