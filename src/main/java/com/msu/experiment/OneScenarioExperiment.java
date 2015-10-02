package com.msu.experiment;

import java.util.List;

import com.msu.algorithms.ExhaustiveSalesman;
import com.msu.algorithms.ExhaustiveThief;
import com.msu.io.reader.JsonThiefReader;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.report.SolutionSetReport;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.FinishedProblemExecution;
import com.msu.thief.ThiefProblem;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.visualize.ThiefVisualizer;


/**
 * 
 * Different scenarios could be read by the different reader.
 * 
 * bonyadi_multi_publication.ttp
 * bonyadi_single_publication.ttp
 * opt_tour_performs_bad.ttp
 * my_publication.ttp
 * EA_example.ttp
 * 
 *
 */
public class OneScenarioExperiment extends AExperiment {

	
	final public ThiefProblem PROBLEM = new JsonThiefReader().read("../ttp-benchmark/EA_example01.ttp");
	//final public ThiefProblem PROBLEM  = new RandomTTPScenario(6, 2, 0.5, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject();
	
	final public boolean SHOW_ALL = false;
	
	
	@Override
	public void finalize() {
		ThiefProblem ttp = (ThiefProblem) problems.get(0);
		TravellingSalesmanProblem p = new TravellingSalesmanProblem(ttp.getMap());
		new ExhaustiveSalesman().run(new Evaluator(p));
		new ThiefVisualizer<ThiefProblem>().show(this);

	}
	
	@Override
	protected void initialize() {
		SolutionSetReport report = new SolutionSetReport();
		//report.set("experiment/scenario.csv");
		EventDispatcher.getInstance().register(FinishedProblemExecution.class, report);
	}


	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		//algorithms.add(AlgorithmFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(new ExhaustiveThief().setOnlyNonDominatedPoints(!SHOW_ALL));
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.add(PROBLEM);
	}

}