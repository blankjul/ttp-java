package com.msu.thief;

import javax.management.JMException;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.Map;
import com.msu.thief.model.packing.BooleanPackingListFactory;
import com.msu.thief.model.tour.StandardTourFactory;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;

public class App {

	public static TravellingThiefProblem example() {
		Map m = new Map(4).set(0, 1, 5).set(0, 2, 6).set(0, 3, 6).set(1, 2, 5).set(1, 3, 6).set(2, 3, 4);
		ItemCollection<Item> items = new ItemCollection<Item>();
		items.add(2, new Item(10, 3));
		items.add(2, new Item(4, 1));
		items.add(2, new Item(4, 1));
		items.add(1, new Item(2, 2));
		items.add(2, new Item(3, 3));
		items.add(3, new Item(2, 2));
		TravellingThiefProblem s = new TravellingThiefProblem(m, items, 3);
		s.setProfitEvaluator(new ExponentialProfitEvaluator());
		return s;
	}

	public static void main(String[] args) throws JMException {

		TravellingThiefProblem ttp = example();

		RandomSearch<TTPVariable, TravellingThiefProblem> r = new RandomSearch<>(
				new TTPVariableFactory(new StandardTourFactory(), new BooleanPackingListFactory()));
		r.setMaxEvaluations(100000L);
		
		
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setCrossover(new TTPCrossover(new PMXCrossover<Integer>(), new SinglePointCrossover<>()));
		
		//NonDominatedSolutionSet set = builder.create().run(ttp);
		NonDominatedSolutionSet set = builder.create().run(ttp);
		for (Solution solution : set.getSolutions()) {
			System.out.println(String.format("%s -> %s", solution.getVariable(), solution));
		}

		/*
		 * NSGAII<jISolution> nsga = new NSGAII<jISolution>(new jProblem(ttp),
		 * 25000, 100, new jCrossover(), new jMutation(), new
		 * BinaryTournamentSelection<jISolution>(), new
		 * SequentialSolutionListEvaluator<jISolution>()); nsga.run();
		 * 
		 * for (jISolution solution : nsga.getResult()) {
		 * System.out.println(solution); }
		 */

	}
}
