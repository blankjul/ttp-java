package moo.ttp.factory;

import jmetal.core.Algorithm;
import jmetal.experiments.settings.NSGAII_Settings;
import jmetal.experiments.settings.RandomSearch_Settings;
import jmetal.metaheuristics.moead.MOEAD;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.metaheuristics.randomSearch.RandomSearch;
import jmetal.operators.selection.SelectionFactory;
import jmetal.util.JMException;
import moo.ttp.jmetal.ThiefCrossover;
import moo.ttp.jmetal.ThiefMutation;
import moo.ttp.jmetal.ThiefProblem;
import moo.ttp.problems.TravellingThiefProblem;

public class AlgorithmFactory {



	public static Algorithm create(String algorithm, TravellingThiefProblem ttp, int maxEvaluations) {
		try {
			if (algorithm.equals("NSGAII")) {
				NSGAII nsga = new NSGAII(new ThiefProblem("ThiefSolutionType", ttp));
				nsga.setInputParameter("populationSize", 100);
				nsga.setInputParameter("maxEvaluations", maxEvaluations);
				nsga.addOperator("crossover", new ThiefCrossover());
				nsga.addOperator("mutation", new ThiefMutation());
				nsga.addOperator("selection", SelectionFactory.getSelectionOperator("BinaryTournament2", null));
				return nsga;
			} else if (algorithm.equals("Random")) {
				RandomSearch rnd = new RandomSearch(new ThiefProblem("ThiefSolutionType", ttp));
				rnd.setInputParameter("maxEvaluations", maxEvaluations);
				return rnd;
			} else if (algorithm.equals("MOEAD")) {
				MOEAD moead = new MOEAD(new ThiefProblem("ThiefSolutionType", ttp));
				moead.setInputParameter("populationSize", 100);
				moead.setInputParameter("maxEvaluations", maxEvaluations);
				moead.setInputParameter("T", 20) ;
				moead.setInputParameter("dataDirectory", "/home/julesy/output/moead.txt") ;
				moead.setInputParameter("delta", 0.9) ;
				moead.setInputParameter("nr", 2) ;
				moead.addOperator("crossover", new ThiefCrossover());
				moead.addOperator("mutation", new ThiefMutation());
				moead.addOperator("selection", SelectionFactory.getSelectionOperator("BinaryTournament2", null));
				return moead;
			} else
				throw new RuntimeException("Algorithm not found!");
		} catch (JMException e) {
			e.printStackTrace();
		}
		return null;
	}

}
