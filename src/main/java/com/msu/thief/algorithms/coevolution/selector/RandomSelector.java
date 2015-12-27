package com.msu.thief.algorithms.coevolution.selector;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class RandomSelector extends ASelector{

	public RandomSelector(int n) {
		super(n);
	}

	
	@Override
	public List<IVariable> select(SolutionSet set, MyRandom rand) {
		List<Integer> s = Util.createIndex(Math.min(n, set.size()));
		rand.shuffle(s);
		
		List<IVariable> coll = new ArrayList<>();
		for (Integer idx : s) {
			if (coll.size() == n) break;
			coll.add(set.get(idx).getVariable());
		}
		return coll;
	}

}
