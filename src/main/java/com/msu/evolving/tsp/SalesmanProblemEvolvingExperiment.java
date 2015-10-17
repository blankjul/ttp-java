package com.msu.evolving.tsp;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.analyze.TourAverageDistanceToOpt;
import com.msu.io.writer.JsonThiefProblemWriter;
import com.msu.moo.Configuration;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.ItemCollection;


/**
 * 
 * VariatyInTheFront
 * OptimalTourIsDominating
 *
 */
public class SalesmanProblemEvolvingExperiment extends AExperiment  {

	
	final public static int NUM_OF_CITIES = 6;
	
	final public static int NUM_OF_GENERATIONS = 1000;
	
	final public static int NUM_OF_INDIVIDUALS = 20;
	
	private class EvolvingSalesmanProblem extends AProblem<SalesmanProblemVariable> {

		@Override
		public int getNumberOfObjectives() {
			return 1;
		}

		@Override
		protected void evaluate_(SalesmanProblemVariable var, List<Double> objectives, List<Double> constraintViolations) {
			SalesmanProblem tsp = new SalesmanProblem(new CoordinateMap(var.get()));
			objectives.add(new TourAverageDistanceToOpt().analyze(tsp));
		}
		
	}
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new SalesmanProblemVariableFactory(NUM_OF_CITIES));
		builder.setCrossover(new SinglePointCrossover<>());
		builder.setMutation(new SalesmanProblemMutation());
		builder.setPopulationSize(NUM_OF_INDIVIDUALS);
		algorithms.add(builder.create());
	}
	
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.add(new EvolvingSalesmanProblem());
	}
	


	@Override
	protected void finalize() {
		
		NonDominatedSolutionSet set = result.getFirst(problems.get(0), algorithms.get(0), "");
		System.out.println(set);
		
		
		for (int i = 0; i < set.size(); i++) {
			
			if (i == 1) break;
			
			SalesmanProblemVariable var = (SalesmanProblemVariable) set.get(i).getVariable();
			ThiefProblem thief = new ThiefProblem(new CoordinateMap(var.get()), new ItemCollection<>(), 0);
			String file = "../ttp-benchmark/EA_example0" + i + ".ttp";
			new JsonThiefProblemWriter().write(thief,file );
		}	
		
	}
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		Configuration.PATH_TO_EAF = "../moo-java/vendor/aft-0.95/eaf";
		Configuration.PATH_TO_HYPERVOLUME = "../moo-java/vendor/hv-1.3-src/hv";
		
		AExperiment experiment = new SalesmanProblemEvolvingExperiment();
		experiment.run(NUM_OF_GENERATIONS * NUM_OF_INDIVIDUALS, 1 , 123456);
	}
	
	



}
