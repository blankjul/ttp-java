package com.msu.io.reader;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.msu.io.AProblemReader;
import com.msu.io.pojo.PlainObjectItem;
import com.msu.io.pojo.PlainObjectThiefProblem;
import com.msu.io.pojo.PlainObjectThiefProblem.CITY_TYPE;
import com.msu.io.pojo.PlainObjectThiefProblem.DROPPING_TYPE;
import com.msu.io.pojo.PlainObjectThiefProblem.PROBLEM_TYPE;
import com.msu.knp.model.Item;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;

public class JsonThiefReader extends AProblemReader<ThiefProblem> {

	@Override
	protected ThiefProblem read_(BufferedReader br) throws IOException {

		ThiefProblem result = null;

		ObjectMapper mapper = new ObjectMapper();
		PlainObjectThiefProblem problem = mapper.readValue(br, PlainObjectThiefProblem.class);

		if (problem.problemType == PROBLEM_TYPE.MultiObjective)
			result = new ThiefProblem();
		else if (problem.problemType == PROBLEM_TYPE.SingleObjective)
			result = new SingleObjectiveThiefProblem();

		result.setMinSpeed(problem.minSpeed);
		result.setMaxSpeed(problem.maxSpeed);
		result.setMaxWeight(problem.maxWeight);
		result.setStartingCityIsZero(problem.startingCityIsZero);

		if (problem.droppingType ==DROPPING_TYPE.NO_DROPPING) {
			result.setProfitEvaluator(new NoDroppingEvaluator());
		} else if (problem.droppingType ==DROPPING_TYPE.EXPONTENTIAL) {
			result.setProfitEvaluator(new ExponentialProfitEvaluator(problem.droppingRate, problem.droppingConstant));
		}

		SymmetricMap map = null;
		if (problem.cityType == CITY_TYPE.FULL_MATRIX) {
			map = new SymmetricMap(problem.numOfCities);
			for (int i = 0; i < problem.cities.size(); i++) {
				for (int j = 0; j < problem.cities.size(); j++) {
						map.set(i, j, problem.cities.get(i).get(j));
				}
			}
		} else if (problem.cityType == CITY_TYPE.XY_COORDINATES) {
			List<Point2D> points = new ArrayList<>();
			for(List<Double> list : problem.cities) {
				points.add(new Point2D.Double(list.get(0), list.get(1)));
			}
			map = new CoordinateMap(points);
		}
		result.setMap(map);
		

		ItemCollection<Item> items = new ItemCollection<>();
		for (PlainObjectItem item : problem.items) {
			items.add(item.city, new Item(item.profit, item.weight));
		}
		result.setItems(items);
		result.setName(problem.name);
		return result;
	}
	
	

}
