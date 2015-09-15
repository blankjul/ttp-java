package com.msu.thief.algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Util;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.tsp.TravellingSalesmanProblem;

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
public class LinKernighanHeuristic extends AbstractAlgorithm<Tour<?>, TravellingSalesmanProblem> {

	// ! path to the LKH executable
	protected String pathToLKH = "vendor/LKH-2.0.7/LKH";

	protected boolean hasFinished = false;
	
	protected List<Integer> result= null;

	public LinKernighanHeuristic() {
		if (!Util.doesFileExist(pathToLKH))
			throw new RuntimeException("LinKernighanHeuristic Implementation not found!");

	}


	@Override
	protected void next() {
		this.hasFinished = true;

		try {
			writeProblemFile();
			writeParameterFile();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		BashExecutor.execute(pathToLKH + " instance.par");

		FileReader fileReader;
		try {
			fileReader = new FileReader("tour.opt");
			BufferedReader br = new BufferedReader(fileReader);
			
			String line = br.readLine();
			while (!line.startsWith("TOUR_SECTION")) line = br.readLine();
			line = br.readLine();
			
			result = new ArrayList<>();
			while (!line.equals("-1")) {
				result.add(Integer.valueOf(line) - 1);
				line = br.readLine();
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasFinished() {
		return hasFinished;
	}

	@Override
	protected NonDominatedSolutionSet getResult() {
		Tour<?> tour = new StandardTour(result);
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		result.add(problem.evaluate(tour));
		return result;
	}
	

	private void writeProblemFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer;
		writer = new PrintWriter("problem.tsp", "UTF-8");
		writer.println("TYPE : TSP");
		writer.println("DIMENSION : " + problem.numOfCities());
		writer.println("EDGE_WEIGHT_TYPE : EXPLICIT");
		writer.println("EDGE_WEIGHT_FORMAT : FULL_MATRIX");
		writer.println("EDGE_WEIGHT_SECTION");
		SymmetricMap m = problem.getMap();
		for (int i = 0; i < problem.numOfCities(); i++) {
			for (int j = 0; j < problem.numOfCities(); j++) {
				writer.print((int) m.get(i, j));
				writer.print(" ");
			}
			writer.print("\n");
		}
		writer.println("EOF");
		writer.close();
		
	}

	private void writeParameterFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer;
		writer = new PrintWriter("instance.par", "UTF-8");
		writer.println("PROBLEM_FILE = problem.tsp");
		writer.println("RUNS = 1");
		writer.println("TRACE_LEVEL = 0");
		writer.println("TOUR_FILE = tour.opt");
		writer.close();
	}

}
