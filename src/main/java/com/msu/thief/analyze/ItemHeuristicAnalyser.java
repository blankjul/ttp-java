package com.msu.thief.analyze;

import java.util.Map;
import java.util.Map.Entry;

import com.msu.model.Evaluator;
import com.msu.thief.heuristics.ItemAverageCaseHeuristic;
import com.msu.thief.heuristics.ItemBestCaseHeuristic;
import com.msu.thief.heuristics.ItemHeuristic;
import com.msu.thief.heuristics.ItemWorstCaseHeuristic;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.util.Util;

public class ItemHeuristicAnalyser {

	
	
	public static void main(String[] args) {
		
		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) new JsonThiefProblemReader().read("resources/my_publication_coordinates.ttp");
		ThiefProblemWithFixedTour fixed = new ThiefProblemWithFixedTour(problem, new StandardTour("0,1,2,3"));
		
		ItemHeuristic heur = new ItemBestCaseHeuristic(fixed, new Evaluator(Integer.MAX_VALUE));
		Map<Integer, Double> m = heur.calc(Util.createIndex(problem.numOfItems()));
		
		for (Entry<Integer, Double> entry : m.entrySet()) {
			System.out.println(String.format("%s %s", entry.getKey(), entry.getValue()));
		}
		
		System.out.println("-----------------------------------------");
		
		
		heur = new ItemWorstCaseHeuristic(fixed, new Evaluator(Integer.MAX_VALUE));
		m = heur.calc(Util.createIndex(problem.numOfItems()));
		
		for (Entry<Integer, Double> entry : m.entrySet()) {
			System.out.println(String.format("%s %s", entry.getKey(), entry.getValue()));
		}
		
		System.out.println("-----------------------------------------");
		
		
		heur = new ItemAverageCaseHeuristic(fixed, new Evaluator(Integer.MAX_VALUE));
		m = heur.calc(Util.createIndex(problem.numOfItems()));
		
		for (Entry<Integer, Double> entry : m.entrySet()) {
			System.out.println(String.format("%s %s", entry.getKey(), entry.getValue()));
		}
		
		
		System.out.println("-----------------------------------------");
		
	}
	
}
