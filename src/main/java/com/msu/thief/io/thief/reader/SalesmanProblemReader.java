package com.msu.thief.io.thief.reader;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.io.AProblemReader;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.util.distances.ADistanceCalculator;
import com.msu.thief.util.distances.EuclideanDistance;
import com.msu.thief.util.rounding.RoundingNearestInt;

public class SalesmanProblemReader extends AProblemReader<SalesmanProblem> {

	static final Logger logger = Logger.getLogger(SalesmanProblemReader.class);

	@Override
	public SalesmanProblem read_(BufferedReader br) throws IOException {

		CoordinateMap map = null;

		String name = br.readLine();
		logger.info(String.format("Reading problem %s", name));
		logger.info(br.readLine()); // TYPE
		logger.info(br.readLine()); // COMMENT

		int numOfCities = Integer.valueOf(br.readLine().split(" ")[1]);
		logger.info(String.format("Num of cities: %s", numOfCities));

		ADistanceCalculator mDistance = null;
		String distance = br.readLine().split(" ")[1];
		if (distance.equals("EUC_2D")) {
			mDistance = new EuclideanDistance();
		} else {
			throw new RuntimeException(String.format("Distance function not implemented: %s", distance));
		}

		List<Point2D> cities = new ArrayList<>();
		String citiesSection = br.readLine();
		logger.info(String.format("Reading Section: %s", citiesSection));
		if (citiesSection.equals("NODE_COORD_SECTION")) {
			String line = null;
			while ((line = br.readLine()) != null) {

				if (line.equals("EOF")) break;
				
				String[] values = line.split("\\s+");
				Point2D point = new Point2D.Double(Double.valueOf(values[1]), Double.valueOf(values[2]));
				cities.add(point);
				logger.trace(String.format("Insert point %s", point));

			}
		} else {
			throw new RuntimeException(String.format("City section not implemented: %s", citiesSection));
		}
		
		String line = br.readLine();
		Solution optimum = null;
		if(line != null && line.startsWith("OPTIMUM:")) {
			optimum = new Solution(null, Arrays.asList(Double.valueOf(line.split(" ")[1])));
			logger.info(String.format("Optimal Time: %s", optimum.getObjective()));
		}

		map = new CoordinateMap(cities, mDistance);
		
		// always round to nearest integer
		map.round(new RoundingNearestInt());
		
		SalesmanProblem p = new SalesmanProblem(map);
		p.setOptimum(new NonDominatedSolutionSet(Arrays.asList(optimum)));
		return p;
	}

}
