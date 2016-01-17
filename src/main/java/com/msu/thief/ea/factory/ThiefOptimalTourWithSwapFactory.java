package com.msu.thief.ea.factory;

import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.operators.ThiefSwapMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;

public class ThiefOptimalTourWithSwapFactory extends ThiefOptimalTourFactory {


	public ThiefOptimalTourWithSwapFactory(AbstractThiefProblem thief) {
		super(thief);
	}

	@Override
	public Tour next(MyRandom rand) {
		Tour result = null;
		if (rand.nextDouble() < 0.5)
			result = best.copy();
		else
			result = sym.copy();

		if (rand.nextDouble() < 0.9)
			new ThiefSwapMutation().mutate(result, rand);

		return result;
	}

}
