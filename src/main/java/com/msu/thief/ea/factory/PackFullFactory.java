package com.msu.thief.ea.factory;

import java.util.List;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;

public class PackFullFactory extends AOperator implements IFactory<Pack> {

	public PackFullFactory(AbstractThiefProblem thief) {
		super(thief);
	}

	@Override
	public Pack next(MyRandom rand) {

		int currentWeight = 0;
		Pack z = new Pack();

		List<Integer> indices = Util.createIndex(thief.numOfItems());
		rand.shuffle(indices);


		for (int i = 0; i < indices.size(); i++) {

			// select the item
			int idx = indices.get(i);
			double weight = thief.getItem(idx).getWeight();
			
			// check if leads to overweight
			if (currentWeight + weight > thief.getMaxWeight()) {
				break;
			// else just add it
			} else {
				currentWeight += weight;
				z.add(idx);
			}
			
		}

		return z;
	}


}
