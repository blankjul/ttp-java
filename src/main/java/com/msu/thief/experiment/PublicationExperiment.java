package com.msu.thief.experiment;

import java.util.Arrays;

import com.msu.moo.algorithms.ExhaustiveSolver;
import com.msu.moo.experiment.OneProblemOneAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Pair;
import com.msu.moo.visualization.ScatterPlot;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.Map;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TTPExhaustiveFactory;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.problems.knp.KnapsackExhaustiveFactory;
import com.msu.thief.problems.knp.KnapsackProblem;
import com.msu.thief.problems.knp.KnapsackVariable;
import com.msu.thief.problems.tsp.TSPExhaustiveFactory;
import com.msu.thief.problems.tsp.TravellingSalesmanProblem;
import com.msu.thief.variable.TTPVariable;

public class PublicationExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {

	@Override
	public void report() {

		
		ScatterPlot sp = new ScatterPlot("GreedyMap");

		TSPExhaustiveFactory facTSP = new TSPExhaustiveFactory();
		KnapsackExhaustiveFactory facKNP = new KnapsackExhaustiveFactory();

		KnapsackProblem knp = new KnapsackProblem(problem.getMaxWeight(), problem.getItems().getItems());
		TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(problem.getMap());

		Tour<?> t = facTSP.create(tsp);
		PackingList<?> p = facKNP.create(knp).get();
		while (t != null) {
			SolutionSet set = new SolutionSet();
			while (p != null) {
				TTPVariable ttpvar = new TTPVariable(Pair.create(t, p));
				Solution s = this.problem.evaluate(ttpvar);
				set.add(s);
				//System.out.println(String.format("%s,%s,\"%s\"", s.getObjectives().get(0),s.getObjectives().get(1), t.toString()));
				// sp.add(, "ExhaustiveResult");

				// Q.add();

				KnapsackVariable var = facKNP.create(knp);
				if (var != null)
					p = var.get();
				else
					p = null;
			}
			sp.add(set, t.toString());
			facKNP = new KnapsackExhaustiveFactory();
			p = facKNP.create(knp).get();
			t = facTSP.create(tsp);
		}

		sp.show();
	}

	@Override
	protected IAlgorithm<TravellingThiefProblem> getAlgorithm() {
		TTPExhaustiveFactory fac = new TTPExhaustiveFactory();
		ExhaustiveSolver<TTPVariable, TravellingThiefProblem> exhaustive = new ExhaustiveSolver<>(fac);
		return exhaustive;
	}

	@Override
	protected TravellingThiefProblem getProblem() {
		Map m = new Map(4);
		m.set(0, 1, 4);
		m.set(0, 2, 10);
		m.set(0, 3, 3);
		m.set(1, 2, 5);
		m.set(1, 3, 6);
		m.set(2, 3, 8);

		ItemCollection<Item> items = new ItemCollection<>();
		items.add(0, new Item(30, 25));
		items.add(1, new Item(34, 30));
		items.add(2, new Item(40, 40));
		items.add(3, new Item(25, 21));

		// create problem
		TravellingThiefProblem ttp = new TravellingThiefProblem(m, items, 80);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(0.9, 10.0));

		// execute hand calculations
		Tour<?> tour = new StandardTour(Arrays.asList(2, 1, 0, 3));
		PackingList<?> list = new BooleanPackingList(Arrays.asList(false, true, true, false));
		Solution result = ttp.evaluate(new TTPVariable(Pair.create(tour, list)));
		System.out.println(result);

		return ttp;

	}

	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		return null;
	}

}