package com.msu.thief.algorithms.heuristic;

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
import com.msu.interfaces.IProblem;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.model.Item;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class HeuristicGraphAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	
	protected Set<List<Boolean>> visited = null;
	
	private class Node {
		public List<Boolean> b = null;
		public int idx = -1;
		public Set<Integer> next = new HashSet<>();
		public Node father = null;
		public Solution s = null;
	}
	
	
	private List<Node> expand(IEvaluator evaluator, IProblem problem, Tour<?> tour, Node n) {
		
		List<Node> result = new ArrayList<>();
		
		Set<Integer> cNext = new HashSet<>(n.next);
		cNext.remove(n.idx);
		
		int counter = 0;
		for(Integer nextIdx : n.next) {
			
			Node exp = new Node();
			exp.idx = nextIdx;
			exp.next = cNext;
			exp.b = new ArrayList<>(n.b);
			exp.b.set(nextIdx, true);
			
			
			if (visited.contains(exp.b)) continue;
			
			exp.s = evaluator.evaluate(problem, new TTPVariable(tour,new BooleanPackingList(exp.b)));
			exp.father = n;
			
			if (!exp.s.hasConstrainViolations() && exp.s.getObjectives(0) < n.s.getObjectives(0)) {
				result.add(exp);
			};
			
			
		}
		
		return result;
	}
	
	
	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {

		Tour<?> pi = AlgorithmUtil.calcBestTour(problem);
		
		// create the open list sorted by current best value
		Queue<Node> open = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return Double.compare(n1.s.getObjectives(0), n2.s.getObjectives(0));
			}
		});
		visited = new HashSet<>();
		
		
		Node root = new Node();
		root.b = new EmptyPackingListFactory().next(problem, rand).get();
		root.s = evaluator.evaluate(problem, new TTPVariable(pi, new BooleanPackingList(root.b)));
		for (int i = 0; i < problem.numOfItems(); i++) {
			root.next.add(i);
		}
		open.add(root);
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		while (evaluator.hasNext() && !open.isEmpty()) {
			
			Node n = open.poll();
			
			visited.add(n.b);
			
			List<Node> children = expand(evaluator, problem, pi, n);
			for (Node child : children) {
				
				// if we got worse by picking that item
				open.add(child);
				set.add(child.s);
				
				System.out.println(Arrays.toString(new BooleanPackingList(child.b).toIndexSet().toArray()));
				
			}
			//System.out.println(Arrays.toString(new BooleanPackingList(n.b).toIndexList().toArray()));
			System.out.println(open.size());
		}
		

		
		return set.get(0);
	}

	

	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();

		
		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
		HeuristicGraphAlgorithm heuristic = new HeuristicGraphAlgorithm();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(Integer.MAX_VALUE), new MyRandom());

		System.out.println(set);
		System.out.println(Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

		
		BooleanPackingList bpl = new BooleanPackingList(
				"[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0]");

		Tour<?> bestTour = AlgorithmUtil.calcBestTour(p);
		System.out.println(p.evaluate(new TTPVariable(bestTour, bpl)));
		System.out.println(Arrays.toString(bpl.toIndexSet().toArray()));
		for (Integer idx : bpl.toIndexSet()) {
			List<Double> deltaObj = HeuristicUtil.calcDeltaObjectives(p, new Evaluator(Integer.MAX_VALUE), bestTour, bpl,  idx);
			System.out.println(String.format("%s %s", idx, deltaObj.get(0)));
		}
	}

}
