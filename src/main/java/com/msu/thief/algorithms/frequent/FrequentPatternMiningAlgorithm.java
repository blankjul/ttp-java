package com.msu.thief.algorithms.frequent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.OnePlusOneEA;
import com.msu.thief.algorithms.apriori.AprioriEntry;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class FrequentPatternMiningAlgorithm
		extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	final public int NUM_OF_POPULATION = -1;

	// ! evaluator for this problem. set for every run
	protected IEvaluator eval;

	// ! problem for this run
	protected SingleObjectiveThiefProblem problem;

	// ! random generator
	protected MyRandom rand;

	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {

		// initialize the variables
		this.problem = problem;
		this.eval = eval;
		this.rand = rand;
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		// calculate best tours
		Tour<?> tour = AlgorithmUtil.calcBestTour(problem).getSymmetric();
		Tour<?> symmetricTour = tour.getSymmetric();

		solve(tour, set);
		solve(symmetricTour, set);

		return set.get(0);
	}

	private void solve(Tour<?> tour, NonDominatedSolutionSet set) {

		Solution empty = eval.evaluate(problem,
				new TTPVariable(tour, new EmptyPackingListFactory().next(problem, rand)));
		set.add(empty);

		MutableList<AprioriEntry> entries = new FastList<>();
		for (int i = 0; i < problem.numOfItems(); i++) {
			if (!eval.hasNext()) break;
			AprioriEntry entry = new AprioriEntry(i, new HashSet<>(Arrays.asList(i)));
			entry.evaluate(eval, problem, tour);
			if (empty.getObjectives(0) > entry.solution.getObjectives(0)) {
				entries.add(entry);
				set.add(entry.solution);
			}
		}


		while (eval.hasNext() && !entries.isEmpty()) {
			
			Collections.sort(entries, (e1, e2) -> Double.compare(e1.solution.getObjectives(0), e2.solution.getObjectives(0)));
			
			if (NUM_OF_POPULATION != -1) {
				//System.out.println(String.format("Pruned from %s to %s", entries.size(),  Math.min(entries.size(), NUM_OF_POPULATION)));
				//Collections.shuffle(entries);
				//rand.shuffle(entries);
				entries = new FastList<>(entries.subList(0, Math.min(entries.size(), NUM_OF_POPULATION)));
			}
			
			
			
			

			// generate a hash set with all possible items
			Set<Integer> hash = new HashSet<>();
			for (AprioriEntry e : entries) hash.addAll(e.items);

			// the resulting hash map that has upper bounds for every entry
			Map<AprioriEntry, Double> next = new HashMap<>();

			// for every entry of the last level
			for (AprioriEntry e : entries) {

				// for each possible new combination
				for (Integer i : hash) {

					// would not be a new combination
					if (e.items.contains(i)) continue;

					Set<Integer> items = new HashSet<>(e.items);
					items.add(i);

					AprioriEntry entry = new AprioriEntry(i, items);
					entry.evaluate(new Evaluator(Integer.MAX_VALUE), problem, tour);
					set.add(entry.solution);

					// if new found index list or if better upper bound is better
					if (!next.containsKey(entry) || e.solution.getObjectives(0) < next.get(entry)) {
						next.put(entry, e.solution.getObjectives(0));
					}
				}
			}

			entries.clear();
			for (Entry<AprioriEntry, Double> e : next.entrySet()) {
				if (e.getKey().solution.getObjectives(0) < e.getValue()) entries.add(e.getKey());
			}

			
			//System.out.println(Arrays.toString(hash.toArray()));
			report(entries, null);
			//System.out.println();
			
		}

	}

	protected void report(MutableList<AprioriEntry> entries, Integer n) {
		if (n == null) n = entries.size();
		Collections.sort(entries, (e1, e2) -> Double.compare(e1.solution.getObjectives(0), e2.solution.getObjectives(0)));
		for (AprioriEntry e : entries.subList(0, Math.min(entries.size(), n))) {
			System.out.println(
					String.format("%s -> %s", e.solution.getObjectives(0), Arrays.toString(e.items.toArray())));
		}
		System.out.println(entries.size());
		System.out.println("---------------------------");
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				//.read("../ttp-benchmark/SingleObjective/10/10_5_6_25.txt");
				//.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
				// .read("../ttp-benchmark/SingleObjective/10/10_15_10_75.txt");
				.read("../ttp-benchmark/SingleObjective/20/20_5_6_75.txt");
		//.read("../ttp-benchmark/SingleObjective/20/20_30_9_25.txt");
		// .read("../ttp-benchmark/SingleObjective/50/50_15_8_50.txt");
		// .read("../ttp-benchmark/SingleObjective/100/100_5_10_50.txt");

		OnePlusOneEA ea = new OnePlusOneEA(false);
		ea.checkSymmetric = true;
		ea.setName("1+1-EA-SYM");
		
		FrequentPatternMiningAlgorithm heuristic = new FrequentPatternMiningAlgorithm();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(500000), new MyRandom(123456));

		System.out.println(set);
		System.out.println(
				Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

	}

}
