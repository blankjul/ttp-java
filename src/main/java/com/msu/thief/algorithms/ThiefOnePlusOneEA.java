package com.msu.thief.algorithms;

import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;

public class ThiefOnePlusOneEA extends AbstractThiefHillClimbing {

	
	protected void mutate(SingleObjectiveThiefProblem problem, Pack nextPack, MyRandom rand) {
		new PackBitflipMutation(problem).mutate(nextPack, rand);
		
	}
	


}
