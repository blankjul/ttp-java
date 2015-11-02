package com.msu.algorithms;

import java.util.ArrayList;
import java.util.Collection;

import com.msu.NSGAIIFactory;
import com.msu.moo.algorithms.nsgaII.INSGAIIModifactor;
import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.algorithms.nsgaII.NSGAIIBuilder;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Random;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.ATourFactory;

public class BiLevelAlgorithm extends AbstractAlgorithm {

	protected int populationSize = 1000;

	private class UpperLevelFactory extends ATourFactory {

		protected Tour<?> t = null;
		
		public UpperLevelFactory(Tour<?> t) {
			super();
			this.t = t;
		}

		@Override
		protected Tour<?> next_(IProblem problem, Random rand) {
			return t;
		}
		
	}
	
	@Override
	public NonDominatedSolutionSet run_(IEvaluator eval, Random rand) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		Collection<Tour<?>> tours = new ArrayList<>();
		ThiefProblem problem = (ThiefProblem) eval.getProblem();
		SymmetricMap map = problem.getMap();
		SalesmanProblem tsp = new SalesmanProblem(map);
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(new Evaluator(tsp));

		// add good tours!
		tours.add(bestTour);
		
		//tours.add(bestTour.getSymmetric());
		
		/*
		ATourFactory facTour = new TwoOptFactory();
		for (int i = tours.size(); i < populationSize; i++) {
			tours.add(facTour.next(problem, rand));
		}
		*/

		/*
		Collection<PackingList<?>> packing = new ArrayList<>();
		for (int i = 1; i <= populationSize; i++) {
			double maximalCapacity = (problem.getMaxWeight() / (double) populationSize) * i;
			NonDominatedSolutionSet combo = new KnapsackCombo().run(new Evaluator(new KnapsackProblem((int) maximalCapacity, problem.getItems())));
			for (Solution s : combo) {
				packing.add((PackingList<?>) s.getVariable());
			}
		}
		*/

		class CheckSymmetric implements INSGAIIModifactor {

			@Override
			public void modify(IEvaluator eval, SolutionSet population, Random rand) {
				
				Collection<Solution> symmetric = new ArrayList<>();
				final int numOfSymmetric = population.size() / 10;
				
				
				for (int i = 0; i < numOfSymmetric; i++) {
					Solution s = population.get(rand.nextInt(population.size()));
					TTPVariable parent = (TTPVariable) s.getVariable();
					TTPVariable var = new TTPVariable(parent.getTour().getSymmetric(), parent.getPackingList());
					symmetric.add(eval.evaluate(var));
				}
				population.addAll(symmetric);
			}
		}
		
		
		
		
		for (Tour<?> t : tours) {
			
			if (!eval.hasNext()) break;
			
			NSGAIIBuilder builder = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-OPT]-[NO-HUX]-[NO-BF]");
			builder.setFactory(new TTPVariableFactory(new UpperLevelFactory(t), new OptimalPackingListFactory()));
			builder.setFuncModify(new CheckSymmetric());
			NSGAII nsgaII = builder.create();
			
			//int evals = eval.getMaxEvaluations() / tours.size();
			
			NonDominatedSolutionSet lowerSet = nsgaII.run(eval);
			
			for (Solution solution : lowerSet) {
				set.add(eval.evaluate(solution.getVariable()));
			}
		}
		return set;

	}

}
