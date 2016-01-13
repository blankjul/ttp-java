package com.msu.thief.ea.pack.factory;

import java.util.Collection;
import java.util.LinkedList;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;
import com.msu.util.Util;

/**
 * Creates a initial population where all items are contained alone as one
 * variable.
 * 
 * e.g.
 * 
 * for three items
 * 
 * [1,0,0] [0,1,0] [0,0,1]
 *
 * Method could be initialized with specific items. ONLY this are then allowed
 * to pack.
 *
 */
public class ThiefPackOneItemFactory extends PackFactory {

	
	//! pool with all the items
	protected LinkedList<Pack> pool = new LinkedList<>();
	
	
	
	@Override
	public void initialize(AbstractThiefProblem problem, MyRandom rand) {
		initialize(problem, rand, Util.createIndex(problem.numOfItems()));
	}


	public void initialize(AbstractThiefProblem problem, MyRandom rand, Collection<Integer> items) {
		
		super.initialize(problem, rand);
		
		for (int i : items) {
			Pack p = new Pack(i);
			pool.add(p);
		}
	
		rand.shuffle(pool);
	}
	
	

	@Override
	public Pack create() {
		if (pool.isEmpty()) throw new RuntimeException("Factory can not provide more entries.");
		return pool.poll();
	}

	
	@Override
	public boolean hasNext() {
		return !pool.isEmpty();
	}
	


}
