package com.moo.ttp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;

import com.google.gson.Gson;
import com.moo.operators.crossover.CycleCrossover;
import com.moo.operators.crossover.OrderedCrossover;
import com.moo.operators.crossover.PMXCrossover;
import com.moo.operators.crossover.SinglePointCrossover;
import com.moo.operators.mutation.BitFlipMutation;
import com.moo.operators.mutation.SwapMutation;
import com.moo.ttp.experiment.Experiment;
import com.moo.ttp.factory.ItemFactory;
import com.moo.ttp.factory.ThiefFactory;
import com.moo.ttp.jmetal.jCrossover;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jMutation;
import com.moo.ttp.jmetal.jProblem;
import com.moo.ttp.json.JsonBuilder;
import com.moo.ttp.model.packing.BooleanPackingList;
import com.moo.ttp.model.tour.StandardTour;
import com.moo.ttp.util.Util;

public class Study {

	public static void main(String[] args) {
		if (args.length == 1) Study.MAX_EVALUATIONS = Integer.valueOf(args[0]);
		Study s = new Study();
		s.run();
	}

	public static String OUTPUT_DIR = "./experiment";
	public static int MAX_EVALUATIONS = 250000;
	public final int NUM_OF_RUNS = 100;
	public Gson gson = new Gson();
	public List<jProblem> problems;
	public List<Algorithm<List<jISolution>>> algorithms;
	
	private Experiment experiment = null;
	

	public Study() {
		problems = configureProblemList();
		experiment = new Experiment();
	}

	public void run() {
		
		
		new File("./experiment").mkdirs();

		for (int i = 0; i < problems.size(); i++) {

			jProblem p = problems.get(i);
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

	public List<jProblem> configureProblemList() {
		List<jProblem> problems = new ArrayList<jProblem>();
		problems.add(new jProblem(ThiefFactory.create(20, 1, ItemFactory.TYPE.WEAKLY_CORRELATED, 0.6)));
		//problems.add(new jProblem(ThiefFactory.create(20, 10, ItemFactory.TYPE.WEAKLY_CORRELATED, 0.6)));
		return problems;
	}

	public List<Algorithm<List<jISolution>>> configureAlgorithmList(jProblem p) {
		List<Algorithm<List<jISolution>>> algorithms = new ArrayList<Algorithm<List<jISolution>>>();
		algorithms.add(new com.moo.algorithms.NSGAII(p, MAX_EVALUATIONS, 100, 
				new StandardTour(null), new BooleanPackingList(null), 
				new jCrossover(new PMXCrossover<Integer>(),  new SinglePointCrossover<Boolean>()),
				new jMutation(new SwapMutation<>(), new BitFlipMutation()), "NSGAII-ST[PMX-SWAP]-BP[SPC-BF]"));
		
		algorithms.add(new com.moo.algorithms.NSGAII(p, MAX_EVALUATIONS, 100, 
				new StandardTour(null), new BooleanPackingList(null), 
				new jCrossover(new CycleCrossover<Integer>(),  new SinglePointCrossover<Boolean>()),
				new jMutation(new SwapMutation<>(), new BitFlipMutation()), "NSGAII-ST[CX-SWAP]-BP[SPC-BFA]"));
		
		algorithms.add(new com.moo.algorithms.NSGAII(p, MAX_EVALUATIONS, 100, 
				new StandardTour(null), new BooleanPackingList(null), 
				new jCrossover(new OrderedCrossover<Integer>(),  new SinglePointCrossover<Boolean>()),
				new jMutation(new SwapMutation<>(), new BitFlipMutation()), "NSGAII-ST[OX-SWAP]-BP[SPC-BFA]"));
		
		return algorithms;
	}
}
