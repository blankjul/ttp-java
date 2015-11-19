package com.msu.thief.io.thief.reader;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.msu.thief.io.AProblemReader;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.util.rounding.RoundingCeil;

public class BonyadiTSPLIBReader extends AProblemReader<SingleObjectiveThiefProblem> {

	protected SingleObjectiveThiefProblem read_(BufferedReader bufferedReader) throws IOException {

		SingleObjectiveThiefProblem ttp = new SingleObjectiveThiefProblem();
		SymmetricMap map = null;
		ItemCollection<Item> items = new ItemCollection<>();

		// This will reference one line at a time
		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			if (line.startsWith("DIMENSION")) {
				map = new SymmetricMap(Integer.valueOf(line.split("\t")[1]));
			} else if (line.startsWith("RENTING RATIO")) {
				ttp.setR(Double.valueOf(line.split("\t")[1]));
			} else if (line.startsWith("MIN SPEED")) {
				ttp.setMinSpeed(Double.valueOf(line.split("\t")[1]));
			} else if (line.startsWith("MAX SPEED")) {
				ttp.setMaxSpeed(Double.valueOf(line.split("\t")[1]));
			} else if (line.startsWith("CAPACITY OF KNAPSACK")) {
				ttp.setMaxWeight(Integer.valueOf(line.split("\t")[1]));
			} else if (line.startsWith("NODE_COORD_SECTION")) {
				List<Point2D> cities = new ArrayList<>();
				for (int i = 0; i < map.getSize(); i++) {
					line = bufferedReader.readLine();
					String[] values = line.split("\t");
					cities.add(new Point2D.Double(Double.valueOf(values[1]).intValue(), Double.valueOf(values[2]).intValue()));
				}
				map = new CoordinateMap(cities);
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

		ttp.setMap(map);
		ttp.setItems(items);
		ttp.setStartingCityIsZero(true);
		
		return ttp;
	}

}
