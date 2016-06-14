package com.msu.thief.ea.operators;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;

public class PackSinglePointCrossover extends AOperator implements ICrossover<Pack> {

	public PackSinglePointCrossover(AbstractThiefProblem thief) {
		super(thief);
	}

	@Override
	public List<Pack> crossover(Pack a, Pack b, MyRandom rand) {

		final int point = rand.nextInt(1, thief.numOfItems() - 2);
		
		// copy the both list and change values
		Pack c1 = new Pack();
		Pack c2 = new Pack();
		
		
		for (int i = 0; i < point; i++) {
			if (a.isPicked(i)) c1.add(i);
			if (b.isPicked(i)) c2.add(i);
		}
		
		for (int i = point; i < thief.numOfItems(); i++) {
			if (a.isPicked(i)) c2.add(i);
			if (b.isPicked(i)) c1.add(i);
		}
		
		return Arrays.asList(c1, c2);

	}

}
