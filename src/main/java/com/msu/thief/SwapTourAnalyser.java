package com.msu.thief;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IVariable;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.algorithms.bilevel.tour.BiLevelEvoluationaryAlgorithm;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.util.FileCollectorParser;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class SwapTourAnalyser {

	final public static String PROBLEM = "../ttp-benchmark/SingleObjective/10/10_5_6_25.txt";

	public static void sortBySingleObjective(SolutionSet set) {
		set.sort(new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				int constraint = Double.compare(o1.getMaxConstraintViolation(), o2.getMaxConstraintViolation());
				if (constraint != 0)
					return constraint;
				else {
					for (int i = 0; i < o1.countObjectives(); i++) {
						int value = Double.compare(o1.getObjectives(i), o2.getObjectives(i));
						if (value != 0)
							return value;
					}
					return 0;
				}
			}
		});
	}

	public static Solution perm(List<Integer> list, int i, int n, int numOfSwaps, int numOfMaxSwaps,
			BiFunction<List<Integer>, Solution, Solution> c, Solution best) {

		if (i == n || numOfSwaps == numOfMaxSwaps) {
			return c.apply(list, best);
		} else {
			SolutionSet solutions = new SolutionSet();
			for (int j = i; j < n; j++) {
				Util.swap(list, i, j);
				Solution s = perm(list, i + 1, n, numOfSwaps + 1, numOfMaxSwaps, c, best);
				Util.swap(list, i, j);
				solutions.add(s);
			}
			sortBySingleObjective(solutions);
			return solutions.get(0);
		}
	}

	public static Solution optimize(AbstractThiefProblem thief, IVariable var, int OPT) {

		TTPVariable v = (TTPVariable) var;

		List<Integer> l = v.getTour().encode();
		Solution start = thief.evaluate(new TTPVariable(v.getTour(), v.getPackingList()));
		double time = start.getObjectives(0);

		BiFunction<List<Integer>, Solution, Solution> func = new BiFunction<List<Integer>, Solution, Solution>() {

			@Override
			public Solution apply(List<Integer> copy, Solution currentBest) {

				Solution s = thief.evaluate(new TTPVariable(new StandardTour(copy), v.getPackingList()));
				double timeSwap = s.getObjectives(0);

				if (timeSwap < time)
					return s;
				else
					return currentBest;
			}
		};

		Solution s = perm(l, 1, l.size(), 0, OPT, func, start);
		return s;
	}

	
	public static void permCopy(Solution start, AbstractThiefProblem problem, List<Integer> list, int i, int n, int numOfSwaps, int numOfMaxSwaps) {
		
		if (i == n || numOfSwaps == numOfMaxSwaps) {
			
			Solution current = problem.evaluate(new TTPVariable(new StandardTour(list), ((TTPVariable)start.getVariable()).getPackingList()));
			
			if (current.getObjectives(0) < start.getObjectives(0)) {
				System.out.println(String.format("%s", current));
			}
		} else {
			for (int j = i; j < n; j++) {
				Util.swap(list, i, j);
				permCopy(start, problem, list, i + 1, n, numOfSwaps + 1, numOfMaxSwaps);
				Util.swap(list, i, j);
			}
		}
	}
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();

		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();

		fcp.add("../ttp-benchmark/SingleObjective/10", "10_5_6_25.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/10", "10_10_2_50.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/10", "10_15_10_75.txt", new BonyadiSingleObjectiveReader());

		fcp.add("../ttp-benchmark/SingleObjective/20", "20_5_6_75.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "20_20_7_50.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "20_30_9_25.txt", new BonyadiSingleObjectiveReader());

		fcp.add("../ttp-benchmark/SingleObjective/50", "50_15_8_50.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "50_25_3_75.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "50_75_6_25.txt", new BonyadiSingleObjectiveReader());

		fcp.add("../ttp-benchmark/SingleObjective/100", "100_5_10_50.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100", "100_50_5_75.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100", "100_150_10_25.txt", new BonyadiSingleObjectiveReader());

		List<AbstractThiefProblem> collected = fcp.collect();

		for (AbstractThiefProblem problem : collected) {

			Solution best = new BiLevelEvoluationaryAlgorithm().run__(problem, new Evaluator(500000),
					new MyRandom(123456));
			TTPVariable var = (TTPVariable) best.getVariable();

			System.out.println(String.format("Analyze: %s", best));

			final int OPT = 20;
			
			TTPVariable v = (TTPVariable) var;
			List<Integer> l = v.getTour().encode();


			permCopy(best, problem, l, 1, l.size(), 0, OPT);
			
			System.out.println("----------------------------------------------");
			
		}

	}

}
