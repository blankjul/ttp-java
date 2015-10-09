package com.msu.experiment;

import java.util.List;
import java.util.function.Function;

import com.msu.NSGAIIFactory;
import com.msu.io.reader.KnapsackProblemReader;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.report.SolutionSetReport;
import com.msu.moo.util.FileCollectorParser;
import com.msu.problems.KnapsackProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;


public class KNPOperatorExperiment extends AExperiment {


	
	protected void initialize() {
		new SolutionSetReport().setPath("experiment/knp_result.csv");
	};
	
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[NO-SPX]-[NO-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[NO-UX]-[NO-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[NO-HUX]-[NO-BF]").create());
	}

	

	@Override
	protected void setProblems(List<IProblem> problems) {
		
		FileCollectorParser<IProblem> fc = new FileCollectorParser<>();
		
		fc.add("resources", "knapPI_13_????_1000.csv", new Function<String, IProblem>() {

			@Override
			public IProblem apply(String t) {
				
				KnapsackProblem knp = new KnapsackProblemReader().read(t);

				ItemCollection<Item> items = new ItemCollection<>();
				for (Item i : knp.getItems()) items.add(0, i);

				ThiefProblem problem = new ThiefProblem(new SymmetricMap(1), items, knp.getMaxWeight());
				problem.setName(knp.getName());
				return problem;
			}
			
		});
		problems.addAll(fc.collect());

	}
	

	


}
