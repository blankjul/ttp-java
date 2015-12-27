package com.msu.thief.algorithms.coevolution;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.algorithms.coevolution.selector.ASelector;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPacking;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.exceptions.EvaluationException;

public class CoevoluationCollaborativeEvaluator extends Evaluator{


	protected SolutionSet collaboratingTours;
	
	protected SolutionSet collaboratingPackingLists;
	
	protected TTPVariable best = null;
	
	protected NonDominatedSolutionSet bestSet = new NonDominatedSolutionSet();
	
	protected ASelector selector;
	
	protected MyRandom rand;
	
	
	
	public CoevoluationCollaborativeEvaluator(int maxEvaluations, ASelector selector, MyRandom rand) {
		super(maxEvaluations);
		this.selector = selector;
		this.rand = rand;
	}


	@Override
	public Solution evaluate(IProblem problem, IVariable variable) {
		
		if (evaluations >= (int) (maxEvaluations * 1.20)) 
			throw new EvaluationException("Evaluations expired. Check hasNext() first.");
		
		
		NonDominatedSolutionSet currentSet = new NonDominatedSolutionSet();
		
		AbstractThiefProblem thief = (AbstractThiefProblem) problem;
		
		
		if (variable instanceof Tour<?>) {
			Tour<?> tour = (Tour<?>) variable;
			for (IVariable collaborator : selector.select(collaboratingPackingLists, rand)) {
				PackingList<?> pack = (PackingList<?>) collaborator;
				ThiefProblemWithFixedPacking fixedPackProblem = new ThiefProblemWithFixedPacking( thief, pack );
				Solution s = fixedPackProblem.evaluate(tour);
				currentSet.add(s);
				if (bestSet.add(s)) {
					best = new TTPVariable(tour,pack);
				}
				increase();
			}
		} else if (variable instanceof PackingList<?>) {
			PackingList<?> pack = (PackingList<?>) variable;
			for (IVariable collaborator : selector.select(collaboratingTours, rand)) {
				Tour<?> tour = (Tour<?>) collaborator;
				ThiefProblemWithFixedTour fixedTourProblem = new ThiefProblemWithFixedTour(thief, tour );
				Solution s = fixedTourProblem.evaluate(pack);
				currentSet.add(s);
				if (bestSet.add(s)) {
					best = new TTPVariable(tour,pack);
				}
				increase();
			}
			
		} else {
			throw new RuntimeException("Unknown variable. It has to be either a tour or a packing list!");
		}
		
		if (currentSet.size() == 0) throw new RuntimeException("No collaborating packing lists are provided.");
		
		return currentSet.get(0);
	}


	
	public Evaluator createChildEvaluator(int maxEvaluations, ASelector selector, MyRandom rand) {
		CoevoluationCollaborativeEvaluator eval = new CoevoluationCollaborativeEvaluator(maxEvaluations, selector, rand);
		eval.father = this;
		return eval;
	}


	public SolutionSet getCollaboratingTours() {
		return collaboratingTours;
	}


	public void setCollaboratingTours(SolutionSet collaboratingTours) {
		this.collaboratingTours = collaboratingTours;
	}


	public SolutionSet getCollaboratingPackingLists() {
		return collaboratingPackingLists;
	}


	public void setCollaboratingPackingLists(SolutionSet collaboratingPackingLists) {
		this.collaboratingPackingLists = collaboratingPackingLists;
	}


	
	
	


}
