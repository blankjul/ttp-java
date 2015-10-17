package com.msu;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.algorithms.KnapsackCombo;
import com.msu.algorithms.SalesmanLinKernighanHeuristic;
import com.msu.io.reader.BonyadiSingleObjectiveReader;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.FileCollectorParser;
import com.msu.problems.KnapsackProblem;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.SingleObjectiveThiefProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;

public class ThiefProblemAnalyzer {

	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		
		
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/SingleObjective/10", "10_3_1_50.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/TSPLIB/berlin52-ttp", "berlin52_n51_bounded-strongly-corr_01.ttp", new BonyadiTSPLIBReader());
		List<ThiefProblem> collected = fcp.collect();
		
		collected.forEach((p) -> {
			if (p instanceof SingleObjectiveThiefProblem) 
				((SingleObjectiveThiefProblem)p).setToMultiObjective(true);
		});
		
		
		
		
		for (ThiefProblem problem : collected) {
			
			KnapsackProblem knp = new KnapsackProblem(problem.getMaxWeight(), problem.getItems());
			
			
			NonDominatedSolutionSet set = new KnapsackCombo().run(knp);
			System.out.println(set);
			
			NonDominatedSolutionSet set2 = new SalesmanLinKernighanHeuristic().run(new SalesmanProblem(problem.getMap()));
			System.out.println(set2);
			
			Tour<?> bestTour = (Tour<?>) set2.get(0).getVariable();
			PackingList<?> bestList = (PackingList<?>) set.get(0).getVariable();
			
			System.out.println(problem.evaluate(new TTPVariable(bestTour,bestList )));
			System.out.println(problem.evaluate(new TTPVariable(bestTour.getSymmetric(),bestList )));
			
			//NonDominatedSolutionSet set = new ThiefExhaustive().run(problem);
			//System.out.println(String.format("DifferentToursInFront: %s", new ThiefAmountOfDifferentTours().analyze(set)));
			//System.out.println(String.format("TSPTourDominance: %s", new TourAverageDistanceToOpt().analyze(problem)));
			//Evaluator eval = new Evaluator(new OptimalTourIsDominating());
			//System.out.println(String.format("FactoryThiefProblem: %s", eval.evaluate(new ThiefProblemVariable(problem))));
			System.out.println("----------------------------------------");
		}

	}


}
