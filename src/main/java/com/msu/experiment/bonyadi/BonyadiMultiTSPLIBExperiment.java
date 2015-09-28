package com.msu.experiment.bonyadi;

import java.util.ArrayList;
import java.util.List;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.algorithms.RandomLocalSearch;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.plots.ScatterPlot;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;
import com.msu.util.Util;

public class BonyadiMultiTSPLIBExperiment extends ABonyadiBenchmark {

	
	final public static String FOLDER = "../ttp-benchmark/TSPLIB/eil51-ttp/eil51_n50_uncorr-similar-weights_06.ttp";
	
	protected List<SingleObjectiveThiefProblem> sotps = new ArrayList<>();
	

	@Override
	protected void setProblems(List<IProblem> problems) {
		for (String path : Util.getFiles(FOLDER)) {
			SingleObjectiveThiefProblem sotp = new BenchmarkTSPLIB().create(path);
			sotps.add(sotp);
			ThiefProblem ttp = sotp.getThiefProblem();
			problems.add(ttp);
		}
	}
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		super.setAlgorithms(algorithms);
		algorithms.add(new OnePlusOneEA());
		algorithms.add(new RandomLocalSearch());
	}


	@Override
	public void finalize() {
		new AttainmentSurfacePlot().show(this);
		
		for (int i = 0; i < problems.size(); i++) {
			ThiefProblem problem =  (ThiefProblem) problems.get(i);
			String title = problem.toString();
			ScatterPlot sp = new ScatterPlot(title);
			
			/*
			OnePlusOneEA a = new OnePlusOneEA();
			NonDominatedSolutionSet singleObjSet = a.run(new Evaluator(sotps.get(i),settings.getMaxEvaluations()));
			NonDominatedSolutionSet set = new NonDominatedSolutionSet();
			set.add(problem.evaluate(singleObjSet.get(0).getVariable()));
			sp.add(set, "OnePlusOneEA");
			
			for (IAlgorithm algorithm : algorithms) {
				for (NonDominatedSolutionSet multiObjSet : result.get(problem, algorithm)) {
					sp.add(multiObjSet, algorithm.toString());
				}
			}
			*/
			
			sp.show();
		}
	}
	
	
	
	

}
