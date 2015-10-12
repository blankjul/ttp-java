package com.msu.evolving.measures;

import java.util.Arrays;
import java.util.List;

import com.msu.NSGAIIFactory;
import com.msu.evolving.ThiefProblemVariable;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.HypervolumeBoxPlot;

public class OptimalTourHypervolume extends AProblem<ThiefProblemVariable>{

	public static final int MAX_EVALUATIONS_OF_THIEF = 4000;
	
	
	IAlgorithm aRandom = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]").setPopulationSize(40).create();
	IAlgorithm aOptimal = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[NO-HUX]-[NO-BF]").setPopulationSize(40).create();
	
	int counter = 0;
	
	@Override
	public int getNumberOfObjectives() {
		return 1;
	}
	

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}


	@Override
	protected void evaluate_(ThiefProblemVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		NonDominatedSolutionSet setRandom = aRandom.run(new Evaluator(var.get(), MAX_EVALUATIONS_OF_THIEF));
		NonDominatedSolutionSet setOptimal = aOptimal.run(new Evaluator(var.get(), MAX_EVALUATIONS_OF_THIEF));
		
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
		
		objectives.add((double) numOfDominations);
		*/
		
		
		List<Double> hv = HypervolumeBoxPlot.calc(Arrays.asList(setRandom, setOptimal), null);
		objectives.add((double) hv.get(1) / hv.get(0));
		
		System.out.println(++counter);
		
	}
	
	

	

}
