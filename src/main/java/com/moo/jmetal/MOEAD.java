package com.moo.jmetal;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.uma.jmetal.algorithm.multiobjective.moead.util.MOEADUtils;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import com.moo.ttp.jmetal.jCrossover;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jMutation;

public class MOEAD extends org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD<jISolution> {

	public MOEAD(Problem<jISolution> problem, int maxEvaluations) {
		super(problem, 0, 0, maxEvaluations, null, null, null, null, 0.0d, 0, 0);
		this.problem = problem;

		this.populationSize = 100;
		this.resultPopulationSize = 100;
		this.maxEvaluations = maxEvaluations;
		this.mutationOperator = new jMutation();
		this.crossoverOperator = new jCrossover();
		this.functionType = MOEAD.FunctionType.TCHE;
		this.dataDirectory = "";
		this.neighborhoodSelectionProbability = 0.1;
		this.maximumNumberOfReplacedSolutions = 100;
		this.neighborSize = 20;

		randomGenerator = JMetalRandom.getInstance();
		population = new ArrayList<jISolution>(populationSize);
		indArray = new Solution[problem.getNumberOfObjectives()];
		neighborhood = new int[populationSize][neighborSize];
		idealPoint = new double[problem.getNumberOfObjectives()];
		lambda = new double[populationSize][problem.getNumberOfObjectives()];
	}

	public String toString() {
		return "MOEAD-" + this.maxEvaluations;
	}

	@Override
	public void run() {

		initializePopulation();
		initializeUniformWeight();
		initializeNeighborhood();
		initializeIdealPoint();

		evaluations = populationSize;
		do {
			int[] permutation = new int[populationSize];
			MOEADUtils.randomPermutation(permutation, populationSize);

			for (int i = 0; i < populationSize; i++) {
				int subProblemId = permutation[i];

				NeighborType neighborType = NeighborType.NEIGHBOR;
				List<jISolution> parents = parentSelection(subProblemId, neighborType);
				List<jISolution> children = crossoverOperator.execute(parents);

				jISolution child = children.get(0);
				mutationOperator.execute(child);
				problem.evaluate(child);

				evaluations++;

				updateIdealPoint(child);
				updateNeighborhood(child, subProblemId, neighborType);
			}
			
		} while (evaluations < maxEvaluations);

	}

	protected List<jISolution> parentSelection(int subProblemId, NeighborType neighborType) {
		    Vector<Integer> matingPool = new Vector<Integer>();
		    matingSelection(matingPool, subProblemId, neighborType);
		    List<jISolution> parents = new ArrayList<jISolution>(2);
		    parents.add(population.get(matingPool.get(0)));
		    parents.add(population.get(subProblemId));
		    return parents ;
		  }

	protected void initializePopulation() {
		for (int i = 0; i < populationSize; i++) {
			jISolution newSolution = (jISolution) problem.createSolution();
			problem.evaluate(newSolution);
			population.add(newSolution);
		}
	}

	@Override
	public List<jISolution> getResult() {
		//return population;
		NonDominatedSolutionListArchive<jISolution> ref = new NonDominatedSolutionListArchive<jISolution>();
		for (jISolution entry : population) {
			ref.add(entry);
		}
		return ref.getSolutionList() ;
		
	}

	protected void updateIdealPoint(jISolution individual) {
		for (int n = 0; n < problem.getNumberOfObjectives(); n++) {
			if (individual.getObjective(n) < idealPoint[n]) {
				idealPoint[n] = individual.getObjective(n);
			}
		}
	}

	protected void updateNeighborhood(jISolution individual, int subProblemId, NeighborType neighborType) throws JMetalException {
		int size;
		int time;

		time = 0;

		if (neighborType == NeighborType.NEIGHBOR) {
			size = neighborhood[subProblemId].length;
		} else {
			size = population.size();
		}
		int[] perm = new int[size];

		MOEADUtils.randomPermutation(perm, size);

		for (int i = 0; i < size; i++) {
			int k;
			if (neighborType == NeighborType.NEIGHBOR) {
				k = neighborhood[subProblemId][perm[i]];
			} else {
				k = perm[i];
			}
			double f1, f2;

			f1 = fitnessFunction(population.get(k), lambda[k]);
			f2 = fitnessFunction(individual, lambda[k]);

			if (f2 < f1) {
				population.set(k, (jISolution) individual.copy());
				time++;
			}

			if (time >= maximumNumberOfReplacedSolutions) {
				return;
			}
		}
	}

	protected double fitnessFunction(final jISolution individual, final double[] lambda) throws JMetalException {
		double fitness;

		if (MOEAD.FunctionType.TCHE.equals(functionType)) {
			double maxFun = -1.0e+30;

			for (int n = 0; n < problem.getNumberOfObjectives(); n++) {
				double diff = Math.abs(individual.getObjective(n) - idealPoint[n]);

				double feval;
				if (lambda[n] == 0) {
					feval = 0.0001 * diff;
				} else {
					feval = diff * lambda[n];
				}
				if (feval > maxFun) {
					maxFun = feval;
				}
			}

			fitness = maxFun;
		} else if (MOEAD.FunctionType.AGG.equals(functionType)) {
			double sum = 0.0;
			for (int n = 0; n < problem.getNumberOfObjectives(); n++) {
				sum += (lambda[n]) * individual.getObjective(n);
			}

			fitness = sum;

		} else if (MOEAD.FunctionType.PBI.equals(functionType)) {
			double d1, d2, nl;
			double theta = 5.0;

			d1 = d2 = nl = 0.0;

			for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
				d1 += (individual.getObjective(i) - idealPoint[i]) * lambda[i];
				nl += Math.pow(lambda[i], 2.0);
			}
			nl = Math.sqrt(nl);
			d1 = Math.abs(d1) / nl;

			for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
				d2 += Math.pow((individual.getObjective(i) - idealPoint[i]) - d1 * (lambda[i] / nl), 2.0);
			}
			d2 = Math.sqrt(d2);

			fitness = (d1 + theta * d2);
		} else {
			throw new JMetalException(" MOEAD.fitnessFunction: unknown type " + functionType);
		}
		return fitness;
	}

}
