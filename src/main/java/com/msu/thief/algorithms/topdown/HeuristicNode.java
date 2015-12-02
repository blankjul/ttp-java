package com.msu.thief.algorithms.topdown;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;

/**
 * This is a heuristic nodes which allows to iterate over different states
 * during the packing process.
 */
public class HeuristicNode {

	// ! item was is added.
	protected int idx = -1;
	
	// ! packing list for evaluating
	protected Set<Integer> currentIndices  = new LinkedHashSet<>();

	// ! all next indices
	protected Set<Integer> nextIndices = new LinkedHashSet<>();

	// ! father where this node was created from
	protected HeuristicNode father = null;

	// ! solution which was evaluated
	protected Solution solution = null;
	
	
	
	/**
	 * This constructor should be used if the father is unknown and the knapsack
	 * is empty so far
	 */
	protected HeuristicNode(Set<Integer> nextIndices) {
		this.idx = -1;
		this.father = null;
		this.nextIndices = nextIndices;
	}
	

	/**
	 * Create a node by using the father node. This expands the father node to a
	 * new one with a new item.
	 */
	protected HeuristicNode(HeuristicNode father, int idx) {
		this.idx = idx;
		this.father = father;

		if (father != null) currentIndices = new LinkedHashSet<>(father.currentIndices);
		boolean isAdded = currentIndices.add(idx);
		if (!isAdded) throw new RuntimeException("Item already contained.");
	}
	
	
	public Set<HeuristicNode> expand() {
		return expand(nextIndices);
	}
	
	public Set<HeuristicNode> expand(Set<Integer> indices) {
		Set<HeuristicNode> result = new HashSet<>();
		for(int next : indices) {
			result.add(new HeuristicNode(this, next));
		}
		return result;
	}
	
	
	public double getFitness() {
		return solution.getObjectives(0);
	}
	
	public Solution evaluate(IEvaluator eval, SingleObjectiveThiefProblem problem, Tour<?> tour) {
		if (solution == null) {
			PackingList<?> b = new BooleanPackingList(currentIndices, problem.numOfItems());
			this.solution = eval.evaluate(problem, new TTPVariable(tour, b));
		}
		return solution;
	}
	
	public boolean isBetterThanFather() {
		if (father == null) return true;
		else return getFitness() < father.getFitness();
	}


	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public Set<Integer> getNextIndices() {
		return nextIndices;
	}

	public void setNextIndices(Set<Integer> nextIndices) {
		this.nextIndices = nextIndices;
		this.nextIndices.remove(idx);
	}

	public HeuristicNode getFather() {
		return father;
	}

	public void setFather(HeuristicNode father) {
		this.father = father;
	}

	public Solution getSolution() {
		return solution;
	}


	public Set<Integer> getCurrentIndices() {
		return currentIndices;
	}


	public void setCurrentIndices(Set<Integer> currentIndices) {
		this.currentIndices = currentIndices;
	}
	
	


}
