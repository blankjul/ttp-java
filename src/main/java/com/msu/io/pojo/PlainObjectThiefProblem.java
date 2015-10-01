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
		
		SymmetricMap map = problem.getMap();
		if (map instanceof CoordinateMap) {
			cityType = CITY_TYPE.XY_COORDINATES;
			List<Point2D> cityPoints = ((CoordinateMap) map).getCities();
			for (Point2D point : cityPoints) {
				this.cities.add(Arrays.asList(point.getX(), point.getY()));
			}
			
		} else {
			cityType = CITY_TYPE.FULL_MATRIX;
			for (int i = 0; i < map.getSize(); i++) {
				List<Double> row = new ArrayList<>();
				for (int j = 0; j < map.getSize(); j++) {
					row.add(map.get(i, j));
				}
				cities.add(row);
			}
		}
		
		for (int i = 0; i < numOfCities; i++) {
			for (Item item : problem.getItemCollection().getItemsFromCity(i)) {
				this.items.add(new PlainObjectItem(i, item.getWeight(), item.getProfit()));
			}
		}
		
	}
	
	
	
	
	

	
}
