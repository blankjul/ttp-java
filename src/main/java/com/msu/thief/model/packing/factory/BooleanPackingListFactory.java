package com.msu.thief.model.packing.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.util.Random;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;

public class BooleanPackingListFactory implements IPackingPlanFactory {


	@Override
	public PackingList<?> create(TravellingThiefProblem p) {
		Random rnd = Random.getInstance();
		double pickingProb = rnd.nextDouble();
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < p.numOfItems(); i++) {
			b.add(rnd.nextDouble() < pickingProb);
		}
		return new BooleanPackingList(b);
	}



}
