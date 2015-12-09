package com.msu.thief.algorithms.fixed.frequent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.SingleObjectiveThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.util.MyRandom;

public class FrequentPatternMiningAlgorithm
		extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblemWithFixedTour> {

	final public int NUM_OF_POPULATION = 500;

	// ! evaluator for this problem. set for every run
	protected IEvaluator eval;

	// ! problem for this run
	protected SingleObjectiveThiefProblemWithFixedTour problem;

	// ! random generator
	protected MyRandom rand;

	@Override
	public Solution run___(SingleObjectiveThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		// initialize the variables
		this.problem = problem;
		this.eval = eval;
		this.rand = rand;
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		solve(set);
		return set.get(0);
	}

	private void solve(NonDominatedSolutionSet set) {

		Solution empty = eval.evaluate(problem,new EmptyPackingListFactory().next(problem, rand));
		set.add(empty);

		
		MutableList<FrequentItemSetSolution> entries = new FastList<>();
		for (int i = 0; i < problem.numOfItems(); i++) {
			if (!eval.hasNext()) break;
			FrequentItemSetSolution entry = FrequentItemSetSolution.create(eval, problem, new HashSet<>(Arrays.asList(i)));
			set.add(entry);
			
			if (empty.getObjectives(0) > entry.getObjectives(0)) {
				entries.add(entry);
			}
			
		}
		
		
		while (eval.hasNext() && !entries.isEmpty()) {
			
			
			// generate a hash set with all possible items
			Set<Integer> hash = new HashSet<>(problem.numOfItems());
			//for (int i = 0; i < problem.numOfItems(); i++) hash.add(i);
			for (FrequentItemSetSolution e : entries) hash.addAll(e.items);

			// the resulting hash map that has upper bounds for every entry
			Map<FrequentItemSetSolution, Double> next = new HashMap<>(40000);

			// for every entry of the last level
			for (FrequentItemSetSolution e : entries) {

				// for each possible new combination
				for (Integer i : hash) {

					// would not be a new combination
					if (e.items.contains(i)) continue;

					Set<Integer> items = new HashSet<>(e.items);
					items.add(i);

					FrequentItemSetSolution entry = FrequentItemSetSolution.create(new Evaluator(Integer.MAX_VALUE), problem, items);
					set.add(entry);

					// if new found index list or if better upper bound is better
					if (!next.containsKey(entry) || e.getObjectives(0) < next.get(entry)) {
						next.put(entry, e.getObjectives(0));
					}
				}
			}

			entries.clear();
			for (Entry<FrequentItemSetSolution, Double> e : next.entrySet()) {
				if (e.getKey().getObjectives(0) < e.getValue()) entries.add(e.getKey());
			}

			Collections.sort(entries, (FrequentItemSetSolution s1, FrequentItemSetSolution s2) -> s1.getObjectives(0).compareTo(s2.getObjectives(0)));
			//entries = new FastList<>(entries.subList(0, Math.min(entries.size(), NUM_OF_POPULATION)));
			
/*			MutableList<FrequentItemSetSolution> nextTmp = new FastList<>();
			for (FrequentItemSetSolution s : entries) s.singleObjective = false;
			for(NonDominatedSolutionSet front : new NaiveNonDominatedSorting().run(entries)) {
				for (Solution solution : front) {
					nextTmp.add((FrequentItemSetSolution) solution);
				}
				if (nextTmp.size() > NUM_OF_POPULATION) break;
			}
			for (FrequentItemSetSolution s : entries) s.singleObjective = true;
			entries = nextTmp;
			*/
			
			//System.out.println(Arrays.toString(hash.toArray()));
			report(entries, 10);
			//System.out.println();
			
		}

		
	}
	
	

	protected void report(MutableList<FrequentItemSetSolution> entries, Integer n) {
		if (n == null) n = entries.size();
		Collections.sort(entries, (e1, e2) -> Double.compare(e1.getObjectives(0), e2.getObjectives(0)));
		for (FrequentItemSetSolution e : entries.subList(0, Math.min(entries.size(), n))) {
			System.out.println(String.format("%s -> %s", e.getObjectives(0), Arrays.toString(e.items.toArray())));
		
			e.singleObjective = false;
			//System.out.println(String.format("%s,%s", e.getObjectives(0), e.getObjectives(1)));
			e.singleObjective = true;
		
		}
		System.out.println(entries.size());
		System.out.println("---------------------------");
	}


	
}
