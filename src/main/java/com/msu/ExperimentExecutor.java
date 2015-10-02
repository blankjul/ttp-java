package com.msu;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.Configuration;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.util.ObjectFactory;

public class ExperimentExecutor {

	/*
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
	
	//! experiment that should be executed
	protected final static String PREFIX = "com.msu.experiment.";
	
	//! experiment that should be executed
	protected final static String EXPERIMENT = "OneScenarioExperiment";
	
	//! number of iterations per experiment
	protected final static int ITERATIONS = 1;
	
	//! max evaluations per run
	protected final static int MAX_EVALUATIONS = 500000;
	
	//! random seed for experiment execution
	protected final static long SEED = 123456;		
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		Configuration.PATH_TO_EAF = "../moo-java/vendor/aft-0.95/eaf";
		Configuration.PATH_TO_HYPERVOLUME = "../moo-java/vendor/hv-1.3-src/hv";
		
		AExperiment experiment = ObjectFactory.create(AExperiment.class,  PREFIX + EXPERIMENT);
		experiment.run(MAX_EVALUATIONS, ITERATIONS, SEED);
		
		
	}
	

}
