package com.msu.thief.algorithms.subproblems;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.msu.interfaces.IEvaluator;
import com.msu.thief.ThiefConfiguration;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.BashExecutor;
import com.msu.util.Util;

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
public class SalesmanLinKernighanHeuristic  {

	protected List<Integer> result= null;
	
	protected String timestamp;

	public SalesmanLinKernighanHeuristic() {
		if (!Util.doesFileExist(ThiefConfiguration.PATH_TO_LKH))
			throw new RuntimeException("LinKernighanHeuristic Implementation not found!");
		timestamp = UUID.randomUUID().toString();
	}

	
	public Tour getTour(AbstractThiefProblem p, IEvaluator eval) {
		
		List<Integer> result = new ArrayList<>();
		SymmetricMap map = p.getMap();
		
		try {
			writeProblemFile(map);
			writeParameterFile();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		BashExecutor.execute(ThiefConfiguration.PATH_TO_LKH + " " + timestamp + "_instance.par");

		FileReader fileReader;
		try {
			fileReader = new FileReader(timestamp + "_tour.opt");
			BufferedReader br = new BufferedReader(fileReader);
			
			String line = br.readLine();
			while (!line.startsWith("TOUR_SECTION")) line = br.readLine();
			line = br.readLine();
			
			
			while (!line.equals("-1")) {
				result.add(Integer.valueOf(line) - 1);
				line = br.readLine();
			}
			br.close();
			
			BashExecutor.execute(String.format("rm %s_instance.par", timestamp ));
			BashExecutor.execute(String.format("rm %s_problem.tsp", timestamp ));
			BashExecutor.execute(String.format("rm %s_tour.opt", timestamp ));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.rotate(result, -result.indexOf(0));
		Tour tour = new Tour(result);
		return tour;
		
	}
	


	private void writeProblemFile(SymmetricMap map) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer;
		writer = new PrintWriter(timestamp + "_problem.tsp", "UTF-8");
		writer.println("TYPE : TSP");
		writer.println("DIMENSION : " + map.getSize());
		writer.println("EDGE_WEIGHT_TYPE : EXPLICIT");
		writer.println("EDGE_WEIGHT_FORMAT : FULL_MATRIX");
		writer.println("EDGE_WEIGHT_SECTION");
		for (int i = 0; i < map.getSize(); i++) {
			for (int j = 0; j < map.getSize(); j++) {
				// multiply the distance because C implementation can only handle Integer!
				int distance = (int) (map.get(i, j) * 1000);
				writer.print(distance);
				writer.print(" ");
			}
			writer.print("\n");
		}
		writer.println("EOF");
		writer.close();
	}

	private void writeParameterFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer;
		writer = new PrintWriter(timestamp + "_instance.par", "UTF-8");
		writer.println("PROBLEM_FILE = " + timestamp + "_problem.tsp");
		writer.println("RUNS = 1");
		writer.println("TRACE_LEVEL = 0");
		writer.println("TOUR_FILE = " + timestamp + "_tour.opt");
		writer.close();
	}


}
