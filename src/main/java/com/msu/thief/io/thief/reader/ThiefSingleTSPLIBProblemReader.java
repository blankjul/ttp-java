package com.msu.thief.io.thief.reader;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.msu.thief.io.AProblemReader;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.util.rounding.IRounding;
import com.msu.thief.util.rounding.RoundingCeil;

public class ThiefSingleTSPLIBProblemReader extends AProblemReader<SingleObjectiveThiefProblem> {

	static final Logger logger = Logger.getLogger(ThiefSingleTSPLIBProblemReader.class);

	@Override
	public SingleObjectiveThiefProblem read_(BufferedReader br) throws IOException {

		CoordinateMap map = null;

		String name = br.readLine();
		logger.info(String.format("Reading problem %s", name));
		logger.info(br.readLine()); // TYPE

		int numOfCities = Integer.valueOf(br.readLine().split(":")[1].trim());
		logger.info(String.format("Num of cities: %s", numOfCities));

		int numOfItems = Integer.valueOf(br.readLine().split(":")[1].trim());
		logger.info(String.format("Num of items: %s", numOfItems));
		
		int maxWeight = Integer.valueOf(br.readLine().split(":")[1].trim());
		logger.info(String.format("Maximal weight: %s", maxWeight));
		
		double minSpeed = Double.valueOf(br.readLine().split(":")[1].trim());
		logger.info(String.format("Minimal Speed: %s", minSpeed));
		
		double maxSpeed = Double.valueOf(br.readLine().split(":")[1].trim());
		logger.info(String.format("Maximal Speed: %s", maxSpeed));
		
		double R = Double.valueOf(br.readLine().split(":")[1].trim());
		logger.info(String.format("Renting Ratio: %s", R));
		
		
		IRounding mRound = null;
		String round = br.readLine().split(":")[1].trim();
		if (round.equals("CEIL_2D")) {
			mRound = new RoundingCeil();
		} else {
			throw new RuntimeException(String.format("Rounding function not implemented: %s", round));
		}

		List<Point2D> cities = new ArrayList<>();
		String citiesSection = br.readLine();
		logger.info(String.format("Reading Section: %s", citiesSection));
		if (citiesSection.startsWith("NODE_COORD_SECTION")) {
			String line = null;
			while ((line = br.readLine()) != null) {

				if (line.startsWith("ITEMS")) break;
				
				String[] values = line.split("\\s+");
				Point2D point = new Point2D.Double(Double.valueOf(values[1]), Double.valueOf(values[2]));
				cities.add(point);
				// logger.info(String.format("Insert point %s", point));

			}
		} else {
			throw new RuntimeException(String.format("City section not implemented: %s", citiesSection));
		}
		map = new CoordinateMap(cities);
		map.round(mRound);
		
		
		ItemCollection<Item> items = new ItemCollection<>();
		
		String line = null;
		while((line = br.readLine()) != null) {
			String[] values = line.split("\\s+");
			Item item = new Item(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
			int city = Integer.valueOf(values[3]) - 1;
			// logger.info(String.format("Insert item %s to city %s", item, city));
			items.add(city, item);
		}
		logger.info(String.format("Finshed parsing file.", citiesSection));
		SingleObjectiveThiefProblem p = new SingleObjectiveThiefProblem(map, items, maxWeight, R);
		return p;
	}

}
