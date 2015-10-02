package com.msu.experiment;

import java.util.List;

import com.msu.io.reader.KnapsackProblemReader;
import com.msu.knp.KnapsackProblem;
import com.msu.knp.model.Item;
import com.msu.knp.model.factory.EmptyPackingListFactory;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.UniformCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.ThiefProblem;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.factory.RandomTourFactory;


public class KNPOperatorExperiment extends AExperiment {

	
	protected final String[] SCENARIOS = new String[] { 
			"resources/knapPI_13_0020_1000.csv",
			"resources/knapPI_13_0200_1000.csv",
			"resources/knapPI_13_1000_1000.csv",
			"resources/knapPI_13_2000_1000.csv"
			};
	
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new TTPVariableFactory(new RandomTourFactory(), new EmptyPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		
		algorithms.add(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new SinglePointCrossover<>()))
				.setName("SPX").create());
		
		algorithms.add(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new UniformCrossover<>()))
				.setName("UX").create());
		
		algorithms.add(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new HalfUniformCrossover<>()))
				.setName("HUX").create());
	}

	

	@Override
	protected void setProblems(List<IProblem> problems) {
		
		for (String scenario : SCENARIOS) {
			
			KnapsackProblem knp = new KnapsackProblemReader().read(scenario);

			ItemCollection<Item> items = new ItemCollection<>();
			for (Item i : knp.getItems()) items.add(0, i);

			ThiefProblem problem = new ThiefProblem(new SymmetricMap(1), items, knp.getMaxWeight());
			problem.setName(knp.getName());
			problems.add(problem);
		}
		
	}
	
	
	
	


}
