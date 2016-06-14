package com.msu.thief.io.thief.reader;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;

public class ThiefReaderUtil {

	static final Logger logger = Logger.getLogger(ThiefReaderUtil.class);

	public static int parseNumOfCities(String line) {
		int numOfCities = Integer.valueOf(line);
		logger.info(String.format("Parsed number of cities: %s", numOfCities));
		return numOfCities;
	}

	public static int parseNumOfItems(String line) {
		int numOfItems = Integer.valueOf(line);
		logger.info(String.format("Parsed number of items: %s", numOfItems));
		return numOfItems;
	}

	public static int parseMaximalWeight(String line) {
		int maxWeight = Integer.valueOf(line);
		logger.info(String.format("Parsed maximal weight of knapsack: %s", maxWeight));
		return maxWeight;
	}

	public static double parseMaxVelocity(String line) {
		double vmax = Double.valueOf(line);
		logger.info(String.format("Parsed maximal velocity: %s", vmax));
		return vmax;
	}

	public static double parseMinVelocity(String line) {
		double vmin = Double.valueOf(line);
		logger.info(String.format("Parsed minimal velocity: %s", vmin));
		return vmin;
	}

	public static double parseDroppingConstant(String line) {
		double droppingConstant = Double.valueOf(line);
		logger.info(String.format("Parsed dropping constant: %s", droppingConstant));
		return droppingConstant;
	}

	public static double parseSingleObjectiveWeight(String line)  {
		double R = Double.valueOf(line);
		logger.info(String.format("Parsed SingleObjectiveWeight: %s", R));
		return R;
	}
	
	public static double parseDroppingRate(String line)  {
		double droppingRate = Double.valueOf(line);
		logger.info(String.format("Parsed dropping rate: %s", droppingRate));
		return droppingRate;
	}

	public static SymmetricMap parseMap(BufferedReader br, int numOfCities) throws NumberFormatException, IOException {
		SymmetricMap map = new SymmetricMap(numOfCities);
		logger.info("Started to parse distance matrix.");
		for (int i = 0; i < numOfCities; i++) {
			String[] distance = br.readLine().split(" ");
			for (int j = 0; j < distance.length; j++) {
				map.set(i, j, Double.valueOf(distance[j]));
			}
		}
		logger.info("Finished parsing map.");
		return map;
	}

	public static ItemCollection<Item> parseItems(BufferedReader br, int numOfItems, int numOfCities) throws NumberFormatException, IOException {
		ItemCollection<Item> items = new ItemCollection<>();

		// parse all the items
		logger.info("Starting to parse items.");
		String[] weights = br.readLine().split(" ");
		String[] values = br.readLine().split(" ");
		Boolean[][] mItems = new Boolean[numOfItems][numOfCities];
		for (int i = 0; i < numOfItems; i++) {
			String[] b = br.readLine().split(" ");
			for (int j = 0; j < b.length; j++) {
				if (b[j].equals("1"))
					mItems[i][j] = true;
				else
					mItems[i][j] = false;
			}
		}

		for (int j = 0; j < numOfCities; j++) {
			for (int i = 0; i < numOfItems; i++) {
				if (mItems[i][j]) {
					Item item = new Item(Double.valueOf(values[i]), Double.valueOf(weights[i]));
					items.add(j, item);
				}
			}
		}
		logger.info("Finished to parse items.");
		return items;

	}

}
