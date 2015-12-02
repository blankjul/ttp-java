package com.msu.thief.algorithms.topdown;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.utility.ListIterate;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.OnePlusOneEA;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class AprioriAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	
	final public int NUM_OF_POPULATION = 200;
	
	protected NonDominatedSolutionSet set;
	
	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {

		set = new NonDominatedSolutionSet();
		Tour<?> tour = AlgorithmUtil.calcBestTour(problem);

		Solution empty1 = eval.evaluate(problem, new TTPVariable(tour, new EmptyPackingListFactory().next(problem, rand)));
		Solution empty2 = eval.evaluate(problem, new TTPVariable(tour.getSymmetric(), new EmptyPackingListFactory().next(problem, rand)));
		set.add(empty1);
		set.add(empty2);
		
		
		MutableList<AprioriNode> population1 = new FastList<>();
		MutableList<AprioriNode> population2 = new FastList<>();
		
		for (int i = 0; i < problem.numOfItems(); i++) {
			AprioriNode n1 = new AprioriNode(i, new HashSet<>(Arrays.asList(i)), empty1.getObjectives(0));
			n1.evaluate(eval, problem, tour);
			population1.add(n1);
			set.add(n1.solution);
			
			AprioriNode n2 = new AprioriNode(i, new HashSet<>(Arrays.asList(i)), empty2.getObjectives(0));
			n2.evaluate(eval, problem, tour.getSymmetric());
			population2.add(n2);
			set.add(n2.solution);
		}
		
		
		// prune all nodes which are worse than the father
		ListIterate.removeIf(population1, AprioriNode::isWorseThanFather);
		population1.sortThisByDouble(s -> s.getFitness());
		ListIterate.removeIf(population2, AprioriNode::isWorseThanFather);
		population2.sortThisByDouble(s -> s.getFitness());
		
		
		while (eval.hasNext() && !(population1.isEmpty()&& population2.isEmpty())) {
			
			next(problem, eval, tour, population1);
			next(problem, eval, tour.getSymmetric(), population2);
			// nodes = new FastList<>(next.subList(0, Math.min(NUM_OF_POPULATION, next.size())));
			
			//nodes = new FastList<>(next);
			
			//System.out.println(nodes.size());
		}
		
		
		return set.get(0);
	}

	
	protected MutableList<AprioriNode> next(SingleObjectiveThiefProblem problem, IEvaluator eval, Tour<?> tour, MutableList<AprioriNode> nodes) {
				
		for (AprioriNode n : nodes.subList(0, Math.min(10, nodes.size()))) {
			System.out.println(String.format("%s -> %s", n.getFitness(), Arrays.toString(n.currentIndices.toArray())));
		}
		System.out.println("----------------------------------------");
		
		
		MutableList<AprioriNode> next = new FastList<>();
		
		// for every node in list
		for (int i = 0; i < nodes.size(); i++) {
			
			AprioriNode father = nodes.get(i);
			
			// create all possible combinations with all nodes in that level
			for (int j = i + 1; j < nodes.size(); j++) {
				
				
				if (!eval.hasNext()) break;
				
				final int nextIdx = nodes.get(j).idx;
				
				Set<Integer> indices = new HashSet<>(father.currentIndices);
				boolean added = indices.add(nextIdx);
				if (!added) {
					System.out.println();
				}
				
				AprioriNode n = new AprioriNode(nextIdx, indices, father.getFitness());
				n.evaluate(eval, problem, tour);
				
				if (!n.isWorseThanFather()) {
					set.add(n.solution);
					next.add(n);
				}
				
			}
			
		}
		
		next.sortThisByDouble(s -> s.getFitness());
		
		
		Set<Set<Integer>> hash = new HashSet<>();
		nodes.clear();
		for (int i = 0; i < next.size(); i++) {
			if (nodes.size() > NUM_OF_POPULATION) break;
			
			AprioriNode n = next.get(i);
			
			if (hash.contains(n.currentIndices)) continue;
			else {
				hash.add(n.currentIndices);
				nodes.add(n);
			}
			
		}
		
		return nodes;
		
	}
	
	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/20/20_5_6_75.txt");
		AprioriAlgorithm heuristic = new AprioriAlgorithm();
		OnePlusOneEA ea = new OnePlusOneEA(false);
		ea.checkSymmetric = true;
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(500000), new MyRandom(564321));

		System.out.println(set);
		System.out.println(
				Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));
/*
		BooleanPackingList bpl = new BooleanPackingList(
				"[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0]");

		Tour<?> bestTour = AlgorithmUtil.calcBestTour(p);
		System.out.println(p.evaluate(new TTPVariable(bestTour, bpl)));
		System.out.println(Arrays.toString(bpl.toIndexSet().toArray()));
		for (Integer idx : bpl.toIndexSet()) {
			List<Double> deltaObj = HeuristicUtil.calcDeltaObjectives(p, new Evaluator(Integer.MAX_VALUE), bestTour,
					bpl, idx);
			System.out.println(String.format("%s %s", idx, deltaObj.get(0)));
		}*/
	}

}
