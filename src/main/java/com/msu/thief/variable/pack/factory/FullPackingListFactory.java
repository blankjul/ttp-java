package com.msu.thief.variable.pack.factory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.Random;
import com.msu.problems.IPackingProblem;
import com.msu.thief.model.Item;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.CombinatorialUtil;

public class FullPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(IProblem p) {
		
		// create empty list with no items 
		List<Boolean> b = new ArrayList<Boolean>();
		IPackingProblem problem = (IPackingProblem) p;
		
		for (int i = 0; i < problem.numOfItems(); i++) b.add(false);
		
		// create queue with all items indices
		List<Item> items = problem.getItems();
		List<Integer> index =  CombinatorialUtil.getIndexVector(items.size());
		Random.getInstance().shuffle(index);
		Queue<Integer> q = new LinkedList<>(index);
		
		int weight = 0;
		while (!q.isEmpty()) {
			final int i = q.poll();
			if (weight + items.get(i).getWeight() > problem.getMaxWeight()) break;
			else {
				b.set(i, true);
				weight += items.get(i).getWeight();
			}
		}
		return new BooleanPackingList(b);
	}



}
