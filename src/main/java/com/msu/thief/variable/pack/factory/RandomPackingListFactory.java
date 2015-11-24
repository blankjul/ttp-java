package com.msu.thief.variable.pack.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.thief.problems.IPackingProblem;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.MyRandom;

public class RandomPackingListFactory extends APackingPlanFactory {


	
	
	@Override
	public PackingList<?> next(IProblem p, MyRandom rand) {
		final double pickingProb = rand.nextDouble();
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < ((IPackingProblem) p).numOfItems(); i++) {
			b.add(rand.nextDouble() < pickingProb);
		}
		return new BooleanPackingList(b);
	}



}
