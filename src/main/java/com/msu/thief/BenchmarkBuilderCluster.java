package com.msu.thief;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.io.writer.JsonThiefProblemWriter;
import com.msu.thief.io.writer.TSPLIBThiefProblemWriter;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class BenchmarkBuilderCluster {

	static final private int SEED = 47845364;

	public static void main(String[] args) {

		BasicConfigurator.configure();

		MyRandom r = new MyRandom(SEED);

		// for (int numOfCities : Arrays.asList(5,10,20,50,100)) {
		for (int numOfCities : Arrays.asList(100)) {

			// used seed 123789456 for 3 clusters
			for (int numOfClusters : Arrays.asList(10)) {

				r = new MyRandom(SEED + numOfCities);
				CoordinateMap m = buildMapCluster(numOfClusters, 50, numOfCities, 0, 1000, r);

				// for (int itemsPerCity : Arrays.asList(1,5,10)) {
				for (int itemsPerCity : Arrays.asList(1)) {

					r = new MyRandom(SEED + 10000 + numOfCities);
					ItemCollection<Item> c = buildItems(numOfCities, itemsPerCity, 1000, r);

					// for (double fillingRate : Arrays.asList(0.2,0.5,0.8)) {
					for (double fillingRate : Arrays.asList(0.8)) {

						int maxWeight = calcMaxWeight(fillingRate, c, numOfCities);
						double R = calcR(m, c, maxWeight);

						AbstractThiefProblem thief = new SingleObjectiveThiefProblem(m, c, maxWeight, R);

						String packSize = "";
						if (fillingRate == 0.2) {
							packSize = "s";
						} else if (fillingRate == 0.5) {
							packSize = "m";
						} else if (fillingRate == 0.8) {
							packSize = "l";
						} else {
							throw new RuntimeException("Unknown max knapsack rate: only s, m, l are allowed.");
						}

						String folder = "../thief-benchmark";
						String file = String.format("%s", numOfClusters);
						new JsonThiefProblemWriter().write(thief, String.format("%s/cluster-%s.json", folder, file));
						new TSPLIBThiefProblemWriter().write((SingleObjectiveThiefProblem) thief, String.format("%s/tsplib-cluster-%s.ttp", folder, file));

					}
				}
			}
		}

		System.out.println("DONE");

	}

	public static double calcR(CoordinateMap m, ItemCollection<Item> c, int maxWeight) {

		MultiObjectiveThiefProblem thief = new MultiObjectiveThiefProblem(m, c, maxWeight);
		Tour pi = AlgorithmUtil.calcBestTour(thief);
		Pack z = AlgorithmUtil.calcBestPackingPlan(c.asList(), maxWeight);

		Solution<TTPVariable> bestPi = thief.evaluate(new TTPVariable(pi, Pack.empty()));
		Solution<TTPVariable> bestPiBestZ = thief.evaluate(new TTPVariable(pi, z));

		// delta y / delta x = delta profit / delta time
		double slope = (-bestPiBestZ.getObjective(1)) / (bestPiBestZ.getObjective(0) - bestPi.getObjective(0));

		return (double) Math.round(slope * 1000d) / 1000d;

	}

	public static int calcMaxWeight(double fillingRate, ItemCollection<Item> c, int numOfCities) {
		int weight = 0;

		for (int i = 0; i < numOfCities; i++) {
			for (Item item : c.getItemsFromCity(i)) {
				weight += item.getWeight();
			}

		}
		return (int) (weight * fillingRate);
	}

	public static ItemCollection<Item> buildItems(int numOfCities, int itemsPerCity, int upperBound, MyRandom r) {

		ItemCollection<Item> c = new ItemCollection<Item>();
		for (int j = 0; j < itemsPerCity; j++) {
			for (int i = 0; i < numOfCities; i++) {
				Item item = new Item(r.nextInt(upperBound), r.nextInt(upperBound));
				c.add(i, item);
			}
		}

		return c;
	}

	public static CoordinateMap buildMap(int numOfCities, int lowerBound, int upperBound, MyRandom r) {

		List<Point2D> cities = new ArrayList<Point2D>();
		for (int i = 0; i < numOfCities; i++) {
			Point2D p = new Point(r.nextInt(lowerBound, upperBound), r.nextInt(lowerBound, upperBound));
			cities.add(p);
		}

		CoordinateMap m = new CoordinateMap(cities);
		return m;
	}

	public static CoordinateMap buildMapCluster(int numOfClusters, int variance, int numOfCities, int lowerBound, int upperBound, MyRandom r) {

		List<Point2D> clusters = new ArrayList<Point2D>();
		clusters.add(new Point(891, 411));
		clusters.add(new Point(398, 994));
		clusters.add(new Point(229, 299));
		clusters.add(new Point(814, 794));
		clusters.add(new Point(901, 188));
		clusters.add(new Point(406, 247));
		clusters.add(new Point(182, 950));
		clusters.add(new Point(50, 600));
		clusters.add(new Point(700, 100));
		clusters.add(new Point(600, 600));

		List<Point2D> cities = new ArrayList<Point2D>();
		cities.add(new Point(500,500));

		for (int i = 0; i < numOfCities - 1; i++) {
			Point2D cluster = r.select(clusters);
			Point2D p = new Point(r.nextInt((int) cluster.getX() - variance, (int) cluster.getX() + variance),
					r.nextInt((int) cluster.getY() - variance, (int) cluster.getY() + variance));
			cities.add(p);
		}

		CoordinateMap m = new CoordinateMap(cities);
		return m;
	}

}
