package com.msu.thief.algorithms.heuristic;

import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.bilevel.tour.SolveKnapsackWithHeuristicValues;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.ICityProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class TourHeuristicProblem extends AProblem<Tour<?>> implements ICityProblem{

	protected SingleObjectiveThiefProblem thief;
	
	protected IEvaluator eval;
	
	
	public TourHeuristicProblem(SingleObjectiveThiefProblem thief, IEvaluator eval) {
		super();
		this.thief = thief;
		this.eval = eval;
	}


	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	
	
	@Override
	protected void evaluate_(Tour<?> var, List<Double> objectives, List<Double> constraintViolations) {
		
		//TourInformation tourInfo = new StandardTimeEvaluator().evaluate_(thief, var, new IntegerSetPackingList(thief.numOfItems()));
		//objectives.add(tourInfo.getTime());
		
		// create local evaluator with maximal 50000 evaluations
		IEvaluator evalLocal = eval.createChildEvaluator(50000);
		
		Solution local = new SolveKnapsackWithHeuristicValues().run___(new ThiefProblemWithFixedTour(thief, var), evalLocal, new MyRandom());
		//objectives.add(local.getObjectives(0));
		
		objectives.add(local.getObjectives(0));
		
		//double heuristic = TourHeuristic.calcHeuristic(thief, var);
		//objectives.add(- heuristic);
		
	}


	@Override
	public int numOfCities() {
		return thief.numOfCities();
	}


	@Override
	public SymmetricMap getMap() {
		return thief.getMap();
	}


	@Override
	public double getMaxSpeed() {
		return thief.getMaxSpeed();
	}
	
	
	

}
