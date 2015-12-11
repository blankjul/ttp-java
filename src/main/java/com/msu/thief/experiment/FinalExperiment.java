package com.msu.thief.experiment;

import java.util.Arrays;
import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.Report;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.SinglePointCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.BiLevelAlgorithms;
import com.msu.thief.algorithms.bilevel.GreedyPackingAlgorithm;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEA;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.APackingListFactory;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.pack.factory.RandomPackingListFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.util.FileCollectorParser;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.RunFinishedEvent;

public class FinalExperiment extends AExperiment {

	private class ThiefReport extends Report {
		public ThiefReport(String path) {
			super(path);
			pw.println("problem,algorithm,result");
			EventDispatcher.getInstance().register(RunFinishedEvent.class, new IListener<RunFinishedEvent>() {
				@Override
				public void handle(RunFinishedEvent event) {
					double value = (event.getNonDominatedSolutionSet().size() == 0) ? Double.NEGATIVE_INFINITY
							: -event.getNonDominatedSolutionSet().get(0).getObjectives(0);
					pw.printf("%s,%s,%s\n", event.getProblem(), event.getAlgorithm(), value);
				}
			});
		}
	}

	protected void initialize() {
		// new HypervolumeReport("../ttp-benchmark/ttp-ea/hypervolume.csv");
		new ThiefReport("../ttp-results/bilevel_temp.csv");
		// new JavaScriptThiefVisualizer("../ttp-benchmark/ttp-pi-new");
	};

	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();

		fcp.add("../ttp-benchmark/SingleObjective/10", "*", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "*", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "*", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100", "*", new BonyadiSingleObjectiveReader());
		
/*		fcp.add("../ttp-benchmark/SingleObjective/10", "10_5_6_25.txt", new BonyadiSingleObjectiveReader());
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
		List<ThiefProblem> collected = fcp.collect();
		problems.addAll(collected);
	}

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {

		Builder<OnePlusOneEA> builderOnePlusOne = new Builder<>(OnePlusOneEA.class);
		builderOnePlusOne.set("checkSymmetric", false).set("name", "1+1-EA");
		algorithms.add(builderOnePlusOne.build());

		builderOnePlusOne.set("checkSymmetric", true).set("name", "1+1-EA-SYM");
		algorithms.add(builderOnePlusOne.build());

		Builder<SingleObjectiveEvolutionaryAlgorithm> singleEAFrame = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		
		
		singleEAFrame
			.set("populationSize", 50)
			.set("probMutation", 0.3);
		
		
		for (String factory : Arrays.asList("EMPTY", "RANDOM", "OPT")) {

			APackingListFactory fac = null;
			
			if (factory.equals("EMPTY")) {
				fac = new EmptyPackingListFactory();
			} else if (factory.equals("RANDOM")) {
				fac = new RandomPackingListFactory();
			} else if (factory.equals("OPT")) {
				fac = new OptimalPackingListFactory();
			}
			
			for (String crossover : Arrays.asList("HUX", "UX", "SPX")) {

				AbstractCrossover<List<Boolean>> cross = null;
				
				if (crossover.equals("HUX")) {
					cross = new HalfUniformCrossover<>();
				} else if (crossover.equals("UX")) {
					cross = new UniformCrossover<>();
				} else if (crossover.equals("SPX")) {
					cross = new SinglePointCrossover<>();
				}
				
				for (String type : Arrays.asList("POOL", "BILEVEL")) {
					
					singleEAFrame .set("name", String.format("EA-%s-%s", factory, crossover));
					
					if (type.equals("POOL")) {
						
						singleEAFrame
							.set("factory", new TTPVariableFactory(new OptimalTourFactory(), fac))
							.set("crossover", new TTPCrossover(new NoCrossover<>(), cross))
							.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()));
						algorithms.add(singleEAFrame.build());
						
					} else if (type.equals("BILEVEL")) {
						
						singleEAFrame
							.set("factory", fac)
							.set("crossover", cross)
							.set("mutation", new BitFlipMutation());
							
						algorithms.add(new BiLevelAlgorithms(singleEAFrame.build()));
						
					}
				}
			}
		}

		
		
	
		IAlgorithm a = new OnePlusOneEAFixedTour();
		a.setName("1+1-EA");
		algorithms.add(new BiLevelAlgorithms(a));

		
		Builder<SingleObjectiveEvolutionaryAlgorithm> changeTour = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		changeTour.set("populationSize", 50).set("probMutation", 0.3)
				.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
				.set("mutation", new TTPMutation(new SwapMutation<>(), new BitFlipMutation()))
				.set("name", "EA-HUX-SWAP");
		algorithms.add(changeTour.build());

		Builder<GreedyPackingAlgorithm> greedy = new Builder<>(GreedyPackingAlgorithm.class);
		greedy.set("type", GreedyPackingAlgorithm.TYPE.BEST).set("name", "GREEDY-BEST");
		algorithms.add(new BiLevelAlgorithms(greedy.build()));

		greedy.set("type", GreedyPackingAlgorithm.TYPE.RANDOM).set("name", "GREEDY-RANDOM");
		algorithms.add(new BiLevelAlgorithms(greedy.build()));

		// algorithms.add(new BiLevelAlgorithms(new AntColonyOptimisation()));

	}

}