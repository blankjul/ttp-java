package com.msu.experiment;

import java.util.Arrays;
import java.util.List;

import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.knp.model.factory.EmptyPackingListFactory;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.UniformCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.ObjectFactory;
import com.msu.moo.util.Pair;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.scenarios.AScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;
import com.msu.tsp.model.factory.RandomFactory;


public class KNPOperatorExperiment extends AMultiObjectiveExperiment<TravellingThiefProblem> {

	
	protected final String[] SCENARIOS = new String[] { 
			"KNP_13_0020_1000_1", 
			"KNP_13_0200_1000_1", 
			"KNP_13_1000_1000_1",
			"KNP_13_2000_1000_1"
			};
	
	@Override
	public void visualize() {
		for (IProblem problem : settings.getProblems()) {
			for (IAlgorithm<NonDominatedSolutionSet, ?> algorithm : settings.getAlgorithms()) {
				for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
					if (set.size() != 1)
						throw new RuntimeException("Single Objective problem only one solution allowed.");
					System.out.println(String.format("%s,%s,%s", problem, algorithm, set.get(0).getObjectives(1)));
				}
			}
		}
		
		for (IProblem problem : settings.getProblems()) {
			System.out.println(String.format("%s,%s,%s", problem, "Optimum", settings.getOptima().get(problem).get(0).getObjective().get(1)));
		}
	}

	
	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new EmptyPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new SinglePointCrossover<>()))
				.setName("SPX").create());
		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new UniformCrossover<>()))
				.setName("UX").create());
		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new HalfUniformCrossover<>()))
				.setName("HUX").create());
	}

	
	
	
	@Override
	protected void setProblems(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		for (String s : SCENARIOS) {
			@SuppressWarnings("unchecked")
			AScenario<Pair<List<Item>,Integer>, PackingList<?>> scenario = 
			(AScenario<Pair<List<Item>,Integer>, PackingList<?>>) ObjectFactory.create("com.msu.knp.scenarios.impl." + s);
			
			Pair<List<Item>, Integer> obj = scenario.getObject();

			// add all to the first city
			ItemCollection<Item> items = new ItemCollection<>();
			for (Item i : obj.first)
				items.add(0, i);

			TravellingThiefProblem problem = new TravellingThiefProblem(new SymmetricMap(1), items, obj.second);
			problem.setName(s);
			settings.addProblem(problem);
			
			Tour<?> t = new StandardTour(Arrays.asList(0));
			Solution sol = problem.evaluate(new TTPVariable(Pair.create(t, scenario.getOptimal())));
			NonDominatedSolutionSet set = new NonDominatedSolutionSet(Arrays.asList(sol));
			settings.addOptima(problem, set);
			
			
		}
	}

	@Override
	protected void setOptima(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		// otherwise all optima are set to zero!
	}
	
	
	


}
