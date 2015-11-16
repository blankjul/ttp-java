package com.msu.algorithms;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.io.reader.BonyadiSingleObjectiveReader;
import com.msu.model.Evaluator;
import com.msu.moo.algorithms.DecomposedAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.operators.AbstractMutation;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.problems.KnapsackProblem;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.SingleObjectiveThiefProblem;
import com.msu.problems.ThiefProblem;
import com.msu.soo.algorithms.HillClimbing;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;
import com.msu.util.Random;
import com.msu.util.Range;

public class ThiefDecomposedAlgorithm extends DecomposedAlgorithm {

	// ! number of decomposed problem that are solved
	protected int numOfRuns = 50;

	class Mutation extends AbstractMutation<Pair<Tour<?>, PackingList<?>>> {

		@Override
		protected void mutate_(Pair<Tour<?>, PackingList<?>> pair, Random rand) {
			if (rand.nextDouble() < 0.1) {
				pair.first = pair.first.getSymmetric();
			} else {
				pair.second = (PackingList<?>) new BitFlipMutation().mutate(pair.second.copy(), rand);
			}
		}
	}

	@Override
	protected void initialize(IProblem p, IEvaluator eval, Random rand) {

		// subproblems
		ThiefProblem problem = (ThiefProblem) p;
		SalesmanProblem tsp = new SalesmanProblem(problem.getMap());
		KnapsackProblem knp = new KnapsackProblem((int) problem.getMaxWeight(), problem.getItems());

		// calculate best and worst tour, and best picking plan
		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);
		SymmetricMap m = problem.getMap().copy().multipleCosts(-1);
		Tour<?> slowestTour = AlgorithmUtil.calcBestTour(new SalesmanProblem(m));
		PackingList<?> bestPacking = AlgorithmUtil.calcBestPackingPlan(problem, 1);

		// set the range for normalization
		Range<Double> r = new Range<>();
		r.add(Arrays.asList(tsp.evaluate(bestTour).getObjectives(0), 0d));
		r.add(Arrays.asList(tsp.evaluate(slowestTour).getObjectives(0), -knp.evaluate(bestPacking).getObjectives(0)));

		PackingList<?> b = new EmptyPackingListFactory().next(problem, rand);
		
		algorithms.add(new HillClimbing(new TTPVariable(bestTour, b), new Mutation()));
		algorithms.add(new HillClimbing(new TTPVariable(bestTour.getSymmetric(), b) , new Mutation()));
		
	}



	public static void main(String[] args) {
		BasicConfigurator.configure();
		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_15_10_75.txt");
		p.setToMultiObjective(true);
		NonDominatedSolutionSet set = new ThiefDecomposedAlgorithm().run(p, new Evaluator(10000), new Random());
		System.out.println(set);

	}

}
