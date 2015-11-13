package com.msu.thief.variable.pack.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.problems.IPackingProblem;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.Random;

public class EmptyPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(IProblem p, Random rand) {
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < ((IPackingProblem) p).numOfItems(); i++) {
			b.add(false);
		}
		return new BooleanPackingList(b);
	}



}
