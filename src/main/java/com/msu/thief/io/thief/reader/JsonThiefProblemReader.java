package com.msu.thief.io.thief.reader;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msu.moo.util.io.AReader;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.profit.IndividualProfitEvaluator;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.ProfitConstraintThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class JsonThiefProblemReader extends AReader<AbstractThiefProblem> {

	protected AbstractThiefProblem read_(String pathToFile) throws IOException {

		BufferedReader br = createBufferedReader(pathToFile);

		AbstractThiefProblem p = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(br);

		String type = root.findValue("problemType").asText();
		if (type.equals("SingleObjective")) {
			p = new SingleObjectiveThiefProblem();
			((SingleObjectiveThiefProblem) p).setR(root.findValue("R").asDouble());
		} else if (type.equals("MultiObjective")) {
			p = new MultiObjectiveThiefProblem();
		} else if (type.equals("ProfitConstraint")) {
			p = new ProfitConstraintThiefProblem();
			((ProfitConstraintThiefProblem) p).setMinProfitConstraint(root.findValue("minProfitConstraint").asDouble());
		}

		// safe the name according to the filename
		p.setName(root.findValue("name").asText());
		
		p.setMinSpeed(root.findValue("minSpeed").asDouble());
		p.setMaxSpeed(root.findValue("maxSpeed").asDouble());
		p.setMaxWeight(root.findValue("maxWeight").asInt());

		JsonNode profitNode = root.findValue("profitEvaluator");
		String profitEval = profitNode.findValue("type").asText();
		if (profitEval.equals("NO_DROPPING")) {
			p.setProfitEvaluator(new NoDroppingEvaluator());
		} else if (profitEval.equals("EXPONTENTIAL")) {
			double droppingRate = profitNode.findValue("droppingRate").asDouble();
			double droppingConstant = profitNode.findValue("droppingConstant").asDouble();
			p.setProfitEvaluator(new ExponentialProfitEvaluator(droppingRate, droppingConstant));
		} else if (profitEval.equals("INDIVIDUAL")) {
			p.setProfitEvaluator(new IndividualProfitEvaluator());
		} else {
			throw new RuntimeException(String.format("ProfitEvaluator %s is unknown.", profitEval));
		}

		JsonNode timeNode = root.findValue("timeEvaluator");
		String timeEval = timeNode.findValue("type").asText();
		if (timeEval.equals("STANDARD")) {
			p.setTimeEvaluator(new StandardTimeEvaluator());
		} else {
			throw new RuntimeException(String.format("TimeEvaluator %s is unknown.", timeEval));
		}

		SymmetricMap map = null;
		String cityType = root.findValue("cityType").asText();
		JsonNode citiesNode = root.findValue("cities");
		if (cityType.equals("XY_COORDINATES")) {
			List<Point2D> cities = new ArrayList<>();
			for (JsonNode n : citiesNode) {
				Point2D point = new Point2D.Double(n.get(0).asDouble(), n.get(1).asDouble());
				cities.add(point);
			}
			map = new CoordinateMap(cities);
		} else if (cityType.equals("FULL_MATRIX")) {
			final int numOfCities = citiesNode.size();
			map = new SymmetricMap(numOfCities);
			for (int i = 0; i < numOfCities; i++) {
				for (int j = 0; j < numOfCities; j++) {
					map.set(i, j, citiesNode.get(i).get(j).asDouble());
				}
			}
		}
		p.setMap(map);

		ItemCollection<Item> col = new ItemCollection<>();
		JsonNode itemsNode = root.findValue("items");
		for (JsonNode item : itemsNode) {
			int city = item.findValue("city").asInt();
			double profit = item.findValue("value").asDouble();
			double weight = item.findValue("weight").asDouble();
			col.add(city, new Item(profit, weight));
		}
		p.setItems(col);

		return p;

	}

}
