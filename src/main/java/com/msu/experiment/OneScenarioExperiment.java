package com.msu.experiment;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import com.msu.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.algorithms.exhaustive.ThiefExhaustive;
import com.msu.io.reader.JsonThiefProblemReader;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.report.SolutionSetReport;
import com.msu.moo.util.FileCollectorParser;
import com.msu.problems.ThiefProblem;
import com.msu.problems.SalesmanProblem;
import com.msu.thief.model.CoordinateMap;
import com.msu.visualize.ThiefVisualizer;


/**
 * 
 * Different scenarios could be read by the different reader.
 * 
 * bonyadi_multi_publication.ttp
 * bonyadi_single_publication.ttp
 * opt_tour_performs_bad.ttp
 * front_large_variaty_in_tours.ttp
 * my_publication.ttp
 * EA_example00.ttp
 * 
 *
 */
public class OneScenarioExperiment extends AExperiment {

	final public boolean ONLY_PARETO_FRONT = true;
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark", "opt_tour_performs_optimal.ttp", new JsonThiefProblemReader());
		problems.addAll(fcp.collect());
	}
	
	
	
	@Override
	public void finalize() {
		ThiefProblem ttp = (ThiefProblem) problems.get(0);
		SalesmanProblem p = new SalesmanProblem(ttp.getMap());
		new SalesmanExhaustive().run(new Evaluator(p));
		for(double[] row : ttp.getMap().getDistances()) {
			System.out.println(Arrays.toString(row));
		}
		if (ttp.getMap() instanceof CoordinateMap) {
			for(Point2D point : ((CoordinateMap)ttp.getMap()).getCities()) {
				System.out.println(point);
			}
		}
	}
	
	
	@Override
	protected void initialize() {
		new SolutionSetReport();
		new ThiefVisualizer<>(true).setVisibility(true);
	}


	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(new ThiefExhaustive().setOnlyNonDominatedPoints(ONLY_PARETO_FRONT));
	}



}