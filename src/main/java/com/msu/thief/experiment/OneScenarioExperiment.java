package com.msu.thief.experiment;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.report.SolutionSetReport;
import com.msu.thief.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.thief.algorithms.exhaustive.ThiefExhaustive;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.visualize.ThiefVisualizer;
import com.msu.util.FileCollectorParser;
import com.msu.util.MyRandom;


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
		new SalesmanExhaustive().run(p, new Evaluator(Integer.MAX_VALUE), new MyRandom());
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