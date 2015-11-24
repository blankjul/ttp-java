package com.msu.thief.algorithms.heuristic;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;

public class HeuristicUtil {

	public static List<Double> calcDeltaObjectives(ThiefProblem problem, IEvaluator evaluator, Tour<?> tour, int indexOfItem) {
		PackingList<?> b = new EmptyPackingListFactory().next(problem, null);
		return HeuristicUtil.calcDeltaObjectives(problem, evaluator, tour, b, indexOfItem);
	}
	
	
	public static List<Double> calcDeltaObjectives(ThiefProblem problem, IEvaluator evaluator, Tour<?> tour, PackingList<?> b, int indexOfItem) {
		
		Solution before = evaluator.evaluate(problem, new TTPVariable(tour, b));
		b.get().set(indexOfItem, !b.get().get(indexOfItem));
		Solution after = evaluator.evaluate(problem, new TTPVariable(tour, b));
		
		List<Double> v = new ArrayList<>();
		for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
			v.add(after.getObjectives(i) - before.getObjectives(i));
		}
		b.get().set(indexOfItem, !b.get().get(indexOfItem));
		return v;
	}
	
	
	public static double calcDeltaSingleObjective(SingleObjectiveThiefProblem problem, double deltaTime, double deltaProfit) {
		return  + problem.getR() *  deltaTime - deltaProfit;
	}
	
	
	public static double calcDeltaTime(ThiefProblem problem, Tour<?> tour, int indexOfItem) {
		return HeuristicUtil.calcDeltaTime(problem, tour, indexOfItem, problem.getMaxSpeed());
	}
	
	public static double calcDeltaTime(ThiefProblem problem, Tour<?> tour, int indexOfItem, double velocityOverAllCities) {
		
		ItemCollection<Item> items = problem.getItemCollection();
		
		List<Integer> pi = tour.encode();
		int tourIndexOfPick = pi.indexOf(items.getCityOfItem(indexOfItem));
		
		final double deltaVelocity = HeuristicUtil.calcDeltaVelocity(problem, indexOfItem);
		
		double deltaTime = 0;
		
		for (int i = tourIndexOfPick; i < pi.size(); i++) {
			double distance = problem.getMap().get(pi.get(i), pi.get((i + 1) % pi.size()));
			deltaTime += distance / (velocityOverAllCities - deltaVelocity) - distance / velocityOverAllCities;
		}
		
		return deltaTime;
	}
	
	public static double calcDeltaVelocity(ThiefProblem problem, int indexOfItem) {
		double weight = problem.getItems().get(indexOfItem).getWeight();
		double speedWithItem =  problem.getMaxSpeed() - 
				((problem.getMaxSpeed() - problem.getMinSpeed()) / problem.getMaxWeight()) * weight;
		return problem.getMaxSpeed() - speedWithItem;
	}
	
	
	
	
}
