package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.Configuration;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.util.ObjectFactory;

/**
 * EXERIMENTS AVAILABLE GreedyMapExperiment, TSPOperatorExperiment,
 * NSGAIIOperatorExperiment, PublicationExperiment BonyadiExperiment,
 * NSGAIIOperatorExperiment, OneScenarioExperiment
 * 
 * TSP TSPExperiment
 * 
 * KNP KNPExperiment
 * 
 * Bonyadi BonyadiTSPLIBExperiment, BonyadiSingleExperiment,
 * BonyadiMultiExperiment
 * 
 */
public class ExperimentExecutor {

	// ! experiment that should be executed
	protected final static String PREFIX = "com.msu.thief.experiment.";

	// ! experiment that should be executed
	protected final static String EXPERIMENT = "IEEE.BenchmarkExperimentSingle";

	// ! number of iterations per experiment
	protected final static int ITERATIONS = 1;

	// ! max evaluations per run
	protected final static IEvaluator EVALUATOR = new StandardEvaluator(100000);

	// ! random seed for experiment executions
	protected final static long SEED = 123456;

	// ! number of threads
	protected final static int NUM_OF_THREADS = 8;

	
	@SuppressWarnings({ "unchecked"})
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Configuration.PATH_TO_EAF = "../moo-java/vendor/bin/eaf";
		Configuration.PATH_TO_HYPERVOLUME = "../moo-java/vendor/bin/hv";
		
		AExperiment<?, IVariable, IProblem<IVariable>> experiment = ObjectFactory.create(AExperiment.class, PREFIX + EXPERIMENT);
		experiment.run(EVALUATOR, ITERATIONS, SEED, NUM_OF_THREADS);
		
	}
	



}
