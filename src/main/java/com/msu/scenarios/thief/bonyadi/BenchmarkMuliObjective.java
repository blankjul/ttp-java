package com.msu.scenarios.thief.bonyadi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;

public class BenchmarkMuliObjective extends ABenchmarkReader{

	static final Logger logger = Logger.getLogger(BenchmarkMuliObjective.class);

	public ThiefProblem create(String pathToFile) {

		ThiefProblem ttp = new ThiefProblem();

		logger.info(String.format("Starting to parse file %s", pathToFile));

		try {

			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(pathToFile);

			// Always wrap FileReader in BufferedReader.
			BufferedReader br = new BufferedReader(fileReader);

			int numOfCities = parseNumOfCities(br);
			int numOfItems =  parseNumOfItems(br);
			
			ttp.setMaxWeight(parseMaximalWeight(br));
			ttp.setMaxSpeed(parseMaxVelocity(br));
			ttp.setMinSpeed(parseMinVelocity(br));
			
			double droppingConstant = parseDroppingConstant(br);
			double droppingRate = parseDroppingRate(br);
			ttp.setProfitEvaluator(new ExponentialProfitEvaluator(droppingRate, droppingConstant));
			
			ttp.setMap(parseMap(br, numOfCities));
			ttp.setItems(parseItems(br, numOfItems, numOfCities));

			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + pathToFile + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + pathToFile + "'");
			// ex.printStackTrace();
		}

		return ttp;
	}

}
