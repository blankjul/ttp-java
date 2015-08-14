package com.moo.algorithms;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import com.moo.ttp.jmetal.jCrossover;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jMutation;
import com.moo.ttp.jmetal.jProblem;
import com.moo.ttp.jmetal.jSolution;
import com.moo.ttp.jmetal.jVariable;
import com.moo.ttp.model.packing.BooleanPackingList;
import com.moo.ttp.model.packing.IPackingList;
import com.moo.ttp.model.tour.ITour;
import com.moo.ttp.model.tour.StandardTour;

public class NSGAII extends org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII<jISolution> {

	protected int evaluations;
	protected int maxEvaluations;
	
	protected ITour tour;
	protected IPackingList pack;
	
	protected jProblem jp = null;
	
	protected String name = "NSGAII";
	
	
	public NSGAII(jProblem p, int maxEvaluations, int populationSize, ITour tour, 
			IPackingList pack, jCrossover cross, jMutation mut, String name) {
		super(p, -1, populationSize, cross, mut, new BinaryTournamentSelection<jISolution>(),
				new SequentialSolutionListEvaluator<jISolution>());
		this.tour = tour;
		this.pack = pack;
		this.jp = p;
		this.maxEvaluations = maxEvaluations;
		this.name = name;
	}

	public NSGAII(jProblem p, int maxEvaluations, int populationSize, String name) {
		this(p,maxEvaluations, populationSize, new StandardTour(null), new BooleanPackingList(null), new jCrossover(), new jMutation(), name);
	}

	@Override
	protected List<jISolution> createInitialPopulation() {
		List<jISolution> population = new ArrayList<>(populationSize);
		
		for (int i = 0; i < populationSize; i++) {
			jISolution newIndividual = new jSolution(new jVariable(jp.ttp.numOfCities(), jp.ttp.numOfItems(), tour, pack));
			population.add(newIndividual);
		}
		return population;
	}

	public String toString() {
		return name;
		//return "NSGAII-" + maxEvaluations + "-" + populationSize;
	}

	@Override
	protected void initProgress() {
		evaluations = populationSize;
	}

	@Override
	protected void updateProgress() {
		evaluations += populationSize;
	}

	@Override
	protected boolean isStoppingConditionReached() {
		return evaluations >= maxEvaluations;
	}

}
