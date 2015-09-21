package com.msu.thief.model.packing.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;

public class EmptyPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(TravellingThiefProblem p) {
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < p.numOfItems(); i++) {
			b.add(false);
		}
		return new BooleanPackingList(b);
	}



}
