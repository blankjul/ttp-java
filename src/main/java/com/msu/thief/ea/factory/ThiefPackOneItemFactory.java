package com.msu.thief.ea.factory;

import java.util.Collection;
import java.util.LinkedList;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;

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
public class ThiefPackOneItemFactory extends AOperator implements IFactory<Pack> {


	
	//! pool with all the items
	protected LinkedList<Pack> pool = new LinkedList<>();
	
	
	public ThiefPackOneItemFactory(AbstractThiefProblem thief) {
		this(thief, Util.createIndex(thief.numOfItems()));
	}
	
	public ThiefPackOneItemFactory(AbstractThiefProblem thief, Collection<Integer> items) {
		super(thief);
		
		for (int i : items) {
			Pack p = new Pack(i);
			pool.add(p);
		}
		
	}

	


	@Override
	public Pack next(MyRandom rand) {
		if (pool.isEmpty()) throw new RuntimeException("Factory can not provide more entries.");
		return pool.poll();
	}

	
	@Override
	public boolean hasNext() {
		return !pool.isEmpty();
	}

	



}
