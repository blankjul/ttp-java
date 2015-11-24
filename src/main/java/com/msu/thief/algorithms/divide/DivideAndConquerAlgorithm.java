package com.msu.thief.algorithms.divide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.heuristic.HeuristicUtil;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.factory.RandomPoolPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.FixedTourFactory;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class DivideAndConquerAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {

		Tour<?> tour = AlgorithmUtil.calcBestTour(problem);
		Set<Integer> indices = solve(problem, eval, tour, 0, problem.numOfItems() - 1);

		Solution s = eval.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(indices, problem.numOfItems())));
		
		
		double best = eval
				.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(indices, problem.numOfItems())))
				.getObjectives(0);
		
		
		/*
		for (int i = 0; i < problem.numOfItems(); i++) {
			if (indices.contains(i)) continue;
			BooleanPackingList b = new BooleanPackingList(indices, problem.numOfItems());
			b.get().set(i, !b.get().get(i));
			double fitness = eval.evaluate(problem, new TTPVariable(tour, b)).getObjectives(0);
			System.out.println(String.format("%s %s %s", i, b.get().get(i), fitness - best));
		}
		
		System.out.println("--------------------");
		for (int i : indices) {
			BooleanPackingList b = new BooleanPackingList(indices, problem.numOfItems());
			b.get().set(i, !b.get().get(i));
			double fitness = eval.evaluate(problem, new TTPVariable(tour, b)).getObjectives(0);
			System.out.println(String.format("%s %s %s", i, b.get().get(i), fitness - best));
		}
		System.out.println("--------------------");
		*/
		return s;
	}

	protected Pair<Set<Integer>, Double> prune(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour, Set<Integer> set) {
		boolean improved = true;
		while (!set.isEmpty() && improved) {
			improved = false;
			double best = eval
					.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(set, problem.numOfItems())))
					.getObjectives(0);

			List<Double> l = new ArrayList<>();
			List<Integer> indices = new ArrayList<>(set);

			for (Integer idx : indices) {
				BooleanPackingList b = new BooleanPackingList(set, problem.numOfItems());
				b.get().set(idx, false);
				double fitness = eval.evaluate(problem, new TTPVariable(tour, b)).getObjectives(0);
				l.add(fitness - best);
			}

			double min = Collections.min(l);

			if (min < 0) {
				improved = true;
				int idx = indices.get(l.indexOf(min));
				set.remove(idx);
			}
		}
		
		double fitness = eval
				.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(set, problem.numOfItems())))
				.getObjectives(0);
		
		return Pair.create(set, fitness);
		
	}

	protected Set<Integer> solve(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour, int start,
			int end) {

		if (start == end) {
			return new HashSet<>(Arrays.asList(start));
		}

		int middle = start + (end - start) / 2;
		Set<Integer> left = solve(problem, eval, tour, start, middle);
		Set<Integer> right = solve(problem, eval, tour, middle + 1, end);
/*
		Pair<Set<Integer>, Double> aMerged = merge(problem, eval, tour, left,right);
		Pair<Set<Integer>, Double> bMerged = merge(problem, eval, tour,right, left);
		
		aMerged = prune(problem, eval, tour, aMerged.first);
		bMerged = prune(problem, eval, tour, bMerged.first);
		*/
		return mergeEvolution(problem, eval, tour, left, right).first;

	}
	
	
	protected Pair<Set<Integer>, Double> mergeEvolution(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour,
			Set<Integer> left, Set<Integer> right) {
		
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> singleEAFrame = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		singleEAFrame
		.set("populationSize", 5)
		.set("probMutation", 0.3)
		.set("factory", new TTPVariableFactory(new FixedTourFactory(tour), new RandomPoolPackingListFactory(Arrays.asList(new BooleanPackingList(left, problem.numOfItems()), new BooleanPackingList(right, problem.numOfItems())))))
		.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
		.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()));
		
		NonDominatedSolutionSet set = singleEAFrame.build().run(problem, new Evaluator(200), new MyRandom());
		
		Solution best = set.get(0);
		TTPVariable var = (TTPVariable)best.getVariable();
		
		return Pair.create(var.getPackingList().toIndexSet(), best.getObjectives(0));
	}
	

	protected Pair<Set<Integer>, Double> merge(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour,
			Set<Integer> left, Set<Integer> right) {
		Set<Integer> merged = new HashSet<>(left);
		double best = eval
				.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(merged, problem.numOfItems())))
				.getObjectives(0);

		for (Integer next : right) {
			merged.add(next);
			double fitnessMerged = eval
					.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(merged, problem.numOfItems())))
					.getObjectives(0);
			if (best <= fitnessMerged)
				merged.remove(next);
			else
				best = fitnessMerged;
		}
		return Pair.create(merged, best);
	}

	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
		DivideAndConquerAlgorithm heuristic = new DivideAndConquerAlgorithm();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(500000), new MyRandom());

		System.out.println(set);
		System.out.println(
				Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

		BooleanPackingList bpl = new BooleanPackingList(
				"[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0]");

		Tour<?> bestTour = AlgorithmUtil.calcBestTour(p);
		System.out.println(p.evaluate(new TTPVariable(bestTour, bpl)));
		System.out.println(Arrays.toString(bpl.toIndexSet().toArray()));
		for (Integer idx : bpl.toIndexSet()) {
			List<Double> deltaObj = HeuristicUtil.calcDeltaObjectives(p, new Evaluator(Integer.MAX_VALUE), bestTour,
					bpl, idx);
			System.out.println(String.format("%s %s", idx, deltaObj.get(0)));
		}
	}

}
