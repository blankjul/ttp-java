package com.msu.thief.evolving.measures;

import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.IAlgorithm;
import com.msu.model.AProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.HypervolumeBoxPlot;
import com.msu.thief.algorithm.factory.NSGAIIFactory;
import com.msu.thief.evolving.ThiefProblemVariable;
import com.msu.util.MyRandom;

public class OptimalTourHypervolume extends AProblem<ThiefProblemVariable>{

	
	public static final int MAX_EVALUATIONS_OF_THIEF = 4000;
	
	public static final int POPULATION_SIZE = 40;
	
	
	IAlgorithm aRandom = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").set("populationSize", POPULATION_SIZE).build();
	IAlgorithm aOptimal = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[NO-HUX]-[NO-BF]").set("populationSize", POPULATION_SIZE).build();
	
	int counter = 0;
	
	@Override
	public int getNumberOfObjectives() {
		return 2;
	}


	@Override
	protected void evaluate_(ThiefProblemVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		NonDominatedSolutionSet setRandom = aRandom.run(var.get(), new Evaluator(MAX_EVALUATIONS_OF_THIEF), new MyRandom());
		NonDominatedSolutionSet setOptimal = aOptimal.run(var.get(), new Evaluator(MAX_EVALUATIONS_OF_THIEF), new MyRandom());
		
		/*
		int numOfDominations = 0;
		SolutionDominatorWithConstraints cmp = new SolutionDominatorWithConstraints();
		
		for (Solution rand : setRandom) {
			boolean isDominated = false;
			for (Solution opt : setOptimal) {
				if (cmp.isDominating(opt, rand)) {
					isDominated = true;
					break;
				}
			}
			if (isDominated) ++numOfDominations;
		}
		
		objectives.add((double) numOfDominations / (double) setRandom.size());
		objectives.add((double) - setRandom.size());
		*/
		
		List<Double> hv = HypervolumeBoxPlot.calc(Arrays.asList(setRandom, setOptimal), null);
		//objectives.add((double) hv.get(1) / hv.get(0));
		objectives.add((double) hv.get(1) -  hv.get(0));
		objectives.add((double) - setRandom.size());
		System.out.println(++counter);
		
	}
	
	

	

}
