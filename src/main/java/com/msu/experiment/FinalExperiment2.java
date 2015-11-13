package com.msu.experiment;

import java.util.List;

import com.msu.algorithms.DecomposedAlgorithm;
import com.msu.algorithms.MicroEvoluationaryAlgorithm;
import com.msu.algorithms.OnePlusOneEA;
import com.msu.algorithms.util.SymmetricTour;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.io.reader.BonyadiSingleObjectiveReader;
import com.msu.model.AReport;
import com.msu.moo.algorithms.moead.MOEADBuilder;
import com.msu.moo.algorithms.nsgaII.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.problems.SingleObjectiveThiefProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.util.FileCollectorParser;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.ProblemFinishedEvent;
import com.msu.visualize.js.JavaScriptThiefVisualizer;

public class FinalExperiment2 extends AExperiment {

	private class ThiefReport extends AReport {
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
		new ThiefReport("../ttp-benchmark/ttp-ea-multi2/result.csv");
		new JavaScriptThiefVisualizer("../ttp-benchmark/ttp-ea-multi2");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();

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

		List<ThiefProblem> collected = fcp.collect();

		collected.forEach((p) -> {
			if (p instanceof SingleObjectiveThiefProblem)
				((SingleObjectiveThiefProblem) p).setToMultiObjective(true);
		});

		problems.addAll(collected);
	}

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {

		/*
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[RANDOM-RANDOM]-[OX-SPX]-[SWAP-BF]",
		 * builder).setName("SPX").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[RANDOM-RANDOM]-[OX-UX]-[SWAP-BF]",
		 * builder).setName("UX").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]",
		 * builder).setName("HUX").create());
		 */
		/*
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]",
		 * builder).setName("OX").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[RANDOM-RANDOM]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("PMX").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[RANDOM-RANDOM]-[ERX-HUX]-[SWAP-BF]",
		 * builder).setName("ERX").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[RANDOM-RANDOM]-[CX-HUX]-[SWAP-BF]",
		 * builder).setName("CX").create());
		 */
		/*
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[RANDOM-RANDOM]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("RANDOM").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[NEAREST-RANDOM]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("NEAREST").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[2OPT-RANDOM]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("2OPT").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-RANDOM]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("OPT").create());
		 */

		/*
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-RANDOM]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("RANDOM").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-EMPTY]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("EMPTY").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-FULL]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("FULL").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-OPT]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("OPT").create());
		 */
		/*
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-OPT]-[PMX-HUX]-[SWAP-BF]",
		 * builder).setName("OPT-OPT-YES").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-OPT]-[NO-HUX]-[NO-BF]",
		 * builder).setName("OPT-OPT-NO").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-OPT]-[NO-SPX]-[NO-BF]",
		 * builder).setName("OPT-OPT-NO-SPX").create());
		 * algorithms.add(NSGAIIFactory.createNSGAIIBuilder(
		 * "NSGAII-[OPT-EMPTY]-[NO-HUX]-[NO-BF]",
		 * builder).setName("OPT-EMPTY-NO").create());
		 */

		// algorithms.add(new DecomposedAlgorithm());

		// algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[NO-HUX]-[SWAP-BF]",
		// builder).setName("OPT-OPT-NO").create());

		// algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[NO-HUX]-[SWAP-BF]",
		// builder).setName("OPT-OPT-NO-SYM").create());

		/*
		 * class SymmetricMutation extends AbstractMutation<List<Integer>> {
		 * 
		 * @Override protected void mutate_(List<Integer> element, Random rand)
		 * { element = new StandardTour(element).getSymmetric().encode(); } }
		 */

		algorithms.add(new MicroEvoluationaryAlgorithm());

		// IAlgorithm a = new MicroEvoluationaryAlgorithm(true);
		// a.setName("MicroEvoluationaryAlgorithm-2OPT");
		// algorithms.add(a);

		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFuncModify(new SymmetricTour());
		builder.setPopulationSize(50);
		

		MOEADBuilder b = new MOEADBuilder();
		b.setFactory(new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()));
		b.setCrossover(new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()));
		b.setMutation(new TTPMutation(new NoMutation<>(), new BitFlipMutation()));
		b.setPopulationSize(50);
		b.setT(10);
		algorithms.add(b.create());

		
		algorithms.add(new DecomposedAlgorithm());

		
		IAlgorithm ea = new OnePlusOneEA(true);
		ea.setName("1+1 EA");
		algorithms.add(ea);

	}

}