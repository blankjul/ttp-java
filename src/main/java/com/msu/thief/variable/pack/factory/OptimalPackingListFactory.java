package com.msu.thief.variable.pack.factory;

import com.msu.algorithms.KnapsackCombo;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.util.Random;
import com.msu.problems.IPackingProblem;
import com.msu.problems.KnapsackProblem;
import com.msu.thief.variable.pack.PackingList;

public class OptimalPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(IProblem p, Random rand) {
		
		IPackingProblem packProblem = (IPackingProblem) p;
		
		double weight = rand.nextDouble() * packProblem.getMaxWeight();
		KnapsackProblem knp = new KnapsackProblem((int) weight, packProblem.getItems());
		
		PackingList<?> l = KnapsackCombo.getPackingList(new Evaluator(knp));
		return l;
	}



}
