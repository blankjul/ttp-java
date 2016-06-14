package com.msu.thief.algorithms;

import com.msu.moo.util.MyRandom;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;

public class ThiefRandomLocalSearch extends AbstractThiefHillClimbing {

	
	protected void mutate(SingleObjectiveThiefProblem problem, Pack nextPack, MyRandom rand) {
		int idx = rand.nextInt(problem.numOfItems());
		if (nextPack.isPicked(idx))
			nextPack.remove(idx);
		else
			nextPack.add(idx);
	}
	


}
