package com.msu.experiment;

import java.util.ArrayList;
import java.util.List;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.plots.ScatterPlot;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;

public class BonyadiMultiTSPLIBExperiment extends ABonyadiBenchmark {

	
	final public static String[] INSTANCES = new String[] { 
			//"../ttp-benchmark/TSPLIB/eil51-ttp/eil51_n50_uncorr-similar-weights_01.ttp",
			//"../ttp-benchmark/TSPLIB/eil51-ttp/eil51_n50_uncorr-similar-weights_04.ttp",
			"../ttp-benchmark/TSPLIB/eil51-ttp/eil51_n50_uncorr-similar-weights_06.ttp",
			//"../ttp-benchmark/TSPLIB/eil51-ttp/eil51_n50_uncorr-similar-weights_07.ttp",
	};
	
	protected List<SingleObjectiveThiefProblem> sotps = new ArrayList<>();
	

	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		for(String s : INSTANCES) {
			SingleObjectiveThiefProblem sotp = new BenchmarkTSPLIB().create(s);
			sotps.add(sotp);
			ThiefProblem ttp = sotp.getThiefProblem();
			settings.addProblem(ttp);
		}
	}


	@Override
	public void visualize() {
		for (int i = 0; i < settings.getProblems().size(); i++) {
			ThiefProblem problem =  settings.getProblems().get(i);
			String title = problem.toString();
			ScatterPlot sp = new ScatterPlot(title);
			
			OnePlusOneEA a = new OnePlusOneEA();
			NonDominatedSolutionSet singleObjSet = a.run(new Evaluator<ThiefProblem>(sotps.get(i),settings.getMaxEvaluations()));
			NonDominatedSolutionSet set = new NonDominatedSolutionSet();
			set.add(problem.evaluate(singleObjSet.get(0).getVariable()));
			sp.add(set, "OnePlusOneEA");
			
			for (IAlgorithm<NonDominatedSolutionSet, ?> algorithm : settings.getAlgorithms()) {
				for (NonDominatedSolutionSet multiObjSet : result.get(problem, algorithm)) {
					sp.add(multiObjSet, algorithm.toString());
				}
			}
			
			
			sp.show();
		}
	}
	
	
	
	

}
