package com.msu.experiment.bonyadi;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.msu.util.Util;

public class BonyadiTSPLIBExperiment extends ABonyadiBenchmark {

	
	final public static String FOLDER = "../ttp-benchmark/TSPLIB/eil51-ttp/";
	
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
		
		algorithms.addAll(soAlgorithms);
		
		new AttainmentSurfacePlot().show(this);
		
		
		for(IProblem problem : problems) {
			Solution best = getResult().getFirst(problem, onePlusOne, "median").get(0);
			for (IAlgorithm algorithm : algorithms) {
				
				int counter = 0;
				NonDominatedSolutionSet set = getResult().getFirst(problem, algorithm, "median");
				SolutionDominator cmp = new SolutionDominator();
				for(Solution s :  set.getSolutions()) {
					if (cmp.isDominating(best, s)) ++counter;
				}
				System.out.println(String.format("%s,%s,%s", problem, algorithm, counter));
			}
		}
		
		
	}
	

	@Override
	protected void setProblems(List<IProblem> problems) {
		for (String path : Util.getFiles(FOLDER)) {
			if (path.endsWith(".ttp")) {
				System.out.println(path);
				SingleObjectiveThiefProblem sotp = new BenchmarkTSPLIB().create(path);
				sotp.setName("TSPLIB_" + new File(path).getName().split("\\.")[0]);
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
