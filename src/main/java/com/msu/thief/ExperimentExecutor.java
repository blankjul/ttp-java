package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.experiment.AbstractExperiment;
import com.msu.thief.experiment.NSGAIIOperatorExperiment;
import com.msu.thief.experiment.PublicationExperiment;
import com.msu.thief.experiment.ReducedToKnpExperiment;
import com.msu.thief.experiment.ReducedToTSPExperiment;
import com.msu.thief.experiment.TSPOperatorExperiment;
import com.msu.thief.experiment.knp.KNP_13_2000_1000_1_Experiment;
import com.msu.thief.experiment.knp.KNP_13_200_1000_1_Experiment;
import com.msu.thief.experiment.knp.KNP_13_20_1000_1_Experiment;
import com.msu.thief.experiment.tsp.Bays29Experiment;
import com.msu.thief.experiment.tsp.Berlin52Experiment;
import com.msu.thief.experiment.tsp.D198Experiment;
import com.msu.thief.experiment.tsp.Eil101Experiment;

public class ExperimentExecutor {

	protected final static ReducedToTSPExperiment reducedTSPExp = new ReducedToTSPExperiment();
	
	protected final static Bays29Experiment reducedTSPBays29Exp = new Bays29Experiment();
	protected final static Berlin52Experiment berlin52Exp = new Berlin52Experiment();
	protected final static D198Experiment d198Exp = new D198Experiment();
	protected final static Eil101Experiment eil101Exp = new Eil101Experiment();
	
	protected final static KNP_13_20_1000_1_Experiment knp20Exp = new KNP_13_20_1000_1_Experiment();
	protected final static KNP_13_200_1000_1_Experiment knp200Exp = new KNP_13_200_1000_1_Experiment();
	protected final static KNP_13_2000_1000_1_Experiment knp2000Exp = new KNP_13_2000_1000_1_Experiment();
	

	protected final static ReducedToKnpExperiment reducedKnpExp = new ReducedToKnpExperiment();
	protected final static NSGAIIOperatorExperiment nsgaIIExp = new NSGAIIOperatorExperiment();
	protected final static TSPOperatorExperiment tspOperatorExp = new TSPOperatorExperiment();
	protected final static PublicationExperiment pubExp = new PublicationExperiment();
	
	protected static final AbstractExperiment<?> exp = eil101Exp;
	protected final static int iterations = 10;
	protected final static long maxEvaluations = 500000;
	//protected final static long seed = 5646;		
	protected final static long seed = 85678;		
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		exp.setPathToEAF("../moo-java/vendor/aft-0.95/eaf");
		exp.setPathToHV("../moo-java/vendor/hv-1.3-src/hv");
		exp.run(maxEvaluations, iterations, seed);
		exp.visualize();
	}

}
