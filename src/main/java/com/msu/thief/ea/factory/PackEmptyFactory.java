package com.msu.thief.ea.factory;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;

public class PackEmptyFactory extends AOperator implements IFactory<Pack> {

	


	public PackEmptyFactory(AbstractThiefProblem thief) {
		super(thief);
	}

	@Override
	public Pack next(MyRandom rand) {
		return Pack.empty();
	}

	


}
