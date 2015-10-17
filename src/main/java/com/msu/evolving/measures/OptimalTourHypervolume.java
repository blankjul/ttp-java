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
	
	public static final int POPULATION_SIZE = 40;
	
	
	IAlgorithm aRandom = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").setPopulationSize(POPULATION_SIZE).create();
	IAlgorithm aOptimal = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[NO-HUX]-[NO-BF]").setPopulationSize(POPULATION_SIZE).create();
	
	int counter = 0;
	
	@Override
	public int getNumberOfObjectives() {
		return 2;
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
