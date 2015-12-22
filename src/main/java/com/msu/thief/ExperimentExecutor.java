package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.Configuration;
import com.msu.experiment.AExperiment;
import com.msu.util.ObjectFactory;

/**
 * EXERIMENTS AVAILABLE
 * GreedyMapExperiment, TSPOperatorExperiment, NSGAIIOperatorExperiment, PublicationExperiment
 * BonyadiExperiment, NSGAIIOperatorExperiment, OneScenarioExperiment
 * 
 * TSP
 * TSPExperiment
 * 
 * KNP
 * KNPExperiment
 * 
 * Bonyadi
 * BonyadiTSPLIBExperiment, BonyadiSingleExperiment, BonyadiMultiExperiment
 * 
 */
public class ExperimentExecutor {

	
	//! experiment that should be executed
	protected final static String PREFIX = "com.msu.thief.experiment.";
	
	//! experiment that should be executed
	protected final static String EXPERIMENT = "IEEE.CoevolutionProblemExperiment";
	
	//! number of iterations per experiment
	protected final static int ITERATIONS = 1;
	
	//! number of threads
	protected final static int NUM_OF_THREADS = 8;
	
	//! max evaluations per run
	protected final static int MAX_EVALUATIONS = 500000;
	
	//! random seed for experiment executions
	protected final static long SEED = 123456;		
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		Configuration.PATH_TO_EAF = "../moo-java/vendor/bin/eaf";
		Configuration.PATH_TO_HYPERVOLUME = "../moo-java/vendor/bin/hv";
		Configuration.NUM_OF_THREADS = NUM_OF_THREADS;
		
		AExperiment experiment = ObjectFactory.create(AExperiment.class,  PREFIX + EXPERIMENT);
		experiment.run(MAX_EVALUATIONS, ITERATIONS, SEED);
		
	}
	

}
