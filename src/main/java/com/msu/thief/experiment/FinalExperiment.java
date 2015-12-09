package com.msu.thief.experiment;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.Report;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.crossover.SinglePointCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.OnePlusOneEA;
import com.msu.thief.algorithms.bilevel.BiLevelSingleObjectiveAlgorithms;
import com.msu.thief.algorithms.fixed.OnePlusOneEAFixedTour;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
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
							: - event.getNonDominatedSolutionSet().get(0).getObjectives(0);
					pw.printf("%s,%s,%s\n", event.getProblem(), event.getAlgorithm(), value);
				}
			});
		}
	}
	

	protected void initialize() {
		//new HypervolumeReport("../ttp-benchmark/ttp-ea/hypervolume.csv");
		new ThiefReport("../ttp-results/bilevel_temp.csv");
		//new JavaScriptThiefVisualizer("../ttp-benchmark/ttp-pi-new");
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
		
		problems.addAll(collected);
	}
	
	

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		/*
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.set("populationSize", 50);
		
		
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-SPX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-UX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]", builder).build());
		
		
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[PMX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[ERX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[CX-HUX]-[SWAP-BF]", builder).build());
		
		builder.set("probMutation", 0.02);
		
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[2OPT-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[PMX-HUX]-[2OPT-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[ERX-HUX]-[2OPT-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[CX-HUX]-[2OPT-BF]", builder).build());
		
		
		builder.set("probMutation", 0.3);
		
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[PMX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-RANDOM]-[PMX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[PMX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[PMX-HUX]-[SWAP-BF]", builder).build());
		
		
		
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[PMX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-EMPTY]-[PMX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-FULL]-[PMX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[PMX-HUX]-[SWAP-BF]", builder).build());
		
		
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[PMX-HUX]-[SWAP-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[NO-HUX]-[NO-BF]", builder).build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-EMPTY]-[NO-HUX]-[NO-BF]", builder).build());
			*/
		

		
/*		Builder<SingleObjectiveEvolutionaryAlgorithm> singleEAFrame = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		singleEAFrame
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new EmptyPackingListFactory()))
			.set("crossover", new TTPCrossover(new NoCrossover<>(), new SinglePointCrossover<>()))
			.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()))
			.set("name", "SingleObjectiveEvolutionaryAlgorithm-EMPTY");
		algorithms.add(singleEAFrame.build());
		
		
		singleEAFrame
			.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
			.set("name", "SingleObjectiveEvolutionaryAlgorithm-OPT");
		algorithms.add(singleEAFrame.build());*/
		
		
/*		Builder<ThiefSingleObjectiveEvolutionaryAlgorithm> singleEA = new Builder<>(ThiefSingleObjectiveEvolutionaryAlgorithm.class);
		singleEA
			.set("populationSize", 50)
			.set("probMutation", 0.8)
			.set("factory", new OptimalPackingListFactory())
			.set("name", "ThiefSingleObjectiveEvolutionary-MICRO-SWAP-PROB-OPT");
		algorithms.add(singleEA.build());*/
		
		/*
		singleEA
			.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
			.set("name", "ThiefSingleObjectiveEvolutionary-ONLY-MUTATION-OPT");
		algorithms.add(singleEA.build());
		
		
		Builder<AntColonyOptimisation> singleEA = new Builder<>(AntColonyOptimisation.class);
		singleEA
			.set("name", "AntColonyOptimisation");
		algorithms.add(singleEA.build());
		*/
		
		/*Builder<ThiefSingleObjectiveEvolutionaryAlgorithm> heur = new Builder<>(ThiefSingleObjectiveEvolutionaryAlgorithm.class);
		heur
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new OptimalPackingListFactory())
			.set("cross", new HalfUniformCrossover<>())
			.set("name", "ThiefSingleObjectiveEvolutionaryAlgorithm-HUX-OPT");
		algorithms.add(heur.build());*/
		
/*		
		heur
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new OptimalPackingListFactory())
			.set("cross", new UniformCrossover<>())
			.set("name", "ThiefSingleObjectiveEvolutionaryAlgorithm-UX-OPT");
		algorithms.add(heur.build());
		*/
/*		
		Builder<ThiefSingleObjectiveEvolutionaryAsSetAlgorithm> h = new Builder<>(ThiefSingleObjectiveEvolutionaryAsSetAlgorithm.class);
		h
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("name", "ThiefSingleObjectiveEvolutionaryAlgorithm");
		
		algorithms.add(h.build());
		
		*/
		
	
		IAlgorithm ea = new OnePlusOneEA(false);
		ea.setName("1+1-EA");
		algorithms.add(ea);
		
		
		OnePlusOneEA eaSym = new OnePlusOneEA(false);
		eaSym.checkSymmetric = true;
		eaSym.setName("1+1-EA-SYM");
		algorithms.add(eaSym);
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> singleEAFrame = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		singleEAFrame
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
			.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
			.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()))
			.set("name", "EA-HUX");
		algorithms.add(singleEAFrame.build());
		
		singleEAFrame
			.set("crossover", new TTPCrossover(new NoCrossover<>(), new UniformCrossover<>()))
			.set("name", "EA-UX");
		algorithms.add(singleEAFrame.build());
		
		singleEAFrame
			.set("crossover", new TTPCrossover(new NoCrossover<>(), new SinglePointCrossover<>()))
			.set("name", "EA-SPX");
		algorithms.add(singleEAFrame.build());
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> algorithm = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		algorithm
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new OptimalPackingListFactory())
			.set("crossover", new HalfUniformCrossover<>())
			.set("mutation", new BitFlipMutation())
			.set("name", "EA-HUX");
		algorithms.add(new BiLevelSingleObjectiveAlgorithms(algorithm.build()));
				
		algorithm
		.set("crossover", new UniformCrossover<>())
		.set("name", "EA-UX");
		algorithms.add(new BiLevelSingleObjectiveAlgorithms(algorithm.build()));
		
		algorithm
		.set("crossover", new SinglePointCrossover<>())
		.set("name", "EA-SPX");
		algorithms.add(new BiLevelSingleObjectiveAlgorithms(algorithm.build()));
		
		IAlgorithm a = new OnePlusOneEAFixedTour();
		a.setName("1+1-EA");
		algorithms.add(new BiLevelSingleObjectiveAlgorithms(a));
		
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> changeTour = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		changeTour
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
			.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
			.set("mutation", new TTPMutation(new SwapMutation<>(), new BitFlipMutation()))
			.set("name", "EA-HUX-SWAP");
		
		algorithms.add(changeTour.build());
	}




	
}