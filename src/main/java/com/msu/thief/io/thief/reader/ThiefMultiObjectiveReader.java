package com.msu.thief.io.thief.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.util.io.AReader;

public class ThiefMultiObjectiveReader extends AReader<SingleObjectiveThiefProblem> {

	static final Logger logger = Logger.getLogger(ThiefMultiObjectiveReader.class);

	@Override
	public SingleObjectiveThiefProblem read_(BufferedReader br) throws IOException {

		SingleObjectiveThiefProblem p = new SingleObjectiveThiefProblem();

		int numOfCities = ThiefReaderUtil.parseNumOfCities(br.readLine());
		int numOfItems = ThiefReaderUtil.parseNumOfItems(br.readLine());

		p.setMaxWeight(ThiefReaderUtil.parseMaximalWeight(br.readLine()));
		p.setMaxSpeed(ThiefReaderUtil.parseMaxVelocity(br.readLine()));
		p.setMinSpeed(ThiefReaderUtil.parseMinVelocity(br.readLine()));
		
		double droppingConstant = ThiefReaderUtil.parseDroppingConstant(br.readLine());
		double droppingRate =ThiefReaderUtil. parseDroppingRate(br.readLine());
		p.setProfitEvaluator(new ExponentialProfitEvaluator(droppingRate, droppingConstant));
		
		p.setMap(ThiefReaderUtil.parseMap(br, numOfCities));
		p.setItems(ThiefReaderUtil.parseItems(br, numOfItems, numOfCities));
		
		return p;
	}
	
	
	@Override
	public SingleObjectiveThiefProblem read(String pathToFile) {
		SingleObjectiveThiefProblem problem = super.read(pathToFile);
		problem.setName(new File(pathToFile).getName().split("\\.")[0]);
		return problem;
	}

}
