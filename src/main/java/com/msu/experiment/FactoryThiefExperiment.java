package com.msu.experiment;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.io.writer.JsonThiefProblemWriter;
import com.msu.meta.FactoryThiefCrossover;
import com.msu.meta.FactoryThiefMutation;
import com.msu.meta.FactoryThiefVariable;
import com.msu.meta.FactoryThiefVariableFactory;
import com.msu.moo.Configuration;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.ObjectFactory;
import com.msu.thief.ThiefProblem;
import com.msu.visualize.ColoredTourScatterPlot;


/**
 * 
 * VariatyInTheFront
 * OptimalTourIsDominating
 *
 */
public class FactoryThiefExperiment extends AExperiment  {

	
	final public static String PROBLEM = "com.msu.meta.problems.VariatyInTheFront";
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new FactoryThiefVariableFactory(6));
		builder.setCrossover(new FactoryThiefCrossover());
		builder.setMutation(new FactoryThiefMutation());
		builder.setProbMutation(0.3);
		builder.setPopulationSize(100);
		algorithms.add(builder.create());
	}
	
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		IProblem p = ObjectFactory.create(IProblem.class, PROBLEM);
		problems.add(p);
	}
	


	@Override
	protected void finalize() {
		
		NonDominatedSolutionSet set = result.getFirst(problems.get(0), algorithms.get(0), "");
		IProblem p = ObjectFactory.create(IProblem.class, PROBLEM);
		//Evaluator eval = new Evaluator(p);
		System.out.println(set);
		
		
		for (int i = 0; i < set.size(); i++) {
			
			FactoryThiefVariable var = (FactoryThiefVariable) set.get(i).getVariable();
			ThiefProblem best = var.get();
			String file = "../ttp-benchmark/EA_example0" + i + ".ttp";
			new JsonThiefProblemWriter().write(best,file );
			
			System.out.println("----------------------------");
			NonDominatedSolutionSet front = new ExhaustiveThief().setOnlyNonDominatedPoints(true).run(best);
			ColoredTourScatterPlot plot = new ColoredTourScatterPlot("test");
			plot.add(front, "");
			AVisualize.show(plot);
			
			
			/*
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
		
		AExperiment experiment = new FactoryThiefExperiment();
		experiment.run(40000, 1 , 7864876);
	}
	
	



}
