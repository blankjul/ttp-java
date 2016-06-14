package com.msu.thief.io.thief.reader;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.moo.util.io.AReader;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;

public class BonyadiMultiObjectiveReader extends AReader<AbstractThiefProblem> {

	static final Logger logger = Logger.getLogger(BonyadiMultiObjectiveReader.class);

	protected MultiObjectiveThiefProblem read_(String pathToFile) throws IOException {

		BufferedReader br = createBufferedReader(pathToFile);

		MultiObjectiveThiefProblem ttp = new MultiObjectiveThiefProblem();

		int numOfCities = BonyadiReaderUtil.parseNumOfCities(br);
		int numOfItems = BonyadiReaderUtil.parseNumOfItems(br);

		ttp.setMaxWeight(BonyadiReaderUtil.parseMaximalWeight(br));
		ttp.setMaxSpeed(BonyadiReaderUtil.parseMaxVelocity(br));
		ttp.setMinSpeed(BonyadiReaderUtil.parseMinVelocity(br));

		double droppingConstant = BonyadiReaderUtil.parseDroppingConstant(br);
		double droppingRate = BonyadiReaderUtil.parseDroppingRate(br);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(droppingRate, droppingConstant));

		ttp.setMap(BonyadiReaderUtil.parseMap(br, numOfCities));
		ttp.setItems(BonyadiReaderUtil.parseItems(br, numOfItems, numOfCities));
		ttp.setName(BonyadiReaderUtil.parseName(pathToFile));

		br.close();
		return ttp;
	}

}
