package com.msu.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.NSGAIIFactory;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.AbstractAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.algorithms.nsgaII.NSGAIIBuilder;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.ATourFactory;
import com.msu.thief.variable.tour.factory.TwoOptFactory;
import com.msu.util.Random;

public class MicroEvoluationaryAlgorithm extends AbstractAlgorithm {

	protected boolean includeTwoOpt = false;
	
	
	public MicroEvoluationaryAlgorithm() {
		super();
	}
	

	public MicroEvoluationaryAlgorithm(boolean includeTwoOpt) {
		super();
		this.includeTwoOpt = includeTwoOpt;
	}


	private class FixedTourFactory extends ATourFactory {
		protected Tour<?> t = null;
		public FixedTourFactory(Tour<?> t) {
			super();
			this.t = t;
		}

		@Override
		protected Tour<?> next_(IProblem problem, Random rand) {
			return t;
		}
		
	}
	
	@Override
	public NonDominatedSolutionSet run_(IProblem p, IEvaluator eval, Random rand) {
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		ThiefProblem ttp =(ThiefProblem) p;
		SalesmanProblem tsp = new SalesmanProblem(ttp.getMap());
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(tsp, new Evaluator(Integer.MAX_VALUE));
		Tour<?> symBestTour = bestTour.getSymmetric();
		
		List<Tour<?>> tours = new ArrayList<>();
		tours.add(bestTour);
		tours.add(symBestTour);
		
		if (includeTwoOpt) {
			for (int i = 0; i < 8; i++) {
				Tour<?> next = new TwoOptFactory().next(ttp, rand);
				tours.add(next);
			}
		}
		
		
		for(Tour<?> t : tours) {
			
			NSGAIIBuilder builder = new NSGAIIBuilder().setPopulationSize(50);
			builder = NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[NO-HUX]-[SWAP-BF]", builder);
			builder.setFactory(new TTPVariableFactory(new FixedTourFactory(t), new OptimalPackingListFactory()));
			IAlgorithm nsgaII = builder.create();
			
			NonDominatedSolutionSet result = nsgaII.run(p, new Evaluator(eval.getMaxEvaluations() / tours.size()), rand);
			
			set.addAll(result.getSolutions());
		}

		
		return set;
		
	}
	
	

}
