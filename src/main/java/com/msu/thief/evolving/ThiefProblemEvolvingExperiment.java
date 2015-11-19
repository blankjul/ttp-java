package com.msu.thief.evolving;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.Configuration;
import com.msu.builder.NSGAIIBuilder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.AProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.operators.crossover.SinglePointCrossover;
import com.msu.thief.algorithms.exhaustive.ThiefExhaustive;
import com.msu.thief.analyze.ThiefAmountOfDifferentTours;
import com.msu.thief.analyze.ThiefAmountOfOptimalTourInFront;
import com.msu.thief.evolving.measures.OptimalTourHypervolume;
import com.msu.thief.evolving.tsp.SalesmanProblemMutation;
import com.msu.thief.evolving.tsp.SalesmanProblemVariableFactory;
import com.msu.thief.io.writer.JsonThiefProblemWriter;
import com.msu.thief.problems.ThiefProblem;
import com.msu.util.Random;


/**
 * 
 * VariatyInTheFront
 * OptimalTourIsDominating
 *
 */
public class ThiefProblemEvolvingExperiment extends AExperiment  {

	final public static int NUM_OF_CITIES = 100;
	
	final public static int NUM_OF_GENERATIONS = 10;
	
	final public static int NUM_OF_INDIVIDUALS = 5;
	
	
	@SuppressWarnings("unused")
	private class EvolvingProblem extends AProblem<ThiefProblemVariable> {

		@Override
		public int getNumberOfObjectives() {
			return 2;
		}

		@Override
		protected void evaluate_(ThiefProblemVariable var, List<Double> objectives, List<Double> constraintViolations) {
			objectives.add((double) new ThiefAmountOfOptimalTourInFront().analyze(var.get()));
			objectives.add((double) - new ThiefAmountOfDifferentTours().analyze(new ThiefExhaustive().run(var.get(), new Evaluator(Integer.MAX_VALUE), new Random())));
		}
	}
	
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.add(new OptimalTourHypervolume());
	}
	
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.set("factory", new SalesmanProblemVariableFactory(NUM_OF_CITIES));
		builder.set("crossover", new SinglePointCrossover<>());
		builder.set("mutation", new SalesmanProblemMutation());
		builder.set("populationSize", NUM_OF_INDIVIDUALS);
		algorithms.add(builder.build());
	}
	
	



	@Override
	protected void finalize() {
		
		NonDominatedSolutionSet set = result.getFirst(problems.get(0), algorithms.get(0), "");
		//IProblem p = ObjectFactory.create(IProblem.class, PROBLEM);
		//Evaluator eval = new Evaluator(p);
		System.out.println(set);
		
		
		for (int i = 0; i < set.size(); i++) {
			
			//if (i == 1) break;
			
			ThiefProblemVariable var = (ThiefProblemVariable) set.get(i).getVariable();
			ThiefProblem best = var.get();
			String file = "../ttp-benchmark/EA_example0" + i + ".ttp";
			new JsonThiefProblemWriter().write(best,file );
			
			//System.out.println(new ThiefExhaustive().run(best));
			
			//new ThiefAmountOfOptimalTourInFront().analyze(best);
			
			/*
			
			System.out.println("----------------------------");
			NonDominatedSolutionSet front = new ThiefExhaustive().setOnlyNonDominatedPoints(true).run(best);
			ColoredTourScatterPlot plot = new ColoredTourScatterPlot("test");
			plot.add(front, "");
			AVisualize.show(plot);
			
			
			ThiefProblem problem = new JsonThiefReader().read(file);
			
			TTPVariable randomVar = new TTPVariable(new StandardTour(Arrays.asList(0, 3, 4, 1, 2)), new BooleanPackingList(Arrays.asList(false, false, false, false, false)));
			System.out.println(best.evaluate(randomVar));
			System.out.println(problem.evaluate(randomVar));
			
		
			
			System.out.println(front);
			System.out.println(String.format("FactoryThiefProblem var: %s", eval.evaluate(var)));
			System.out.println(String.format("FactoryThiefProblem var: %s", eval.evaluate(var)));
			System.out.println(String.format("FactoryThiefProblem var: %s", eval.evaluate(var)));
			
			System.out.println(String.format("FactoryThiefProblem: %s", eval.evaluate(new FactoryThiefVariable(problem))));
			System.out.println(new ExhaustiveThief().run(problem));
			
			problem = new JsonThiefReader().read(file);
			System.out.println(String.format("FactoryThiefProblem: %s", eval.evaluate(new FactoryThiefVariable(problem))));
			System.out.println(new ExhaustiveThief().run(problem));
			*/
		}	
		
	}
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		Configuration.PATH_TO_EAF = "../moo-java/vendor/aft-0.95/eaf";
		Configuration.PATH_TO_HYPERVOLUME = "../moo-java/vendor/hv-1.3-src/hv";
		
		AExperiment experiment = new ThiefProblemEvolvingExperiment();
		experiment.run(NUM_OF_GENERATIONS * NUM_OF_INDIVIDUALS, 1 , 123456);
		
	}
	
	



}
