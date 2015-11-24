package com.msu.thief.algorithms.topdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.heuristic.HeuristicUtil;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class TopDownHeuristicAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	protected Set<HeuristicNode> visited = new HashSet<>();
	
	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		Tour<?> pi = AlgorithmUtil.calcBestTour(problem);

		Set<Integer> allIndices = new HashSet<>();
		for (int i = 0; i < problem.numOfItems(); i++) {
			allIndices.add(i);
		}
		HeuristicNode root = new HeuristicNode(allIndices);
		root.evaluate(eval, problem, pi);
		HeuristicTree tree = new HeuristicTree(root);
		

		// create the open list sorted by current best value
		Queue<HeuristicNode> open = new PriorityQueue<>(new Comparator<HeuristicNode>() {
			@Override
			public int compare(HeuristicNode n1, HeuristicNode n2) {
				return Double.compare(n1.getFitness(), n2.getFitness());
			}
		});
		open.add(tree.getRoot());
		visited.add(tree.getRoot());

		while (eval.hasNext() && !open.isEmpty()) {

			
			HeuristicNode n = open.poll();
			List<Pair<Integer,Double>> nextIndices = new ArrayList<>();
			
			Set<HeuristicNode> children = n.expand();
			
			for (HeuristicNode child : children) {
				
				child.evaluate(eval, problem, pi);
				
				if (!child.getSolution().hasConstrainViolations() && child.isBetterThanFather() 
						&& !visited.contains(child)) {
					// if we got worse by picking that item
					open.add(child);
					visited.add(child);
					set.add(child.getSolution());
					nextIndices.add(Pair.create(child.idx, child.getFitness() - n.getFitness()));
					//System.out.println(Arrays.toString(child.currentIndices.toArray()));
				}
			}
			
			
			Collections.sort(nextIndices, Comparator.comparing(p -> p.second));
			
			for (HeuristicNode child : children) {
				Set<Integer> next = new HashSet<>();
				final int numOfNext = Math.min(nextIndices.size(), 20);
				for (int i = 0; i < numOfNext; i++) {
					if (nextIndices.get(i).second < 0) next.add(nextIndices.get(i).first);
				}
				child.setNextIndices(next);
			}
			
			// System.out.println(open.size());
			// System.out.println(Arrays.toString(new BooleanPackingList(n.b).toIndexList().toArray()));

		}
		return set.get(0);
	}

	
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
		TopDownHeuristicAlgorithm heuristic = new TopDownHeuristicAlgorithm();
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
