package com.msu.thief.problems.knp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.thief.model.Item;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.util.CombinatorialUtil;

public class KnapsackExhaustiveFactory implements VariableFactory<KnapsackVariable, KnapsackProblem> {

	public static void main(String[] args) {
		KnapsackExhaustiveFactory fac = new KnapsackExhaustiveFactory();
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(10, 3));
		items.add(new Item(4, 1));
		items.add(new Item(4, 1));
		KnapsackProblem p = new KnapsackProblem(10, items);
		fac.create(p);
	}
	
	// ! all the permutations that exist
	protected Queue<List<Boolean>> q = null;

	
	@Override
	public KnapsackVariable create(KnapsackProblem problem) {
		if (q == null) {
			q = new LinkedList<>(CombinatorialUtil.getAllBooleanCombinations(problem.numOfItems()));
		} else if (q.isEmpty())
			return null;
		PackingList<?> tour = new BooleanPackingList(q.poll());
		return new KnapsackVariable(tour);
	}

	
	
	

}
