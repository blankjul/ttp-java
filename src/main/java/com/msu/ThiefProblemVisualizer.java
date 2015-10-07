package com.msu;

import org.apache.log4j.BasicConfigurator;

import com.msu.io.AProblemReader;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Pair;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.thief.ThiefProblem;
import com.msu.visualize.JavaScriptThiefVisualizer;

public class ThiefProblemVisualizer {

	final public static AProblemReader<ThiefProblem> reader = new BenchmarkTSPLIB();
	
	//final public static IAlgorithm algorithm = new ExhaustiveThief();
	final public static IAlgorithm algorithm = NSGAIIFactory.
			createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create();
	
	
	final public static String[] PROBLEMS = new String[] { 
			//"../ttp-benchmark/opt_tour_performs_bad.ttp" ,
			//"../ttp-benchmark/opt_tour_performs_optimal.ttp",
			"../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_05.ttp", 
	};

	public static void main(String[] args) {
		BasicConfigurator.configure();
		for (String strProblem : PROBLEMS) {
			ThiefProblem problem = reader.read(strProblem);
			NonDominatedSolutionSet set = algorithm.run(new Evaluator(problem, 1000000));
			new JavaScriptThiefVisualizer().write(Pair.create(problem, set), strProblem + ".html");
		}

	}

}
