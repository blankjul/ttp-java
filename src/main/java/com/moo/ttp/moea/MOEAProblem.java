package com.moo.ttp.moea;

import java.util.Collections;
import java.util.LinkedList;

import org.moeaframework.core.Solution;
import org.moeaframework.problem.AbstractProblem;

import com.moo.ttp.model.Item;
import com.moo.ttp.model.Map;
import com.moo.ttp.problems.TravellingThiefProblem;
import com.moo.ttp.util.Pair;
import com.moo.ttp.util.Rnd;

public class MOEAProblem extends AbstractProblem {

	private TravellingThiefProblem ttp;
	

	
	public MOEAProblem() {
		super(1, 2);
		
		Map m = new Map(4);
        m.set(0,1,5);
        m.set(0,2,6);
        m.set(0,3,6);
        m.set(1,2,5);
        m.set(1,3,6);
        m.set(2,3,4);
        ttp  = new TravellingThiefProblem(m, 3);
        ttp.addItem(2, new Item(10, 3));
        ttp.addItem(2, new Item(4, 1));
        ttp.addItem(2, new Item(4, 1));
        ttp.addItem(1, new Item(2, 2));
        ttp.addItem(2, new Item(3, 3));
        ttp.addItem(3, new Item(2, 2));
	}
	
	public MOEAProblem(TravellingThiefProblem ttp) {
		super(1, 2);
		this.ttp = ttp;
	}

	
	public Solution newSolution() {
		Solution solution = new Solution(getNumberOfVariables(), getNumberOfObjectives());
		
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 1; i < ttp.numOfCities(); i++) {
			indices.add(i);
		}

		Collections.shuffle(indices);
		indices.addFirst(0);
		int[] pi = new int[indices.size()];
		for (int j = 0; j < pi.length; j++) {
			pi[j] = indices.get(j);
		}
		
		boolean[] pickingPlan = new boolean[ttp.numOfItems()];
		for (int i = 0; i < pickingPlan.length; i++) {
			pickingPlan[i] = Rnd.rndDouble() < 0.5;
		}
		
		//solution.setVariable(0, new MOEAVariable(pi, pickingPlan));
		
		return solution;
	}

	
	
	public void evaluate(Solution solution) {

		double[] f = new double[numberOfObjectives];
		
		MOEAVariable var  = (MOEAVariable) solution.getVariable(0);
		Pair<Double,Double> result = ttp.evaluate(var.getPi(), var.getPickingPlan());

		f[0] = result.first;
		f[1] = - result.second;

		solution.setObjectives(f);
	}

}
