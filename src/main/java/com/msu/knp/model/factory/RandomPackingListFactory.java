package com.msu.knp.model.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.moo.util.Random;
import com.msu.thief.TravellingThiefProblem;

public class RandomPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(TravellingThiefProblem p) {
		Random rnd = Random.getInstance();
		double pickingProb = rnd.nextDouble();
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < p.numOfItems(); i++) {
			b.add(rnd.nextDouble() < pickingProb);
		}
		return new BooleanPackingList(b);
	}



}
