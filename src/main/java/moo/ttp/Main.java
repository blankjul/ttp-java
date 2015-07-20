package moo.ttp;

import moo.ttp.moea.MOEAProblem;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;



public class Main 
{
	public static void main(String[] args) {
		
		
		Executor executor = new Executor()
		    .withProblemClass(MOEAProblem.class)
		    .withAlgorithm("Random")
		    .withProperty("operator", "ttp")
		    .withMaxEvaluations(100000);
		

		NondominatedPopulation result = executor.run();
		
		//display the results
		System.out.format("Objective1  Objective2%n");
		
		for (Solution solution : result) {
			System.out.format("%.4f      %.4f%n",
					solution.getObjective(0),
					solution.getObjective(1));
		}
	}
	
	
}
