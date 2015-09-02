package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.experiment.AbstractExperiment;
import com.msu.thief.experiment.NSGAIIOperatorExperiment;
import com.msu.thief.experiment.ReducedToKnpExperiment;
import com.msu.thief.experiment.ReducedToTSPBays29Experiment;
import com.msu.thief.experiment.ReducedToTSPExperiment;
import com.msu.thief.experiment.TSPOperatorExperiment;

public class ExperimentExecutor {

	protected final static ReducedToTSPExperiment reducedTSPExp = new ReducedToTSPExperiment();
	protected final static ReducedToTSPBays29Experiment reducedTSPBays29Exp = new ReducedToTSPBays29Experiment();
	protected final static ReducedToKnpExperiment reducedKnpExp = new ReducedToKnpExperiment();
	protected final static NSGAIIOperatorExperiment nsgaIIExp = new NSGAIIOperatorExperiment();
	protected final static TSPOperatorExperiment tspOperatorExp = new TSPOperatorExperiment();
	
	
	protected static final AbstractExperiment<?> exp = reducedTSPBays29Exp;
	protected final static int iterations = 1;
	protected final static long maxEvaluations = 1000000;
	protected final static long seed = 12345;		
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		exp.setPathToEAF("../moo-java/vendor/aft-0.95/eaf");
		exp.setPathToHV("../moo-java/vendor/hv-1.3-src/hv");
		exp.run(maxEvaluations, iterations, seed);
		exp.visualize();
	}

}
