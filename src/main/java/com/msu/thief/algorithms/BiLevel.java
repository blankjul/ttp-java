package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.builder.Builder;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractDomainAlgorithm;
import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.NSGAIIFactory;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.FixedTourFactory;
import com.msu.thief.variable.tour.factory.TwoOptFactory;
import com.msu.util.MyRandom;

public class BiLevel extends AbstractDomainAlgorithm<ThiefProblem> {

	protected boolean includeTwoOpt = false;
	
	protected int lowerLevelPopulationSize = 50;
	
	public BiLevel() {
		super();
	}
	
	public BiLevel(boolean includeTwoOpt) {
		super();
		this.includeTwoOpt = includeTwoOpt;
	}

	
	@Override
	public NonDominatedSolutionSet run__(ThiefProblem problem, IEvaluator eval, MyRandom rand) {
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);
		Tour<?> symBestTour = bestTour.getSymmetric();
		
		List<Tour<?>> tours = new ArrayList<>();
		tours.add(bestTour);
		tours.add(symBestTour);
		
		if (includeTwoOpt) {
			for (int i = 0; i < 8; i++) {
				Tour<?> next = new TwoOptFactory().next(problem, rand);
				tours.add(next);
			}
		}
		
		
		for(Tour<?> t : tours) {
			
			Builder<NSGAII> builder = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[NO-HUX]-[SWAP-BF]");
			builder.set("populationSize", lowerLevelPopulationSize);
			builder.set("factory", new TTPVariableFactory(new FixedTourFactory(t), new OptimalPackingListFactory()));
			IAlgorithm nsgaII = builder.build();
			
			NonDominatedSolutionSet result = nsgaII.run(problem, eval.createChildEvaluator(eval.getMaxEvaluations() / tours.size()), rand);
			set.addAll(result.getSolutions());
		}
		return set;
	}


	
	

}
