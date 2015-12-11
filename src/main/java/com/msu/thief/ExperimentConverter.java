package com.msu.thief;

import java.util.Collection;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.writer.JsonThiefProblemWriter;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.FileCollectorParser;

public class ExperimentConverter {

	final public static String OUTPUT_FOLDER = "../ttp-benchmark/json/";

	final public static boolean SET_R_AND_RETURN_SINGLE_OBJECTIVE_PROBLEM = false;
	final public static boolean CONVERT_TO_EXPONENTIAL_DROPPING = true;

	public static Collection<AbstractThiefProblem> getProblems() {
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
		return fcp.collect();
	}

	public static void main(String[] args) {

		BasicConfigurator.configure();

		for (AbstractThiefProblem problem : getProblems()) {

			// ThiefProblem problem = new
			// JsonThiefProblemReader().read(PROBLEM);
			// ThiefProblem problem = new
			// BonyadiSingleObjectiveReader().read(PROBLEM);

			Tour<?> tour = AlgorithmUtil.calcBestTour(problem);
			PackingList<?> pack = AlgorithmUtil.calcBestPackingPlan(problem);
			
			if (problem instanceof SingleObjectiveThiefProblem)
				((SingleObjectiveThiefProblem) problem).setToMultiObjective(true);
			
			Solution empty = problem.evaluate(new TTPVariable(tour, new EmptyPackingListFactory().next(problem, null)));

			// c = t / (log l / log r)
			// l is equal to how many percentage of item should be left at t
			if (CONVERT_TO_EXPONENTIAL_DROPPING) {
				final double droppingRate = 0.7;
				final double dropUntilPercentInTime = 0.1;

				double t = empty.getObjectives(0) / 2;
				double denominator = Math.log(dropUntilPercentInTime) / Math.log(droppingRate);
				problem.setProfitEvaluator(new ExponentialProfitEvaluator(droppingRate, t / denominator));

			}

			
			if (SET_R_AND_RETURN_SINGLE_OBJECTIVE_PROBLEM) {

				double R = calcR(problem, tour, pack);
				problem = new SingleObjectiveThiefProblem(problem, R);

				if (problem instanceof SingleObjectiveThiefProblem)
					((SingleObjectiveThiefProblem) problem).setToMultiObjective(false);

			}


			new JsonThiefProblemWriter().write(problem, OUTPUT_FOLDER + problem.getName() + ".json");
		}

	}

	public static double calcR(AbstractThiefProblem problem, Tour<?> tour, PackingList<?> pack) {

		Solution empty = problem.evaluate(new TTPVariable(tour, new EmptyPackingListFactory().next(problem, null)));

		// calculate both solutions and take the faster one as reference point
		Solution s1 = problem.evaluate(new TTPVariable(tour, pack));
		Solution s2 = problem.evaluate(new TTPVariable(tour.getSymmetric(), pack));
		// compare the time
		Solution best = (s1.getObjectives(0) < s2.getObjectives(0)) ? s1 : s2;

		double deltaTime = best.getObjectives(0) - empty.getObjectives(0);
		double deltaProfit = best.getObjectives(1);

		double R = deltaProfit / deltaTime;

		return R;
	}

}
