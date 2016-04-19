package com.msu.thief.ea.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;

public class PackRandomFactory extends AOperator implements IFactory<Pack> {

	
	
	public PackRandomFactory(AbstractThiefProblem thief) {
		super(thief);
	}
	


	@Override
	public Pack next(MyRandom rand) {
		List<Integer> items = new ArrayList<>();
		for (int i = 0; i < thief.numOfItems(); i++) {
			if (rand.nextDouble() < 0.5) items.add(i);
		}
		rand.shuffle(items);
		
		Pack next = new Pack();
		int currentWeight = 0;
		for (Integer idx : items) {
			// if packing this item would violate the constraint break
			if (currentWeight + thief.getItem(idx).getWeight() > thief.getMaxWeight()) break;
			else {
				currentWeight += thief.getItem(idx).getWeight();
				next.add(idx);
			}
		}
		
		return next;
	}

	


}
