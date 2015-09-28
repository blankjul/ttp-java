package com.msu.knp.model.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.IPackingProblem;
import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.moo.interfaces.IProblem;

public class EmptyPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(IProblem p) {
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < ((IPackingProblem) p).numOfItems(); i++) {
			b.add(false);
		}
		return new BooleanPackingList(b);
	}



}
