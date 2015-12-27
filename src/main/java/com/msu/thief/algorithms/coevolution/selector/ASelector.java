package com.msu.thief.algorithms.coevolution.selector;

import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.MyRandom;

public abstract class ASelector {
	
	protected int n;
	
	
	public ASelector(int n) {
		super();
		this.n = n;
	}


	public abstract List<IVariable> select(SolutionSet set, MyRandom rand);

}
