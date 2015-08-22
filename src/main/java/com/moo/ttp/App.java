package com.moo.ttp;

import javax.management.JMException;

import com.moo.ttp.model.item.Item;
import com.moo.ttp.model.item.ItemCollection;
import com.moo.ttp.model.packing.BooleanPackingListFactory;
import com.moo.ttp.model.tour.Map;
import com.moo.ttp.model.tour.StandardTourFactory;
import com.moo.ttp.variable.TTPCrossover;
import com.moo.ttp.variable.TTPFactory;
import com.moo.ttp.variable.TTPMutation;
import com.moo.ttp.variable.TTPVariable;
import com.moo.ttp.variable.TravellingThiefProblem;
import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.model.interfaces.IFactory;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;

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
		TravellingThiefProblemSettings s = new TravellingThiefProblemSettings(m, items, 3);
		s.setProfitCalculator("com.moo.ttp.calculator.profit.ExponentialProfitCalculator");
		return new TravellingThiefProblem(s);
	}

	public static void main(String[] args) throws JMException {

		TravellingThiefProblem ttp = example();
		
		IFactory<TTPVariable> fac = new TTPFactory(new StandardTourFactory(ttp.numOfCities()), new BooleanPackingListFactory(ttp.numOfItems()));
		
		NSGAII<TTPVariable, TravellingThiefProblem> nsgaII = new NSGAII<TTPVariable, TravellingThiefProblem>(
				fac,
				10000L, new TTPCrossover(new CycleCrossover<>(), new SinglePointCrossover<>()), 
				new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));

		NonDominatedSolutionSet set = nsgaII.run(ttp);
		for (Solution solution : set.getSolutions()) {
			System.out.println(solution);
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
