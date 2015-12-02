package com.msu.thief.algorithms.topdown;

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
public class AprioriNode {

	// ! packing list for evaluating
	protected Set<Integer> currentIndices  = new LinkedHashSet<>();

	// ! solution which was evaluated
	protected Solution solution = null;
	
	protected int idx = -1;
	
	protected double fatherFitness;
	
	
	/**
	 * This constructor should be used if the father is unknown and the knapsack
	 * is empty so far
	 */
	protected AprioriNode(int idx, Set<Integer> currentIndices, double fatherFitness) {
		this.idx = idx;
		this.currentIndices = currentIndices;
		this.fatherFitness = fatherFitness;
	}
	
	
	public boolean isWorseThanFather() {
		return  fatherFitness < getFitness();
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
	
	

	public Solution getSolution() {
		return solution;
	}



}
