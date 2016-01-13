package com.msu.thief.ea.pack.crossover;

import java.util.Arrays;
import java.util.List;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;

public class ThiefUniformCrossover implements PackCrossover{

	
	@Override
	public List<Pack> crossover(AbstractThiefProblem problem, MyRandom rand, Pack p1, Pack p2) {
		
		Pack off1 = new Pack();
		Pack off2 = new Pack();
		
		for (int i = 0; i < problem.numOfItems(); i++) {
			
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
