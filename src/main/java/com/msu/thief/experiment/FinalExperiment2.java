package com.msu.thief.experiment;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.builder.MOEADBuilder;
import com.msu.experiment.AExperiment;
import com.msu.experiment.ExperimentResult;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.Report;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.thief.algorithms.OnePlusOneEA;
import com.msu.thief.algorithms.ThiefDecomposedAlgorithm;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.thief.visualize.js.JavaScriptThiefVisualizer;
import com.msu.util.FileCollectorParser;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.ProblemFinishedEvent;

public class FinalExperiment2 extends AExperiment {

	private class ThiefReport extends Report {
		public ThiefReport(String path) {
			super(path);
			pw.println("problem,algorithm,run,popSize,isDominating,isDominated");
			EventDispatcher.getInstance().register(ProblemFinishedEvent.class, new IListener<ProblemFinishedEvent>() {
				@Override
				public void handle(ProblemFinishedEvent event) {

					IProblem problem = event.getProblem();
					AExperiment experiment = event.getExperiment();
					ExperimentResult result = experiment.getResult();

					// search for single-obj algorithm to compare
					IAlgorithm ea = null;
					for (IAlgorithm a : experiment.getAlgorithms()) {
						if (a.toString() == "1+1 EA")
							ea = a;
					}

					// compare each iteration
					for (int i = 0; i < event.getNumOfRuns(); i++) {
						Solution single = result.get(problem, ea, i).get(0);
						for (IAlgorithm a : experiment.getAlgorithms()) {
							if (a.toString() == "1+1 EA")
								continue;

							int isDominating = 0;
							int isDominated = 0;

							NonDominatedSolutionSet set = result.get(problem, a, i);
							for (Solution s : set) {
								if (new SolutionDominator().isDominating(single, s))
									isDominating++;
								if (new SolutionDominator().isDominating(s, single))
									isDominated++;
							}

							pw.printf("%s,%s,%s,%s,%s,%s\n", event.getProblem(), a, i, set.size(), isDominating,
									isDominated);

						}

					}

				}
			});
		}
	}

	protected void initialize() {
		// new HypervolumeReport("../ttp-benchmark/ttp-ea/hypervolume.csv");
		new ThiefReport("../ttp-benchmark/ttp-ea-multi/result.csv");
		new JavaScriptThiefVisualizer("../ttp-benchmark/ttp-ea-multi");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();

		fcp.add("../ttp-benchmark/SingleObjective/50", "50_25_3_75.txt", new BonyadiSingleObjectiveReader());

		/*
		 * fcp.add("../ttp-benchmark/SingleObjective/10", "10_5_6_25.txt", new
		 * BonyadiSingleObjectiveReader());
		 * fcp.add("../ttp-benchmark/SingleObjective/10", "10_10_2_50.txt", new
		 * BonyadiSingleObjectiveReader());
		 * fcp.add("../ttp-benchmark/SingleObjective/10", "10_15_10_75.txt", new
		 * BonyadiSingleObjectiveReader());
		 * 
		 * fcp.add("../ttp-benchmark/SingleObjective/20", "20_5_6_75.txt", new
		 * BonyadiSingleObjectiveReader());
		 * fcp.add("../ttp-benchmark/SingleObjective/20", "20_20_7_50.txt", new
		 * BonyadiSingleObjectiveReader());
		 * fcp.add("../ttp-benchmark/SingleObjective/20", "20_30_9_25.txt", new
		 * BonyadiSingleObjectiveReader());
		 * 
		 * fcp.add("../ttp-benchmark/SingleObjective/50", "50_15_8_50.txt", new
		 * BonyadiSingleObjectiveReader());
		 * fcp.add("../ttp-benchmark/SingleObjective/50", "50_25_3_75.txt", new
		 * BonyadiSingleObjectiveReader());
		 * fcp.add("../ttp-benchmark/SingleObjective/50", "50_75_6_25.txt", new
		 * BonyadiSingleObjectiveReader());
		 * 
		 * fcp.add("../ttp-benchmark/SingleObjective/100", "100_5_10_50.txt",
		 * new BonyadiSingleObjectiveReader());
		 * fcp.add("../ttp-benchmark/SingleObjective/100", "100_50_5_75.txt",
		 * new BonyadiSingleObjectiveReader());
		 * fcp.add("../ttp-benchmark/SingleObjective/100", "100_150_10_25.txt",
		 * new BonyadiSingleObjectiveReader());
		 */

		List<ThiefProblem> collected = fcp.collect();

		collected.forEach((p) -> {
			if (p instanceof SingleObjectiveThiefProblem)
				((SingleObjectiveThiefProblem) p).setToMultiObjective(true);
		});

		problems.addAll(collected);
	}

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {


		algorithms.add(new ThiefDecomposedAlgorithm());

		
		MOEADBuilder moead = new MOEADBuilder();
		moead
			.set("populationSize", 50)
			.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
			.set("crossover", new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()))
			.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()))
			.set("T", 10)
	     	.set("delta", 0.3);
		

		algorithms.add(
			new Builder<OnePlusOneEA>(OnePlusOneEA.class)
			.set("checkSymmetric", false)
			.set("name", "1+1 EA")
			.build());
		
		
		algorithms.add(
			new Builder<OnePlusOneEA>(OnePlusOneEA.class)
			.set("checkSymmetric", true)
			.set("name", "1+1 EA - SYM")
			.build());
		
		
	}

}