package com.msu.meta;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.Configuration;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;

public class FactoryThiefExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new FactoryThiefVariableFactory(5));
		builder.setCrossover(new FactoryThiefCrossover());
		builder.setMutation(new FactoryThiefMutation());
		builder.setProbMutation(0.3);
		builder.setPopulationSize(20);
		algorithms.add(builder.create());
	}
	

	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.add(new FactoryThiefProblem());
	}
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		Configuration.PATH_TO_EAF = "../moo-java/vendor/aft-0.95/eaf";
		Configuration.PATH_TO_HYPERVOLUME = "../moo-java/vendor/hv-1.3-src/hv";
		
		AExperiment experiment = new FactoryThiefExperiment();
		//experiment.setOutputDir(OUTPUT_DIR);
		experiment.setVisualize(true);
		experiment.run(10000, 1, 12345);
	}

}
