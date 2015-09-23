package com.msu.scenarios.thief.bonyadi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.thief.SingleObjectiveThiefProblem;

public class BenchmarkSingleObjective extends ABenchmarkReader {

	static final Logger logger = Logger.getLogger(BenchmarkSingleObjective.class);

	
	
	public SingleObjectiveThiefProblem create(String pathToFile) {

		SingleObjectiveThiefProblem ttp = new SingleObjectiveThiefProblem();

		logger.info(String.format("Starting to parse file %s", pathToFile));

		try {

			FileReader fileReader = new FileReader(pathToFile);
			BufferedReader br = new BufferedReader(fileReader);

			int numOfCities = parseNumOfCities(br);
			int numOfItems =  parseNumOfItems(br);
			
			ttp.setMaxWeight(parseMaximalWeight(br));
			ttp.setMaxSpeed(parseMaxVelocity(br));
			ttp.setMinSpeed(parseMinVelocity(br));
			ttp.setR(parseSingleObjectiveWeight(br));
			
			ttp.setMap(parseMap(br, numOfCities));
			ttp.setItems(parseItems(br, numOfItems, numOfCities));

			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + pathToFile + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + pathToFile + "'");
		}

		return ttp;
	}
}
