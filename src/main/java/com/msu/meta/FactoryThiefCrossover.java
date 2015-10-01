package com.msu.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.io.pojo.PlainObjectItem;
import com.msu.io.pojo.PlainObjectThiefProblem;
import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.util.Random;
import com.msu.util.ThiefUtil;

public class FactoryThiefCrossover extends AbstractCrossover<PlainObjectThiefProblem>{

	@Override
	protected List<PlainObjectThiefProblem> crossover_(PlainObjectThiefProblem a, PlainObjectThiefProblem b) {
		return Arrays.asList(crossoverWithOrder(a, b), crossoverWithOrder(b, a));
	}
	
	protected PlainObjectThiefProblem crossoverWithOrder(PlainObjectThiefProblem a, PlainObjectThiefProblem b) {
		PlainObjectThiefProblem child = (PlainObjectThiefProblem) ThiefUtil.cloneObject(a);
		
		List<List<Double>> cities = new ArrayList<>();
		for (int i = 0; i < a.cities.size(); i++) {
			if (Random.getInstance().nextDouble() < 0.5) {
				cities.add(a.cities.get(i));
			} else {
				cities.add(b.cities.get(i));
			}
		}
		child.cities = cities;
		child.maxWeight = (a.maxWeight + b.maxWeight) / 2;
		
		List<PlainObjectItem> items = new ArrayList<>();
		for (int i = 0; i < a.items.size(); i++) {
			if (Random.getInstance().nextDouble() < 0.5) {
				items.add(a.items.get(i));
			} else {
				items.add(b.items.get(i));
			}
		}
		child.items = items;
		
		return child;
	}

}
