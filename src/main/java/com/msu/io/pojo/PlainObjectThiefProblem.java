package com.msu.io.pojo;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.knp.model.Item;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;

public class PlainObjectThiefProblem {

	public enum PROBLEM_TYPE { SingleObjective, MultiObjective };
	public enum CITY_TYPE { FULL_MATRIX, XY_COORDINATES };
	public enum DROPPING_TYPE { NO_DROPPING, EXPONTENTIAL };
	
	public String name;
	public PROBLEM_TYPE problemType;
	public int numOfItems;
	public int numOfCities;
	public double minSpeed;
	public double maxSpeed;
	public int maxWeight;
	public boolean startingCityIsZero;
	
	public Double R = null;
	public DROPPING_TYPE droppingType;
	
	public Double droppingConstant = null;
	public Double droppingRate = null;
	
	public CITY_TYPE cityType;
	public List<List<Double>> cities = new ArrayList<>();
	public List<PlainObjectItem> items = new ArrayList<>();
	
	
	
	public PlainObjectThiefProblem() {
		super();
	}



	public PlainObjectThiefProblem(ThiefProblem problem) {
		
		this.name = problem.getName();
		this.problemType = PROBLEM_TYPE.MultiObjective;
		this.numOfItems = problem.numOfItems();
		this.numOfCities = problem.numOfCities();
		this.minSpeed = problem.getMinSpeed();
		this.maxSpeed = problem.getMaxSpeed();
		this.maxWeight = problem.getMaxWeight();
		this.startingCityIsZero = problem.isStartingCityIsZero();
		
		if (problem.getProfitEvaluator() instanceof NoDroppingEvaluator) {
			this.droppingType = DROPPING_TYPE.NO_DROPPING;
		} else if (problem.getProfitEvaluator() instanceof ExponentialProfitEvaluator) {
			this.droppingType = DROPPING_TYPE.EXPONTENTIAL;
			ExponentialProfitEvaluator epe = (ExponentialProfitEvaluator) problem.getProfitEvaluator();
			this.droppingConstant = epe.getDroppingConstant();
			this.droppingRate = epe.getDroppingRate();
		}
		
		if (problem instanceof SingleObjectiveThiefProblem) {
			this.problemType = PROBLEM_TYPE.SingleObjective;
			this.R = ((SingleObjectiveThiefProblem) problem).getR();
		}
		
		

		
		for (int i = 0; i < numOfCities; i++) {
			for (Item item : problem.getItemCollection().getItemsFromCity(i)) {
				this.items.add(new PlainObjectItem(i, item.getWeight(), item.getProfit()));
			}
		}
		
	}
	
	
	
	public ThiefProblem create() {
		
		ThiefProblem result = new ThiefProblem();
		
		if (problemType == PROBLEM_TYPE.MultiObjective)
			result = new ThiefProblem();
		else if (problemType == PROBLEM_TYPE.SingleObjective)
			result = new SingleObjectiveThiefProblem();

		result.setMinSpeed(minSpeed);
		result.setMaxSpeed(maxSpeed);
		result.setMaxWeight(maxWeight);
		result.setStartingCityIsZero(startingCityIsZero);

		if (droppingType ==DROPPING_TYPE.NO_DROPPING) {
			result.setProfitEvaluator(new NoDroppingEvaluator());
		} else if (droppingType ==DROPPING_TYPE.EXPONTENTIAL) {
			result.setProfitEvaluator(new ExponentialProfitEvaluator(droppingRate, droppingConstant));
		}

		SymmetricMap map = null;
		if (cityType == CITY_TYPE.FULL_MATRIX) {
			map = new SymmetricMap(numOfCities);
			for (int i = 0; i < cities.size(); i++) {
				for (int j = 0; j < cities.size(); j++) {
						map.set(i, j, cities.get(i).get(j));
				}
			}
		} else if (cityType == CITY_TYPE.XY_COORDINATES) {
			List<Point2D> points = new ArrayList<>();
			for(List<Double> list : cities) {
				points.add(new Point2D.Double(list.get(0), list.get(1)));
			}
			map = new CoordinateMap(points);
		}
		result.setMap(map);
		

		ItemCollection<Item> items = new ItemCollection<>();
		for (PlainObjectItem item : this.items) {
			items.add(item.city, new Item(item.profit, item.weight));
		}
		result.setItems(items);
		result.setName(name);
		return result;
	}
	

	
}
