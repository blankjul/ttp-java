package com.moo.ttp.model.packing;

import java.util.ArrayList;
import java.util.List;

import com.moo.ttp.util.Rnd;
import com.moo.ttp.variable.TravellingThiefProblem;

public class BooleanPackingListFactory implements IPackingPlanFactory {


	@Override
	public PackingList<?> create(TravellingThiefProblem p) {
		double pickingProb = Rnd.rndDouble();
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < p.numOfItems(); i++) {
			b.add(Rnd.rndDouble() < pickingProb);
		}
		return new BooleanPackingList(b);
	}



}
