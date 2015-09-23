package com.msu.experiment;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.scenarios.impl.BonyadiFactory;
import com.msu.visualize.TSPObjectiveVisualizer;

public class BonyadiExperiment extends AMultiObjectiveExperiment<TravellingThiefProblem> {

	final public static String[] INSTANCES = new String[] { 
			"../ttp-benchmark/10/10_3_1_25.txt",
			//"../ttp-benchmark/10/10_15_10_75.txt",
			//"../ttp-benchmark/20/20_25_10_75.txt",
			//"../ttp-benchmark/50/50_75_10_75.txt",
			//"../ttp-benchmark/100/100_150_10_75.txt",
	};
	
	@Override
	public void visualize() {
		new TSPObjectiveVisualizer<TravellingThiefProblem>().show(settings, result);
	}
	

	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		//settings.addAlgorithm(AlgorithmFactory.createNSGAII());
		settings.addAlgorithm(new ExhaustiveThief());
	}
	

	@Override
	protected void setProblems(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		for(String s : INSTANCES) {
			TravellingThiefProblem ttp = new BonyadiFactory().create(s);
			ttp.setName(s);
			settings.addProblem(ttp);
		}
	}

}
