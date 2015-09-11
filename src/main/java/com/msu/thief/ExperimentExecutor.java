package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.Configuration;
import com.msu.moo.experiment.AbstractExperiment;
import com.msu.moo.util.ObjectFactory;

public class ExperimentExecutor {

	/*
	 * EXERIMENTS AVAILABLE
	 * GreedyMapExperiment, TSPOperatorExperiment, NSGAIIOperatorExperiment, PublicationExperiment
	 * 
	 * TSP
	 * tsp.Bays29Experiment, 
	 * 
	 */
	
	//! experiment that should be executed
	protected final static String EXPERIMENT = "com.msu.thief.experiment.tsp.Bays29Experiment";
	
	//! number of iterations per experiment
	protected final static int ITERATIONS = 10;
	
	//! max evaluations per run
	protected final static long MAX_EVALUATIONS = 50000;
	
	//! random seed for experiment execution
	protected final static long SEED = 85678;		
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		Configuration.pathToEAF = "../moo-java/vendor/aft-0.95/eaf";
		Configuration.pathToHV = "../moo-java/vendor/hv-1.3-src/hv";
		
		AbstractExperiment<?> experiment = ObjectFactory.create(AbstractExperiment.class,  EXPERIMENT);
		
		experiment.run(MAX_EVALUATIONS, ITERATIONS, SEED);
		experiment.report();
	}
	

}
