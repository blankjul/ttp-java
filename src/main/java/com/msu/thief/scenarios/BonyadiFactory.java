package com.msu.thief.scenarios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;

public class BonyadiFactory {

	static final Logger logger = Logger.getLogger(BonyadiFactory.class);

	public TravellingThiefProblem create(String pathToFile) {

		TravellingThiefProblem ttp = new TravellingThiefProblem();
		SymmetricMap map = null;
		ItemCollection<Item> items = new ItemCollection<>();

		logger.info(String.format("Starting to parse file %s", pathToFile));

		try {

			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(pathToFile);

			// Always wrap FileReader in BufferedReader.
			BufferedReader br = new BufferedReader(fileReader);

			int numOfCities = Integer.valueOf(br.readLine());
			logger.info(String.format("Parsed number of cities: %s", numOfCities));
			map = new SymmetricMap(numOfCities);

			int numOfItems = Integer.valueOf(br.readLine());
			logger.info(String.format("Parsed number of items: %s", numOfItems));

			
			// set the maximal weight
			int maxWeight = Integer.valueOf(br.readLine());
			logger.info(String.format("Parsed maximal weight of knapsack: %s", maxWeight));
			ttp.setMaxWeight(maxWeight);

			// set speed values
			double vmax = Double.valueOf(br.readLine());
			logger.info(String.format("Parsed maximal velocity: %s", vmax));
			ttp.setMaxSpeed(vmax);
			double vmin = Double.valueOf(br.readLine());
			logger.info(String.format("Parsed minimal velocity: %s", vmin));
			ttp.setMinSpeed(vmin);
			
			
			// set the dropping for the items
			double droppingConstant = Double.valueOf(br.readLine());
			logger.info(String.format("Parsed dropping constant: %s", droppingConstant));
			double droppingRate = Double.valueOf(br.readLine());
			logger.info(String.format("Parsed dropping rate: %s", droppingRate));
			ttp.setProfitEvaluator(new ExponentialProfitEvaluator(droppingRate, droppingConstant));
			
			
			// parse the distance matrix
			logger.info("Started to parse distance matrix.");
			for (int i = 0; i < numOfCities; i++) {
				String[] distance = br.readLine().split(" ");
				for (int j = 0; j < distance.length; j++) {
					map.set(i, j, Double.valueOf(distance[j]));
				}
			}
			logger.info("Finished parsing map.");

			
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
						//System.out.println(String.format("%s,%s", j, item));
						items.add(j, item);
					}
				}
			}
			logger.info("Finished to parse items.");

			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + pathToFile + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + pathToFile + "'");
			// ex.printStackTrace();
		}

		ttp.setMap(map);
		ttp.setItems(items);
		
		return ttp;
	}

}
