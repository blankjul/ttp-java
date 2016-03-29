package com.msu.thief.util;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.FileCollectorParser;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.io.writer.JsonThiefProblemWriter;
import com.msu.thief.io.writer.TSPLIBThiefProblemWriter;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.ProfitConstraintThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class BenchmarkBuilder {

	static final private int SEED = 123456;

	static final String folder = "../new";

	public static void main(String[] args) {

		BasicConfigurator.configure();
		MyRandom r = new MyRandom(SEED);

		List<SingleObjectiveThiefProblem> problems = new ArrayList<>();

		for (int numOfCities : Arrays.asList(5, 10, 20, 50, 100)) {

			r = new MyRandom(SEED + numOfCities);
			CoordinateMap m = buildMap(numOfCities, 0, 1000, r);

			for (int itemsPerCity : Arrays.asList(1, 5, 10)) {

				r = new MyRandom(SEED + 10000 + numOfCities);
				ItemCollection<Item> c = buildItems(numOfCities, itemsPerCity, 1000, r);

				for (double fillingRate : Arrays.asList(0.2, 0.5, 0.8)) {

					int maxWeight = calcMaxWeight(fillingRate, c, numOfCities);
					double R = calcR(m, c, maxWeight);
					String name = String.format("%04d-%02d-%s", numOfCities, itemsPerCity, getSizeString(fillingRate));

					SingleObjectiveThiefProblem single = new SingleObjectiveThiefProblem(m, c, maxWeight, R);
					single.setName("single-" + name);

					problems.add(single);

				}
			}
		}
		
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("resources/", "single-cluster*", new JsonThiefProblemReader());
		for(AbstractThiefProblem p : fcp.collect()) problems.add((SingleObjectiveThiefProblem) p);
		

		for (SingleObjectiveThiefProblem single : problems) {

			final String name = single.getName().replace("single-", "");
			
			new JsonThiefProblemWriter().write(single, String.format("%s/%s.json", folder, single.getName()));
			new TSPLIBThiefProblemWriter().write(single, String.format("%s/tsplib-%s.ttp", folder, single.getName()));

			MultiObjectiveThiefProblem multi = calcMultiObjectiveThiefProblem(single, name);
			new JsonThiefProblemWriter().write(multi, String.format("%s/%s.json", folder, multi.getName()));

			if (name.endsWith("l")) {
				List<ProfitConstraintThiefProblem> profit = calcProfitConstraintThiefProblems(single, name);
				for (ProfitConstraintThiefProblem p : profit) {
					new JsonThiefProblemWriter().write(p, String.format("%s/%s.json", folder, p.getName()));
				}
			}
		}

		System.out.println("DONE");

	}

	public static MultiObjectiveThiefProblem calcMultiObjectiveThiefProblem(AbstractThiefProblem p, String name) {

		final double deprRate = 0.95;

		Tour pi = AlgorithmUtil.calcBestTour(p);
		Solution<TTPVariable> bestPi = p.evaluate(new TTPVariable(pi, Pack.empty()));

		// multi-objective with depreciation

		// worth 10% of value when on the optimal tour
		double C = bestPi.getObjective(0) * Math.log(deprRate) / Math.log(0.3);
		C = (int) C;

		MultiObjectiveThiefProblem thief = new MultiObjectiveThiefProblem(p);
		thief.setProfitEvaluator(new ExponentialProfitEvaluator(deprRate, C));
		thief.setName("multi-" + name);

		return thief;

	}

	public static List<ProfitConstraintThiefProblem> calcProfitConstraintThiefProblems(AbstractThiefProblem p, String name) {

		final int instances = 5;

		List<ProfitConstraintThiefProblem> problems = new ArrayList<>();

		double interval = 0;
		interval = p.getMaxWeight() / instances + 1;

		for (int i = 0; i < instances; i++) {

			Pack forWeight = AlgorithmUtil.calcBestPackingPlan(p.getItems(), (int) (interval * (i + 1)));
			double profit = 0;
			for (int idx : forWeight.decode()) {
				profit += p.getItem(idx).getProfit();
			}

			ProfitConstraintThiefProblem timeConstraint = new ProfitConstraintThiefProblem(p, profit);
			timeConstraint.setName(String.format("profit-%s-%s", name, i + 1));
			problems.add(timeConstraint);

		}

		return problems;

	}

	public static String getSizeString(double fillingRate) {
		if (fillingRate == 0.2) {
			return "s";
		} else if (fillingRate == 0.5) {
			return "m";
		} else if (fillingRate == 0.8) {
			return "l";
		} else {
			throw new RuntimeException("Unknown max knapsack rate: only s, m, l are allowed.");
		}
	}

	public static double calcR(CoordinateMap m, ItemCollection<Item> c, int maxWeight) {

		MultiObjectiveThiefProblem thief = new MultiObjectiveThiefProblem(m, c, maxWeight);
		Tour pi = AlgorithmUtil.calcBestTour(thief);
		Pack z = AlgorithmUtil.calcBestPackingPlan(thief.getItems(), maxWeight);

		Solution<TTPVariable> bestPi = thief.evaluate(new TTPVariable(pi, Pack.empty()));
		Solution<TTPVariable> bestPiBestZ = thief.evaluate(new TTPVariable(pi, z));

		// delta y / delta x = delta profit / delta time
		// double slope = (-bestPiBestZ.getObjective(1)) /
		// (bestPiBestZ.getObjective(0));
		double slope = (-bestPiBestZ.getObjective(1)) / (bestPiBestZ.getObjective(0) - bestPi.getObjective(0));
		slope = (double) Math.round(slope * 1000d) / 1000d;

		return slope;

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

}
