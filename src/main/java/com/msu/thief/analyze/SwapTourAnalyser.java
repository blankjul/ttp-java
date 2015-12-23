package com.msu.thief.analyze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.RandomPackingListFactory;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.factory.RandomTourFactory;
import com.msu.util.FileCollectorParser;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class SwapTourAnalyser {

	final public static String PROBLEM = "../ttp-benchmark/SingleObjective/10/10_5_6_25.txt";



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
			SingleObjectiveEvolutionaryAlgorithm.sortBySingleObjective(solutions);
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

	
	public static void permCopy(PrintWriter pw, Solution start, AbstractThiefProblem problem, List<Integer> list, int i, int n, int numOfSwaps, int numOfMaxSwaps) {
		
		if (i == n) {
			return;
		} else {
			for (int j = i; j < n; j++) {
				
				Util.swap(list, i, j);
				
				if (i!=j) {
					Solution current = problem.evaluate(new TTPVariable(new StandardTour(list), ((TTPVariable)start.getVariable()).getPackingList()));
					double value = (current.getObjectives(0) - start.getObjectives(0)) / start.getObjectives(0);
					
					if (!tmp.containsKey(numOfSwaps)) tmp.put(numOfSwaps, new DoubleSummaryStatistics());
					tmp.get(numOfSwaps).accept(value);
					
					
					pw.println(String.format("%s %s %s %s", numOfSwaps, i,j,value ));
				}
				
				permCopy(pw, start, problem, list, i + 1, n, numOfSwaps + 1, numOfMaxSwaps);
				
				Util.swap(list, i, j);
			}
		}
	}
	
	
	public static Map<Integer, DoubleSummaryStatistics> tmp = new HashMap<>();
	
	public static void main(String[] args) throws FileNotFoundException {
		BasicConfigurator.configure();
		tmp = new HashMap<>();
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		
		fcp.add("/home/julesy/workspace/ttp-java/resources", "my_publication_coordinates_more_cities.ttp", new JsonThiefProblemReader());
		
		
/*		
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

		*/
		
		List<AbstractThiefProblem> collected = fcp.collect();

		
		MyRandom rand = new MyRandom();
		
		
		for (AbstractThiefProblem problem : collected) {

			//Solution best = new BiLevelEvoluationaryAlgorithm().run__(problem, new Evaluator(500000),
			//		rand);
			
			
			//TTPVariable var = (TTPVariable) best.getVariable();
			
			TTPVariable var = new TTPVariableFactory(new RandomTourFactory(), new RandomPackingListFactory()).next(problem, rand);
			Solution best = problem.evaluate(var);
			
			final int OPT = 20;
			
			TTPVariable v = (TTPVariable) var;
			List<Integer> l = v.getTour().encode();

			PrintWriter pw = new PrintWriter(new File("/home/julesy/workspace/ttp-results/out.csv"));
			pw.println("opt i j value");
			permCopy(pw, best, problem, l, 1, l.size(), 1, OPT);
			pw.close();
			
			System.out.println("swaps & min & avg & max & range\\\\");
			for (Entry<Integer, DoubleSummaryStatistics> e : tmp.entrySet()) {
				DoubleSummaryStatistics st = e.getValue();
				System.out.println(String.format("%s & %.3f &  %.3f & %.3f & %.3f \\\\", e.getKey(), st.getMin(), st.getAverage(), st.getMax(), st.getMax() - st.getMin()));
			}
			
			System.out.println("FINISHED");
		}

	}

}
