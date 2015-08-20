package com.moo.jmetal;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.uma.jmetal.algorithm.multiobjective.nsgaiii.EnvironmentalSelectionNSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.ReferencePoint;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.solutionattribute.Ranking;

import com.moo.ttp.jmetal.jCrossover;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jMutation;

public class NSGAIII extends org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII<jISolution> {

	private Vector<Integer> numberOfDivisions;
	private List<ReferencePoint<jISolution>> referencePoints = new Vector<ReferencePoint<jISolution>>();

	public NSGAIII(Problem<jISolution> p, int maxEvaluations, int populationSize, int divisions) {
		super();
		this.problem = p;
		this.maxEvaluations = maxEvaluations;
		this.populationSize = populationSize;
		this.evaluator = new SequentialSolutionListEvaluator<jISolution>();
		this.crossoverOperator = new jCrossover();
		this.mutationOperator = new jMutation();
		this.selectionOperator = new BinaryTournamentSelection<jISolution>();

		numberOfDivisions = new Vector<Integer>(1);
		numberOfDivisions.add(divisions);

		ReferencePoint.generateReferencePoints(referencePoints, problem.getNumberOfObjectives(), numberOfDivisions);

		populationSize = referencePoints.size();
		while (populationSize % 4 > 0)
			populationSize++;

	}

	@Override
	protected List<jISolution> replacement(List<jISolution> population, List<jISolution> offspringPopulation) {
		List<jISolution> jointPopulation = new ArrayList<jISolution>();
		jointPopulation.addAll(population);
		jointPopulation.addAll(offspringPopulation);

		Ranking<jISolution> ranking = computeRanking(jointPopulation);

		List<jISolution> pop = new ArrayList<jISolution>();
		List<List<jISolution>> fronts = new ArrayList<List<jISolution>>();
		int rankingIndex = 0;
		int candidateSolutions = 0;
		while (candidateSolutions < populationSize) {
			fronts.add(ranking.getSubfront(rankingIndex));
			candidateSolutions += ranking.getSubfront(rankingIndex).size();
			if ((pop.size() + ranking.getSubfront(rankingIndex).size()) <= populationSize)
				addRankedSolutionsToPopulation(ranking, rankingIndex, pop);
			rankingIndex++;
		}

		EnvironmentalSelectionNSGAIII<jISolution> selection = new EnvironmentalSelectionNSGAIII.Builder<jISolution>()
				.setNumberOfObjectives(problem.getNumberOfObjectives()).setFronts(fronts).setSolutionsToSelect(populationSize)
				.setReferencePoints(getReferencePointsCopy()).build();

		pop = selection.execute(pop);
		return pop;
	}

	
	private List<ReferencePoint<jISolution>> getReferencePointsCopy() {
		List<ReferencePoint<jISolution>> copy = new ArrayList<ReferencePoint<jISolution>>();
		for (ReferencePoint<jISolution> r : this.referencePoints) {
			copy.add(new ReferencePoint<jISolution>(r));
		}
		return copy;
	}

	public String toString() {
		return "NSGAIII-" + maxEvaluations + "-" + populationSize;
	}

}
