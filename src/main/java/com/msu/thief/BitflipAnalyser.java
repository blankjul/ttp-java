package com.msu.thief;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.solution.Solution;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.factory.RandomPackingListFactory;
import com.msu.thief.variable.tour.factory.RandomTourFactory;
import com.msu.util.FileCollectorParser;
import com.msu.util.MyRandom;

public class BitflipAnalyser {

	final public static String PROBLEM = "../ttp-benchmark/SingleObjective/10/10_5_6_25.txt";


	
	public static void bitlfip(PrintWriter pw, Solution start, AbstractThiefProblem problem, List<Boolean> list, int i, int numOfBitlfips) {
		
		if (i == list.size()) {
			Solution current = problem.evaluate(new TTPVariable(((TTPVariable)start.getVariable()).getTour(), new BooleanPackingList(list)));
			double value = (current.getObjectives(0) - start.getObjectives(0)) / start.getObjectives(0);
			
			if (!tmp.containsKey(numOfBitlfips)) tmp.put(numOfBitlfips, new DoubleSummaryStatistics());
			tmp.get(numOfBitlfips).accept(value);
			System.out.println(String.format("%s %s", numOfBitlfips, value ));
			return;
			
			
		} else {
			
			    // no flip
				bitlfip(pw, start, problem, list, i + 1, numOfBitlfips);
				
				// flip
				List<Boolean> setTrue = new ArrayList<>(list);
				setTrue.set(i, !list.get(i));
				bitlfip(pw, start, problem, setTrue, i + 1, numOfBitlfips + 1);
				
			
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

			PrintWriter pw = new PrintWriter(new File("/home/julesy/workspace/ttp-results/outBit.csv"));
			pw.println("opt i j value");
			bitlfip(pw, best, problem, var.getPackingList().encode(), 0, 0);
			pw.close();
			
			System.out.println("flips & min & avg & max & range\\\\");
			for (Entry<Integer, DoubleSummaryStatistics> e : tmp.entrySet()) {
				DoubleSummaryStatistics st = e.getValue();
				System.out.println(String.format("%s & %.3f &  %.3f & %.3f & %.3f \\\\", e.getKey(), st.getMin(), st.getAverage(), st.getMax(), st.getMax() - st.getMin()));
			}
			
			System.out.println("FINISHED");
		}

	}

}
