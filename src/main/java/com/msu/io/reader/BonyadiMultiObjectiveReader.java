package com.msu.io.reader;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.problems.ThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;

public class BonyadiMultiObjectiveReader extends ABonyadiReader<ThiefProblem> {

	static final Logger logger = Logger.getLogger(BonyadiMultiObjectiveReader.class);

	@Override
	protected ThiefProblem read_(BufferedReader br) throws IOException {

		ThiefProblem ttp = new ThiefProblem();

		int numOfCities = parseNumOfCities(br);
		int numOfItems = parseNumOfItems(br);

		ttp.setMaxWeight(parseMaximalWeight(br));
		ttp.setMaxSpeed(parseMaxVelocity(br));
		ttp.setMinSpeed(parseMinVelocity(br));

		double droppingConstant = parseDroppingConstant(br);
		double droppingRate = parseDroppingRate(br);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(droppingRate, droppingConstant));

		ttp.setMap(parseMap(br, numOfCities));
		ttp.setItems(parseItems(br, numOfItems, numOfCities));

		br.close();
		ttp.setStartingCityIsZero(true);
		return ttp;
	}

}
