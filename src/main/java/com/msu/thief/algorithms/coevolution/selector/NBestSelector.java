package com.msu.thief.algorithms.coevolution.selector;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.MyRandom;

public class NBestSelector extends ASelector{

	public NBestSelector(int n) {
		super(n);
	}

	@Override
	public List<IVariable> select(SolutionSet set, MyRandom rand) {
		List<IVariable> coll = new ArrayList<>();
		for (Solution s : set.subList(0, Math.min(n, set.size()))) {
			coll.add(s.getVariable());
		}
		return coll;
	}

}
