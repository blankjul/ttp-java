package com.msu.thief.factory;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.Map;
import com.msu.thief.problems.TravellingThiefProblem;

public class BonyadiFactory {

	public TravellingThiefProblem create(String pathToFile) {

		TravellingThiefProblem ttp = new TravellingThiefProblem();
		Map map = null;
		ItemCollection<Item> items = new ItemCollection<>();
		
		
		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(pathToFile);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				
				if (line.startsWith("DIMENSION")) {
					map = new Map(Integer.valueOf(line.split("\t")[1]));
				} else if (line.startsWith("CAPACITY OF KNAPSACK")) {
					ttp.setMaxWeight(Integer.valueOf(line.split("\t")[1]));
				} else if (line.startsWith("NODE_COORD_SECTION")) {
					List<Point> cities = new ArrayList<>();
					for (int i = 0; i < map.getSize(); i++) {
						line = bufferedReader.readLine();
						String[] values = line.split("\t");
						cities.add(new Point(Double.valueOf(values[1]).intValue(), Double.valueOf(values[2]).intValue()));
					}
					map = new MapFactory().create(cities);
				} else if (line.startsWith("ITEMS SECTION")) {
					while ((line = bufferedReader.readLine()) != null) {
						String[] values = line.split("\t");
						Item item = new Item(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
						items.add(Integer.valueOf(values[3]) - 1, item);
					}
				} 
			}

			// Always close files.
			bufferedReader.close();
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
