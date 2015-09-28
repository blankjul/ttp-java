package com.msu.scenarios.thief.bonyadi;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.Item;
import com.msu.scenarios.tsp.RandomTSPScenario;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.util.rounding.RoundingCeil;

public class BenchmarkTSPLIB {

	public SingleObjectiveThiefProblem create(String pathToFile) {

		SingleObjectiveThiefProblem ttp = new SingleObjectiveThiefProblem();
		SymmetricMap map = null;
		ItemCollection<Item> items = new ItemCollection<>();
		
		
		// This will reference one line at a time
		String line = null;

		try {
			FileReader fileReader = new FileReader(pathToFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("DIMENSION")) {
					map = new SymmetricMap(Integer.valueOf(line.split("\t")[1]));
				} else if (line.startsWith("RENTING RATIO")) {
					ttp.setR(Double.valueOf(line.split("\t")[1]));
				} else if (line.startsWith("MIN SPEED")) {
					ttp.setMinSpeed(Double.valueOf(line.split("\t")[1]));
				} else if (line.startsWith("MAX SPEED")) {
					ttp.setMaxSpeed(Double.valueOf(line.split("\t")[1]));
				} else if (line.startsWith("RENTING RATIO")) {
					ttp.setR(Double.valueOf(line.split("\t")[1]));
				} else if (line.startsWith("CAPACITY OF KNAPSACK")) {
					ttp.setMaxWeight(Integer.valueOf(line.split("\t")[1]));
				} else if (line.startsWith("NODE_COORD_SECTION")) {
					List<Point> cities = new ArrayList<>();
					for (int i = 0; i < map.getSize(); i++) {
						line = bufferedReader.readLine();
						String[] values = line.split("\t");
						cities.add(new Point(Double.valueOf(values[1]).intValue(), Double.valueOf(values[2]).intValue()));
					}
					map = RandomTSPScenario.create(cities);
					map.round(new RoundingCeil());
				} else if (line.startsWith("ITEMS SECTION")) {
					while ((line = bufferedReader.readLine()) != null) {
						String[] values = line.split("\t");
						Item item = new Item(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
						items.add(Integer.valueOf(values[3]) - 1, item);
					}
				} 
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + pathToFile + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + pathToFile + "'");
			// ex.printStackTrace();
		}
		
		ttp.setMap(map);
		ttp.setName(pathToFile);
		ttp.setItems(items);
		return ttp;
	}

}
