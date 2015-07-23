package com.moo.ttp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;

import com.google.gson.Gson;
import com.moo.ttp.experiment.Experiment;
import com.moo.ttp.factory.ThiefFactory;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jProblem;
import com.moo.ttp.json.JsonBuilder;
import com.moo.ttp.util.Util;

public class Study {

	public static void main(String[] args) {
		if (args.length == 1) Study.OUTPUT_DIR = args[0];
		Study s = new Study();
		s.run();
	}

	public static String OUTPUT_DIR = "./experiment";
	public final static int MAX_EVALUATIONS = 25000;
	public final int NUM_OF_RUNS = 10;
	public Gson gson = new Gson();
	public List<Problem<jISolution>> problems;
	public List<Algorithm<List<jISolution>>> algorithms;
	
	private Experiment experiment = null;
	

	public Study() {
		problems = configureProblemList();
		experiment = new Experiment();
	}

	public void run() {
		
		new File("./experiment").mkdirs();

		for (int i = 0; i < problems.size(); i++) {

			Problem<jISolution> p = problems.get(i);
			List<Algorithm<List<jISolution>>> algorithms = configureAlgorithmList(p);
			
			NonDominatedSolutionListArchive<jISolution> ref = new NonDominatedSolutionListArchive<jISolution>();

			for (int j = 0; j < algorithms.size(); j++) {
				

				for (int k = 0; k < NUM_OF_RUNS; k++) {

					// calculate the front using the algorithm
					Algorithm<List<jISolution>> a = algorithms.get(j);
					a.run();
					List<jISolution> front = a.getResult();
					experiment.addFront(p, a, front);

					// print the front to file
					String path = String.format("%s/%s_%s_%s.pf", OUTPUT_DIR, p.toString(), a.toString(), k);
					Util.write(path, gson.toJson(JsonBuilder.toJson(front)));
					
					for (jISolution s : front) ref.add(s);

				}
				
				experiment.addReferenceFront(p, ref.getSolutionList());
				
				
			}
			
			// write all the indicator values
			for (int j = 0; j < algorithms.size(); j++) {
				Algorithm<List<jISolution>> a = algorithms.get(j);
				
				for(Map.Entry<String, double[]> entry : experiment.calcIndicator(p, a).entrySet()) {
					Util.write(String.format("%s/%s_%s.%s", OUTPUT_DIR, p.toString(), a.toString(), 
							entry.getKey()), gson.toJson(entry.getValue()));
				}
			}
			
			
			String path = String.format("%s/%s.pf", OUTPUT_DIR, p.toString());
			Util.write(path, gson.toJson(JsonBuilder.toJson(ref.getSolutionList())));
		}
		
		
		String path = String.format("%s/experiment.json", OUTPUT_DIR);
		Util.write(path, gson.toJson(JsonBuilder.toJson(this)));
	}

	public List<Problem<jISolution>> configureProblemList() {
		List<Problem<jISolution>> problems = new ArrayList<Problem<jISolution>>();
		//problems.add(new jProblem(App.example()));
		problems.add(new jProblem(ThiefFactory.create(20, 1)));
		problems.add(new jProblem(ThiefFactory.create(20, 5)));
		
		
		return problems;
	}

	public List<Algorithm<List<jISolution>>> configureAlgorithmList(Problem<jISolution> p) {
		List<Algorithm<List<jISolution>>> algorithms = new ArrayList<Algorithm<List<jISolution>>>();
		algorithms.add(new com.moo.ttp.algorithms.NSGAII(p, MAX_EVALUATIONS, 100));
		algorithms.add(new com.moo.ttp.algorithms.NSGAIII(p, MAX_EVALUATIONS, 100, 12));
		algorithms.add(new com.moo.ttp.algorithms.RandomSearch(p, MAX_EVALUATIONS));
		return algorithms;
	}
}
