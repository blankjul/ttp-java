package com.msu.experiment.bonyadi;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;
import com.msu.util.ThiefUtil;

public class BonyadiTSPLIBExperiment extends ABonyadiBenchmark {

	
	final public static String FOLDER = "../ttp-benchmark/TSPLIB";
	final public static String PATTERN = "berlin52_n510_bounded-strongly-corr_.*";
	
	//! single objective problems for the SO algorithms
	protected List<IProblem> sotps = new ArrayList<>();
	
	IAlgorithm onePlusOne = new OnePlusOneEA();

	
	@Override
	public void run(int maxEvaluations, int iterations, long seed) {
		
		setProblems(problems);
		setAlgorithms(algorithms);
		
		// run with multi objective problems
		run(problems, algorithms, maxEvaluations, iterations, seed);
		
		// run the other algorithms with single objective algorithms
		List<IAlgorithm> soAlgorithms = Arrays.asList(onePlusOne);
		run(sotps, soAlgorithms, maxEvaluations, iterations, seed);
		
		// convert the result to multiobjective solutions
		for(IProblem problem : problems) {
			Evaluator eval = new Evaluator(problem);
			for (IAlgorithm algorithm : soAlgorithms) {
				List<NonDominatedSolutionSet> solutions = new ArrayList<>();
				for(NonDominatedSolutionSet set : getResult().get(problem, algorithm)) {
					if (set.size() != 1) throw new RuntimeException("Only one solution since SingleObj!");
					Solution s = eval.evaluate(set.get(0).getVariable());
					solutions.add(new NonDominatedSolutionSet(Arrays.asList(s)));
				}
				getResult().set(problem, algorithm, solutions);
			}
		}
		
		algorithms.add(0,onePlusOne);
		
		new AttainmentSurfacePlot().setVisibility(true);
		
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s,%s,%s,%s\n", "problem", "algorithm", "dominates", "isDominated"));
		
		for(IProblem problem : problems) {
			Solution best = getResult().getFirst(problem, onePlusOne, "median").get(0);
			for (IAlgorithm algorithm : algorithms) {
				
				int singleDominates = 0;
				int singleIsDominated = 0;
				NonDominatedSolutionSet set = getResult().getFirst(problem, algorithm, "median");
				SolutionDominator cmp = new SolutionDominator();
				for(Solution s :  set.getSolutions()) {
					if (cmp.isDominating(best, s)) ++singleDominates;
					if (cmp.isDominating(s, best)) ++singleIsDominated;
				}
				sb.append(String.format("%s,%s,%s,%s\n", problem, algorithm, singleDominates, singleIsDominated));
			}
		}
		
		//if (hasOutputDirectory()) Util.write(String.format("%s/%s", getOutputDir(), "TSPLIB_result.csv"),sb);
		System.out.println(sb);
		
		
	}
	

	@Override
	protected void setProblems(List<IProblem> problems) {
		for (String path : ThiefUtil.getFiles(FOLDER)) {
			if (path.endsWith(".ttp")) {
				
				String name = new File(path).getName().split("\\.")[0];
				Pattern r = Pattern.compile(PATTERN);
				if (!r.matcher(name).matches())  continue;

				System.out.println(path);
				SingleObjectiveThiefProblem sotp = new BenchmarkTSPLIB().create(path);
				sotp.setName("TSPLIB_" + name);
				sotps.add(sotp);
				ThiefProblem ttp = sotp.getThiefProblem();
				problems.add(ttp);
			}
		}
	}
	
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		super.setAlgorithms(algorithms);
	}

	
	

}
