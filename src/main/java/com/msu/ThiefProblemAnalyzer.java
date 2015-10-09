package com.msu;

import com.msu.algorithms.exhaustive.ThiefExhaustive;
import com.msu.analyze.DifferentToursInFront;
import com.msu.analyze.TSPTourDominance;
import com.msu.evolving.FactoryThiefVariable;
import com.msu.evolving.measures.OptimalTourIsDominating;
import com.msu.io.reader.JsonThiefProblemReader;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.problems.ThiefProblem;

public class ThiefProblemAnalyzer {

	final public static String[] PROBLEMS = new String[] { 
			"../ttp-benchmark/opt_tour_performs_bad.ttp" ,
			"../ttp-benchmark/opt_tour_performs_optimal.ttp",
			"../ttp-benchmark/EA_example00.ttp",
			
	};
	
	public static void main(String[] args) {
		for (String strProblem : PROBLEMS) {
			ThiefProblem problem = new JsonThiefProblemReader().read(strProblem);
			NonDominatedSolutionSet set = new ThiefExhaustive().run(problem);
			System.out.println(strProblem);
			System.out.println(String.format("DifferentToursInFront: %s", new DifferentToursInFront().analyze(set)));
			System.out.println(String.format("TSPTourDominance: %s", new TSPTourDominance().analyze(problem)));
			Evaluator eval = new Evaluator(new OptimalTourIsDominating());
			System.out.println(String.format("FactoryThiefProblem: %s", eval.evaluate(new FactoryThiefVariable(problem))));
			System.out.println("----------------------------------------");
		}

	}


}
