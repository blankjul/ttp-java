package com.msu.algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.msu.ThiefConfiguration;
import com.msu.moo.algorithms.AMultiObjectiveAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Util;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

/**
 * http://www.akira.ruc.dk/~keld/research/LKH/
 * 
 * LKH is an effective implementation of the Lin-Kernighan heuristic for solving
 * the traveling salesman problem.
 * 
 * Computational experiments have shown that LKH is highly effective. Even
 * though the algorithm is approximate, optimal solutions are produced with an
 * impressively high frequency. LKH has produced optimal solutions for all
 * solved problems we have been able to obtain; including a 85,900-city instance
 * (at the time of writing, the largest nontrivial instance solved to
 * optimality). Furthermore, the algorithm has improved the best known solutions
 * for a series of large-scale instances with unknown optima, among these a
 * 1,904,711-city instance (World TSP).
 * 
 */
public class LinKernighanHeuristic extends AMultiObjectiveAlgorithm<ICityProblem> {

	protected List<Integer> result= null;

	public LinKernighanHeuristic() {
		if (!Util.doesFileExist(ThiefConfiguration.PATH_TO_LKH))
			throw new RuntimeException("LinKernighanHeuristic Implementation not found!");

	}

	
	public static Tour<?> getTour(Evaluator<ICityProblem> eval) {
		return getTour(eval, null);
	}
	
	
	public static Tour<?> getTour(Evaluator<ICityProblem> eval, Double speed) {
		
		List<Integer> result = new ArrayList<>();
		
		SymmetricMap map = eval.getProblem().getMap();
		if (speed != null) map = map.multipleCosts(speed);
		
		try {
			writeProblemFile(map);
			writeParameterFile();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		BashExecutor.execute(ThiefConfiguration.PATH_TO_LKH + " instance.par");

		FileReader fileReader;
		try {
			fileReader = new FileReader("tour.opt");
			BufferedReader br = new BufferedReader(fileReader);
			
			String line = br.readLine();
			while (!line.startsWith("TOUR_SECTION")) line = br.readLine();
			line = br.readLine();
			
			
			while (!line.equals("-1")) {
				result.add(Integer.valueOf(line) - 1);
				line = br.readLine();
			}
			br.close();
			
			BashExecutor.execute("rm instance.par");
			BashExecutor.execute("rm problem.tsp");
			BashExecutor.execute("rm tour.opt");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Tour<?> tour = new StandardTour(result);
		return tour;
		
	}
	
	@Override
	public NonDominatedSolutionSet run(com.msu.moo.model.Evaluator<ICityProblem> eval) {
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		result.add(eval.evaluate(LinKernighanHeuristic.getTour(eval)));
		return result;
	}


	private static void writeProblemFile(SymmetricMap map) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer;
		writer = new PrintWriter("problem.tsp", "UTF-8");
		writer.println("TYPE : TSP");
		writer.println("DIMENSION : " + map.getSize());
		writer.println("EDGE_WEIGHT_TYPE : EXPLICIT");
		writer.println("EDGE_WEIGHT_FORMAT : FULL_MATRIX");
		writer.println("EDGE_WEIGHT_SECTION");
		for (int i = 0; i < map.getSize(); i++) {
			for (int j = 0; j < map.getSize(); j++) {
				writer.print((int) map.get(i, j));
				writer.print(" ");
			}
			writer.print("\n");
		}
		writer.println("EOF");
		writer.close();
		
	}

	private static void writeParameterFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer;
		writer = new PrintWriter("instance.par", "UTF-8");
		writer.println("PROBLEM_FILE = problem.tsp");
		writer.println("RUNS = 1");
		writer.println("TRACE_LEVEL = 0");
		writer.println("TOUR_FILE = tour.opt");
		writer.close();
	}



}
