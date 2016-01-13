package com.msu.thief.ea.tour.factory;

import com.msu.thief.ea.tour.mutation.ThiefSwapMutation;
import com.msu.thief.problems.variable.Tour;

public class ThiefOptimalTourWithSwapFactory extends ThiefOptimalTourFactory {


	@Override
	public Tour create() {
		Tour result = null;
		if (rand.nextDouble() < 0.5)
			result = best.copy();
		else
			result = sym.copy();

		if (rand.nextDouble() < 0.9)
			new ThiefSwapMutation().mutate(problem, rand, result);

		return result;
	}

}
