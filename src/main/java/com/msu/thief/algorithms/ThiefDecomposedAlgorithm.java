package com.msu.thief.algorithms;


import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.algorithms.DecomposedAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.operators.AbstractMutation;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.soo.algorithms.HillClimbing;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.KnapsackProblem;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;
import com.msu.util.MyRandom;
import com.msu.util.Range;

public class ThiefDecomposedAlgorithm extends DecomposedAlgorithm {

	// ! number of decomposed problem that are solved
	protected int numOfRuns = 50;
	
	class Mutation extends AbstractMutation<Pair<Tour<?>, PackingList<?>>> {

		@Override
		protected Pair<Tour<?>, PackingList<?>> mutate_(Pair<Tour<?>, PackingList<?>> pair, IProblem problem, MyRandom rand) {
			if (rand.nextDouble() < 0.1) {
				return Pair.create(pair.first.getSymmetric(), pair.second);
			} else {
				return Pair.create(pair.first, (PackingList<?>) new BitFlipMutation().mutate(pair.second.copy(),problem,  rand));
			}
		}
	}

	@Override
	protected void initialize(IProblem p, IEvaluator eval, MyRandom rand) {

		// subproblems
		ThiefProblem problem = (ThiefProblem) p;
		SalesmanProblem tsp = new SalesmanProblem(problem.getMap());
		KnapsackProblem knp = new KnapsackProblem((int) problem.getMaxWeight(), problem.getItems());

		// calculate best and worst tour, and best picking plan
		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);
		PackingList<?> bestPacking = AlgorithmUtil.calcBestPackingPlan(problem, 1);

		// set the range for normalization
		range = new Range<>(2, null, null);
		
		// range for time
		range.setMinimum(0, tsp.evaluate(bestTour).getObjectives(0));
		range.setMaximum(0, problem.evaluate(new TTPVariable(bestTour, bestPacking)).getObjectives(0));
		
		// range for profit
		range.setMinimum(1, -knp.evaluate(bestPacking).getObjectives(0));
		range.setMaximum(1, 0d);

		algorithms.add(new HillClimbing(new TTPVariable(bestTour, new EmptyPackingListFactory().next(problem, rand)), new Mutation()));
		algorithms.add(new HillClimbing(new TTPVariable(bestTour.getSymmetric(), new EmptyPackingListFactory().next(problem, rand)) , new Mutation()));
		
	}
	



	public static void main(String[] args) {
		BasicConfigurator.configure();
		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_15_10_75.txt");
		p.setToMultiObjective(true);
		NonDominatedSolutionSet set = new ThiefDecomposedAlgorithm().run(p, new Evaluator(10000), new MyRandom());
		System.out.println(set);

	}

}
