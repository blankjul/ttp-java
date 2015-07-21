package com.moo.ttp.factory;

import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearch;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import com.moo.ttp.jmetal.jCrossover;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jMutation;
import com.moo.ttp.jmetal.jProblem;
import com.moo.ttp.problems.TravellingThiefProblem;

public class AlgorithmFactory {



	public static Algorithm<List<jISolution>> create(String algorithm, TravellingThiefProblem ttp, int maxEvaluations) throws jmetal.util.JMException {
		if (algorithm.equals("NSGAII")) {
			return new NSGAII<jISolution>(new jProblem(ttp), maxEvaluations, 100,
				      new jCrossover(), new jMutation(),
				      new BinaryTournamentSelection<jISolution>(), new SequentialSolutionListEvaluator<jISolution>());
		} else if (algorithm.equals("Random")) {
			return new RandomSearch<jISolution>(new jProblem(ttp), maxEvaluations);
		} else
			throw new RuntimeException("Algorithm not found!");
	}

}
