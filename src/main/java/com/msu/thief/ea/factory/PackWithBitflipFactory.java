package com.msu.thief.ea.factory;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.AOperator;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;


public class PackWithBitflipFactory extends AOperator implements IFactory<Pack>  {

	protected Pack starting = null;
	

	public PackWithBitflipFactory(AbstractThiefProblem thief, Pack starting) {
		super(thief);
		this.starting = starting;
	}


	@Override
	public Pack next(MyRandom rand) {
		Pack next = starting.copy();
		new PackBitflipMutation(thief).mutate(next, rand);
		return next;
	}

	



}
