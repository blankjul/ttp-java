package com.msu.experiment;

import java.util.List;
import java.util.function.Function;

import com.msu.NSGAIIFactory;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.io.reader.KnapsackProblemReader;
import com.msu.model.AReport;
import com.msu.moo.experiment.AExperiment;
import com.msu.problems.KnapsackProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.util.FileCollectorParser;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.RunFinishedEvent;

public class KNPOperatorExperiment extends AExperiment {

	private class KnpReport extends AReport {
		public KnpReport() {
			super();
			pw.println("problem,algorithm,result");
			EventDispatcher.getInstance().register(RunFinishedEvent.class, new IListener<RunFinishedEvent>() {
				@Override
				public void handle(RunFinishedEvent event) {
					
					double value = (event.getNonDominatedSolutionSet().size() == 0) ? Double.NEGATIVE_INFINITY
							: event.getNonDominatedSolutionSet().get(0).getObjectives(1);
					
					pw.printf("%s,%s,%s\n", event.getProblem(), event.getAlgorithm(), value);
				}
			});
		}
	}

	protected void initialize() {
		new KnpReport().setPath("experiment/knp_result.csv");
	};

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[NO-SPX]-[NO-BF]").setName("SPX").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[NO-UX]-[NO-BF]").setName("UX").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[NO-HUX]-[NO-BF]").setName("HUX").create());
	}

	@Override
	protected void setProblems(List<IProblem> problems) {

		FileCollectorParser<IProblem> fc = new FileCollectorParser<>();

		fc.add("resources", "knapPI_13_????_1000.csv", new Function<String, IProblem>() {

			@Override
			public IProblem apply(String t) {

				KnapsackProblem knp = new KnapsackProblemReader().read(t);

				ItemCollection<Item> items = new ItemCollection<>();
				for (Item i : knp.getItems())
					items.add(0, i);

				ThiefProblem problem = new ThiefProblem(new SymmetricMap(1), items, knp.getMaxWeight());
				problem.setName(knp.getName());
				return problem;
			}

		});
		problems.addAll(fc.collect());

	}

}
