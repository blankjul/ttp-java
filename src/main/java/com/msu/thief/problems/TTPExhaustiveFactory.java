package com.msu.thief.problems;

import java.util.LinkedList;
import java.util.Queue;

import com.msu.knp.KnapsackExhaustiveFactory;
import com.msu.knp.KnapsackProblem;
import com.msu.knp.KnapsackVariable;
import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.moo.util.Pair;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.TSPExhaustiveFactory;
import com.msu.tsp.TravellingSalesmanProblem;

public class TTPExhaustiveFactory implements VariableFactory<TTPVariable,TravellingThiefProblem> {

	
	//! all the permutations that exist
	protected Queue<TTPVariable> Q = null;
	
	protected TSPExhaustiveFactory facTSP = new TSPExhaustiveFactory();
	protected KnapsackExhaustiveFactory facKNP = new KnapsackExhaustiveFactory();


	@Override
	public TTPVariable create(TravellingThiefProblem problem) {
		
		KnapsackProblem knp = new KnapsackProblem(problem.getMaxWeight(), problem.items.getItems());
		TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(problem.map);
		
		if (Q == null) {
			Q = new LinkedList<>();
			Tour<?> t = facTSP.create(tsp);
			PackingList<?> p = facKNP.create(knp).get();
			while (t != null) {
				while (p != null) {
					Q.add(new TTPVariable(Pair.create(t, p)));
					
					KnapsackVariable var = facKNP.create(knp);
					if (var != null) p = var.get();
					else p = null;
				}
				facKNP = new KnapsackExhaustiveFactory();
				p = facKNP.create(knp).get();
				t = facTSP.create(tsp);
			}
		}
		if (Q.isEmpty()) return null;
		return Q.poll();

	}



}
