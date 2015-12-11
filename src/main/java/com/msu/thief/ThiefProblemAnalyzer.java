package com.msu.thief;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.algorithms.KnapsackCombo;
import com.msu.thief.algorithms.SalesmanLinKernighanHeuristic;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.KnapsackProblem;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.FileCollectorParser;
import com.msu.util.MyRandom;

public class ThiefProblemAnalyzer {

	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		
		
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/SingleObjective/10", "10_3_1_50.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/TSPLIB/berlin52-ttp", "berlin52_n51_bounded-strongly-corr_01.ttp", new BonyadiTSPLIBReader());
		List<AbstractThiefProblem> collected = fcp.collect();
		
		collected.forEach((p) -> {
			if (p instanceof SingleObjectiveThiefProblem) 
				((SingleObjectiveThiefProblem)p).setToMultiObjective(true);
		});
		
		
		
		
		for (AbstractThiefProblem problem : collected) {
			
			KnapsackProblem knp = new KnapsackProblem(problem.getMaxWeight(), problem.getItems());
			
			
			NonDominatedSolutionSet set = new KnapsackCombo().run(knp, new Evaluator(Integer.MAX_VALUE), new MyRandom());
			System.out.println(set);
			
			NonDominatedSolutionSet set2 = new SalesmanLinKernighanHeuristic().run(new SalesmanProblem(problem.getMap()), new Evaluator(Integer.MAX_VALUE), new MyRandom());
			System.out.println(set2);
			
			Tour<?> bestTour = (Tour<?>) set2.get(0).getVariable();
			PackingList<?> bestList = (PackingList<?>) set.get(0).getVariable();
			
			System.out.println(problem.evaluate(new TTPVariable(bestTour,bestList )));
			System.out.println(problem.evaluate(new TTPVariable(bestTour.getSymmetric(),bestList )));
			
			System.out.println("----------------------------------------");
		}

	}


}
