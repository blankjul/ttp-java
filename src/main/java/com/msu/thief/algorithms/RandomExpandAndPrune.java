package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class RandomExpandAndPrune extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	// ! problem for this run
	protected SingleObjectiveThiefProblem problem;

	// ! random generator
	protected MyRandom rand;

	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {
		// initialize the variables
		this.problem = problem;
		this.rand = rand;
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		// calculate best tours
		Tour<?> tour = AlgorithmUtil.calcBestTour(problem).getSymmetric();
		Tour<?> symmetricTour = tour.getSymmetric();

		Evaluator eval1 = new Evaluator(eval.getMaxEvaluations() / 2);
		Evaluator eval2 = new Evaluator(eval.getMaxEvaluations() / 2);

		while (eval1.hasNext())
			solve(eval1, tour, set);
		while (eval2.hasNext())
			solve(eval2, symmetricTour, set);

		return set.get(0);
	}

	private void solve(IEvaluator eval, Tour<?> tour, NonDominatedSolutionSet set) {

		Solution current = eval.evaluate(problem,
				new TTPVariable(tour, new EmptyPackingListFactory().next(problem, rand)));
		set.add(current);

		while (eval.hasNext()) {
			
			Set<Integer> items = ((TTPVariable) current.getVariable()).getPackingList().toIndexSet();

			List<Solution> next = new ArrayList<>();
			for (int idx = 0; idx < problem.numOfItems(); idx++) {
				if (items.contains(idx))
					continue;
				BooleanPackingList b = new BooleanPackingList(items, problem.numOfItems());
				b.get().set(idx, true);
				Solution s = eval.evaluate(problem, new TTPVariable(tour, b));
				if (s.getObjectives(0) < current.getObjectives(0)) next.add(s);
			}
			
			if (next.isEmpty()) break;
			
			current = rand.select(next);
			
			

			//System.out.println(Arrays.toString(((TTPVariable) current.getVariable()).getPackingList().toIndexSet().toArray()));

		}


	}

	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				// .read("../ttp-benchmark/SingleObjective/10/10_5_6_25.txt");
				// .read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
				// .read("../ttp-benchmark/SingleObjective/10/10_15_10_75.txt");
				// .read("../ttp-benchmark/SingleObjective/20/20_5_6_75.txt");
				// .read("../ttp-benchmark/SingleObjective/20/20_20_7_50.txt");
				.read("../ttp-benchmark/SingleObjective/20/20_30_9_25.txt");
		// .read("../ttp-benchmark/SingleObjective/50/50_15_8_50.txt");
		// .read("../ttp-benchmark/SingleObjective/100/100_5_10_50.txt");

		RandomExpandAndPrune heuristic = new RandomExpandAndPrune();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(500000), new MyRandom(123456));

		System.out.println(p);
		System.out.println(set.size());
		System.out.println(set);
		System.out.println(
				Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

	}

}
