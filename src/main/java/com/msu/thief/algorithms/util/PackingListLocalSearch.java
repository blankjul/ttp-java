package com.msu.thief.algorithms.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.algorithms.moead.MOEADUtil;
import com.msu.moo.algorithms.nsgaII.INSGAIIModifactor;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.MyRandom;
import com.msu.util.Range;

public class PackingListLocalSearch implements INSGAIIModifactor {
	
	protected int iterations = 100;
	
	@Override
	public void modify(IProblem p, IEvaluator eval, SolutionSet population, MyRandom rand) {
		
		NonDominatedSolutionSet front = new NonDominatedSolutionSet(population);
		Solution s = front.get(rand.nextInt(front.size()));
		//System.out.println(s);
		
		TTPVariable var = (TTPVariable) s.getVariable();

		List<Double> weights = Arrays.asList(rand.nextDouble(), rand.nextDouble());

		Range<Double> range = new Range<Double>();
		for (Solution sol : population) range.add(sol.getObjective());
		
		PackingList<?> b = var.getPackingList();
		double fitness = MOEADUtil.calcWeightedSum(s.normalize(range.get()).getObjective(), weights);
		
		
		for (int i = 0; i < iterations; i++) {
			
			PackingList<?> next = (PackingList<?>) new BitFlipMutation().mutate(b.copy(), p, rand);
			Solution nextSolution = eval.evaluate(p,new TTPVariable(var.getTour(), next));
			double nextFitness = MOEADUtil.calcWeightedSum(nextSolution.normalize(range.get()).getObjective(), weights);
			
			if (Collections.max(nextSolution.getConstraintViolations()) <= Collections.max(s.getConstraintViolations())) {
				if (nextFitness < fitness) {
					s = nextSolution;
					b = next;
					fitness = nextFitness;
					population.add(nextSolution);
				}
			}
			
		}
		//System.out.println(s);
		//System.out.println("---------------------------");
		//population.add(s);
	}
	
}