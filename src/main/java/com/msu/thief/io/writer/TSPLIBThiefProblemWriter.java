package com.msu.thief.io.writer;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.msu.moo.util.io.AWriter;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class TSPLIBThiefProblemWriter extends AWriter<SingleObjectiveThiefProblem> {


	@Override
	protected void write_(SingleObjectiveThiefProblem p, OutputStream os) throws IOException {
		
		final PrintStream s = new PrintStream(os);
		s.println(String.format("PROBLEM NAME: 	%s", p.getName()));
		s.println("KNAPSACK DATA TYPE: uncorrelated");
		s.println(String.format("DIMENSION:	%s", p.numOfCities()));
		s.println(String.format("NUMBER OF ITEMS: 	%s", p.numOfItems()));
		s.println(String.format("CAPACITY OF KNAPSACK: 	%s", p.getMaxWeight()));
		s.println(String.format("MIN SPEED: 	%s", p.getMinSpeed()));
		s.println(String.format("MAX SPEED: 	%s", p.getMaxSpeed()));
		s.println(String.format("RENTING RATIO: 	%s", p.getR()));
		s.println(String.format("EDGE_WEIGHT_TYPE:	EUCL_2D"));
		s.println(String.format("NODE_COORD_SECTION	(INDEX, X, Y):"));
		
		CoordinateMap m = (CoordinateMap) p.getMap();
		
		for (int i = 0; i < p.numOfCities(); i++) {
			Point2D point = m.getCities().get(i);
			s.println(String.format("%s	%s	%s", i + 1, point.getX(), point.getY()));
		}
		
		
		s.println(String.format("ITEMS SECTION	(INDEX, PROFIT, WEIGHT, ASSIGNED NODE NUMBER): "));
		ItemCollection<Item> c = p.getItemCollection();
		
		int counter = 1;
		for (int i = 0; i < p.numOfCities(); i++) {
			for (Item item : c.getItemsFromCity(i)) {
				s.println(String.format("%s	%s	%s	%s", counter++, item.getProfit(), item.getWeight(), i + 1));
				
			}
				
		}
		

		s.close();
		os.close();
	}
	

}
