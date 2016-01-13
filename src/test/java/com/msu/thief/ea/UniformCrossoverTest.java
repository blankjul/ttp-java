package com.msu.thief.ea;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.msu.thief.ea.pack.crossover.ThiefUniformCrossover;
import com.msu.thief.problems.variable.Pack;

public class UniformCrossoverTest extends Operator{
	
	
	@Test
	public void testCrossover() {
		Pack p1 = Pack.createFromString("0,2");
		Pack p2 = Pack.createFromString("0,3");
		
		List<Pack> result = new ThiefUniformCrossover().crossover(thief, rand, p1, p2);
		
		for (int i = 0; i < p1.numOfItems(); i++) {
			if (p1.isPicked(i) && p2.isPicked(i)) {
				assertTrue(result.get(0).isPicked(i) && result.get(1).isPicked(i));
			} else 	if (!p1.isPicked(i) && !p2.isPicked(i)) {
				assertTrue(!result.get(0).isPicked(i) && !result.get(1).isPicked(i));
			} else {
				assertTrue(result.get(0).isPicked(i) ^ result.get(1).isPicked(i));
			}
		}
	}
	
	
	
	
	
	

}
