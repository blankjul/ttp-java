package com.msu.scenarios.thief.bonyadi;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.thief.SingleObjectiveThiefProblem;

public class BenchmarkSingleObjective extends ABenchmarkReader<SingleObjectiveThiefProblem> {

	static final Logger logger = Logger.getLogger(BenchmarkSingleObjective.class);

	protected SingleObjectiveThiefProblem read_(BufferedReader br) throws IOException {

		SingleObjectiveThiefProblem ttp = new SingleObjectiveThiefProblem();

		int numOfCities = parseNumOfCities(br);
		int numOfItems = parseNumOfItems(br);

		ttp.setMaxWeight(parseMaximalWeight(br));
		ttp.setMaxSpeed(parseMaxVelocity(br));
		ttp.setMinSpeed(parseMinVelocity(br));
		ttp.setR(parseSingleObjectiveWeight(br));

		ttp.setMap(parseMap(br, numOfCities));
		ttp.setItems(parseItems(br, numOfItems, numOfCities));

		br.close();

		return ttp;
	}

}
