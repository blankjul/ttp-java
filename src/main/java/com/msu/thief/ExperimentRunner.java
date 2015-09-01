package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.thief.experiment.knp.KnpReducedExperiment;

public class ExperimentRunner extends com.msu.moo.ExperimentRunner {
	
	
	public final static long maxEvaluations = 50000;
	public final static int iterations = 10;
	public final static long seed = 1238;
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		ExperimentRunner er = new ExperimentRunner();
		er.pathToEAF = "../moo-java/vendor/aft-0.95/eaf";
		er.pathToHV = "../moo-java/vendor/hv-1.3-src/hv";
		er.calcMedianFront = false;
		
		//er.execute(new NSGAIIOperatorExperiment(), "NSGA Operator");
		er.execute(new KnpReducedExperiment(), "KnpReducedExperiment", maxEvaluations, iterations, seed);
		//er.execute(new TspReducedExperiment(), "KnpReducedExperiment", maxEvaluations, iterations, seed);
		//er.execute(new TSPOperatorExperiment(), "TSPOperatorExperiment", maxEvaluations, iterations, seed);
		
	}

	
	
	
	
}
