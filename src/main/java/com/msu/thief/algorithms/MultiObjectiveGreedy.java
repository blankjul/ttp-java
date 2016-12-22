package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.msu.moo.algorithms.AMultiObjectiveAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Pair;
import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.model.Item;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class MultiObjectiveGreedy
		extends AMultiObjectiveAlgorithm<TTPVariable, MultiObjectiveThiefProblem> {

	
	public boolean postLocalOptimization = false;

	
	
	
	public MultiObjectiveGreedy() {
		super();
	}




	public MultiObjectiveGreedy(boolean postLocalOptimization) {
		super();
		this.postLocalOptimization = postLocalOptimization;
	}





	@Override
	public NonDominatedSet<ISolution<TTPVariable>> run_(MultiObjectiveThiefProblem problem, IEvaluator evaluator,
			MyRandom rand) {

		
		
		NonDominatedSet<ISolution<TTPVariable>> set = new NonDominatedSet<>();

		// sort items according profit / weight rate
		List<Pair<Integer,Item>> items = new ArrayList<>();
		for (int i = 0; i < problem.getItems().size(); i++) {
			items.add(Pair.create(i, problem.getItem(i)));
		}
		Collections.sort(items, (i1, i2) -> - Double.compare(i1.second.getProfit() / i1.second.getWeight(), i2.second.getProfit() / i2.second.getWeight()) );
		
		// calculate the best tours
		Tour best = AlgorithmUtil.calcBestTour(problem);
		List<Tour> tours = Arrays.asList(best, best.getSymmetric());
		
		for(Tour pi : tours) {
			
			Collection<Integer> current = new ArrayList<>();
			
			for (int i = 0; i < items.size(); i++) {
				current.add(items.get(i).first);
				
				// add copy to a variable and evaluate
				Collection<Integer> copy = new ArrayList<>(current);
				Pack p = new Pack(copy);
				ISolution<TTPVariable> s = evaluator.evaluate(problem, TTPVariable.create(pi, p));
				set.add(s);
				
				if (s.hasConstrainViolations()) break;
				
				
			}
			
			
			
		}
		
		if (postLocalOptimization) {
			
			final int MAX_NO_IMPROVMENT = 10000;
			
			SolutionSet<ISolution<TTPVariable>> list = new SolutionSet<>(set.getSolutions());
			
			for(ISolution<TTPVariable> s : list) {
				
				ISolution<TTPVariable> current = s;
				
				int counter = 0;
				while (counter < MAX_NO_IMPROVMENT) {

					Pack nextPack = s.getVariable().getPack().copy();
					new PackBitflipMutation(problem).mutate(nextPack, rand);

					ISolution<TTPVariable> next = problem.evaluate(TTPVariable.create(s.getVariable().getTour(), nextPack));

					if (SolutionDominator.isDominating(next, current)) {
						current = next;
						counter = 0;
					}
					else counter++;

				}

				set.add(current);
				
			}
			
			
		}

		return set;
	}

}
