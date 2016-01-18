package com.msu.thief.ea.operators;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;

public class PackBitflipMutation extends AOperator implements IMutation<Pack> {

	
	
	public PackBitflipMutation(AbstractThiefProblem thief) {
		super(thief);
	}

	
	@Override
	public void mutate(Pack p, MyRandom rand) {
		
		boolean isMutated = false;
		
		final int n = thief.numOfItems();
		final double prob = 1 / (double) n;
		
		// iterate over all possible items to pick
		for (int i = 0; i < n; i++) {
			
			// bit flip with a probability
			if (rand.nextDouble() < prob) {
				// bitflip
				bitlfip(p, i);
				isMutated = true;
			}
			
		}
		
		// at least one bitflip
		if (!isMutated) {
			final int idx = rand.nextInt(n);
			bitlfip(p, idx);
		}
		
	}
	
	protected void bitlfip(Pack p, int i) {
		if (p.isPicked(i)) p.remove(i);
		else p.add(i);
	}

	
	
}
