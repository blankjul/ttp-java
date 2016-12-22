package com.msu.thief.ea.factory;

import java.util.LinkedList;
import java.util.Queue;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;


public class PackOptimalFactory extends AOperator implements IFactory<Pack>  {

	// queue with all packs to return
	private Queue<Pack> q = new LinkedList<>();
	
	
	public PackOptimalFactory(AbstractThiefProblem thief) {
		super(thief);
	}


	@Override
	public Pack next(MyRandom rand) {
		if (q.isEmpty()) {
			q.add(Pack.empty());
			q.add(AlgorithmUtil.calcBestPackingPlan(thief.getItems(), thief.getMaxWeight()));
			for (int i = 0; i < 98; i++) {
				final int maxWeight = (int) (rand.nextDouble() * thief.getMaxWeight());
				Pack p = AlgorithmUtil.calcBestPackingPlan(thief.getItems(), maxWeight);
				q.add(p);
			}
			
		}
		return q.poll();
	}


	
}
