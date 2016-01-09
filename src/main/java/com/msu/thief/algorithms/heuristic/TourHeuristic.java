package com.msu.thief.algorithms.heuristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class TourHeuristic {

	public static List<Integer> bestTour = Arrays.asList(0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46,
			25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29, 1, 6,
			41, 20, 16, 2, 17, 30, 21);

	public static List<Integer> heuristicTour = Arrays.asList(0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13,
			12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 39, 38, 36, 45, 15, 28, 29, 1, 6, 41, 20, 16, 2, 17,
			30, 22, 19, 49, 43, 33, 34, 35, 21);

	public static IntegerSetPackingList heuristicPack = new IntegerSetPackingList(
			new HashSet<Integer>(Arrays.asList(0, 32, 1, 33, 2, 34, 3, 42, 48, 17, 49, 18, 50, 19, 20)), 51);

	
	
	public static double calcHeuristic(SingleObjectiveThiefProblem thief, Tour<?> tour) {

		List<Double> tourLength = new ArrayList<>();
		double sum = 0;
		for (int i = 0; i < thief.numOfCities(); i++) {
			tourLength.add(sum);
			int city = tour.encode().get(i);
			int next = tour.encode().get((i + 1) % thief.numOfCities());
			sum += thief.getMap().get(city, next);
		}

		for (int i = 0; i < tourLength.size(); i++) {
			tourLength.set(i,sum - tourLength.get(i));
		}

		ItemCollection<Item> c = thief.getItemCollection();

		List<Pair<Integer, Double>> items = new ArrayList<>();
		for (int i = 0; i < thief.numOfItems(); i++) {
			
			int city = c.getCityOfItem(i);
			int idxOfTour = tour.encode().indexOf(city);
			
			double deltaProfit = thief.getItem(i).getProfit();
			
			double deltaSpeed = thief.getMaxSpeed() - ((thief.getItem(i).getWeight() / thief.getMaxWeight()) * (thief.getMaxSpeed() - thief.getMinSpeed()));
			double deltaTime = tourLength.get(idxOfTour) * deltaSpeed;
			
			double heuristic = deltaProfit - thief.getR() * deltaTime;
			items.add(Pair.create(i, heuristic));
		}

		Collections.sort(items, (i1, i2) -> i2.second.compareTo(i1.second));

		double h = 0;
		
		for (Pair<Integer, Double> item : items) {
			h += item.second;
			//System.out.println(String.format("%s %f", item.first, item.second));
		}

		//System.out.println();
		

		return h;

	}

	public static void main(String[] args) {

		BasicConfigurator.configure();

		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

		MyRandom rand = new MyRandom(123456);

		for (int i = 0; i < thief.numOfCities(); i++) {

			int city = heuristicTour.get(i);

			for (Integer idx : thief.getItemCollection().getItemsFromCityByIndex(city)) {
				Item item = thief.getItem(idx);
				if (heuristicPack.isPicked(idx))
					System.out.print("-- > ");
				System.out.println(idx + " " + city + " " + item + " " + item.getProfit() / item.getWeight());
			}

		}

		System.out.println();
		System.out.println(calcHeuristic(thief, new StandardTour(bestTour)));
		System.out.println(calcHeuristic(thief, new StandardTour(heuristicTour)));

	}

}
