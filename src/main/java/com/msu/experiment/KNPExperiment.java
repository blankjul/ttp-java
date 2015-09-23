package com.msu.experiment;

import java.util.Arrays;
import java.util.List;

import com.msu.AlgorithmFactory;
import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.util.ObjectFactory;
import com.msu.moo.util.Pair;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.scenarios.AScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

/**
 * 
 * Scenarios:
 * 
 * KNP_13_0020_1000_1, KNP_13_0200_1000_1, KNP_13_1000_1000_1 ,KNP_13_2000_1000_1
 *
 */
public class KNPExperiment extends AMultiObjectiveExperiment<TravellingThiefProblem> {

	final public static String SCENARIO = "com.msu.knp.scenarios.impl.KNP_13_0020_1000_1";
	
	
	// ! the current scenario which is executed
	@SuppressWarnings("unchecked")
	protected final AScenario<Pair<List<Item>, Integer>, PackingList<?>> scenario = (AScenario<Pair<List<Item>, Integer>, PackingList<?>>) ObjectFactory
			.create(SCENARIO);

	
	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = AlgorithmFactory.createNSGAIIBuilder();
		builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<>()));
		builder.setPopulationSize(1000);
		settings.addAlgorithm(builder.create());
	}

	
	@Override
	protected void setProblems(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		Pair<List<Item>, Integer> obj = scenario.getObject();

		// add all to the first city
		ItemCollection<Item> items = new ItemCollection<>();
		for (Item i : obj.first)
			items.add(0, i);

		TravellingThiefProblem problem = new TravellingThiefProblem(new SymmetricMap(1), items, obj.second);
		problem.setName(this.getClass().getSimpleName());
		settings.addProblem(problem);
	}
	
	

	@Override
	protected void setOptima(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		TravellingThiefProblem problem = settings.getProblems().get(0);
		Tour<?> t = new StandardTour(Arrays.asList(0));
		Solution s = problem.evaluate(new TTPVariable(Pair.create(t, scenario.getOptimal())));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet(Arrays.asList(s));
		settings.addOptima(problem, set);
	}
	


}
