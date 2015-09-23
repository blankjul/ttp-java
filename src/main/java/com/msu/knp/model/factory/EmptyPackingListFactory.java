package com.msu.knp.model.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.thief.ThiefProblem;

public class EmptyPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(ThiefProblem p) {
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < p.numOfItems(); i++) {
			b.add(false);
		}
		return new BooleanPackingList(b);
	}



}
