package com.msu.thief.ea.operators;

import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;

public class ThiefUniformCrossover  extends AOperator implements ICrossover<Pack> {

	
	public ThiefUniformCrossover(AbstractThiefProblem thief) {
		super(thief);
	}
	

	@Override
	public List<Pack> crossover(Pack p1, Pack p2, MyRandom rand) {
		
		final int n = thief.numOfItems();
		
		Pack off1 = new Pack();
		Pack off2 = new Pack();
		
		for (int i = 0; i < n; i++) {
			
			if (rand.nextDouble() < 0.5) {
				if (p1.isPicked(i)) off1.add(i);
				if (p2.isPicked(i)) off2.add(i);
			} else {
				if (p1.isPicked(i)) off2.add(i);
				if (p2.isPicked(i)) off1.add(i);
			}
			
		}
		
		return Arrays.asList(off1, off2);
		
	}
	
	

	

}
