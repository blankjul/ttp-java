package com.msu;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.io.AProblemReader;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Pair;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.thief.ThiefProblem;
import com.msu.visualize.JavaScriptThiefVisualizer;

public class ThiefProblemVisualizer {

	final public static AProblemReader<ThiefProblem> reader = new BenchmarkTSPLIB();

	// final public static IAlgorithm algorithm = new ExhaustiveThief();
	final public static List<IAlgorithm> algorithms = Arrays.asList(
			NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-EMPTY]-[OX-HUX]-[SWAP-BF]").create()
			//NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create()
			);

	final public static String[] PROBLEMS = new String[] {
			// "../ttp-benchmark/opt_tour_performs_bad.ttp" ,
			// "../ttp-benchmark/opt_tour_performs_optimal.ttp",
			"../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_uncorr-similar-weights_01.ttp", };

	public static void main(String[] args) {
		BasicConfigurator.configure();
		for (String strProblem : PROBLEMS) {
			ThiefProblem problem = reader.read(strProblem);

			SolutionSet finalSet = new SolutionSet();
			for(IAlgorithm a : algorithms) {
				NonDominatedSolutionSet set = a.run(new Evaluator(problem, 1000000));
				finalSet.addAll(set.getSolutions());
			}
			

			File f = new File(strProblem);
			new JavaScriptThiefVisualizer().write(Pair.create(problem, finalSet), "../ttp-benchmark/" + f.getName() + ".html");
		}

	}

}
