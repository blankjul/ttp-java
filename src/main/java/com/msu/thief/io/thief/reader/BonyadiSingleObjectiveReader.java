package com.msu.thief.io.thief.reader;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.moo.util.io.AReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class BonyadiSingleObjectiveReader extends AReader<SingleObjectiveThiefProblem> {

	static final Logger logger = Logger.getLogger(BonyadiSingleObjectiveReader.class);

	protected SingleObjectiveThiefProblem read_(String pathToFile) throws IOException {

		BufferedReader br = createBufferedReader(pathToFile);

		SingleObjectiveThiefProblem ttp = new SingleObjectiveThiefProblem();

		int numOfCities = BonyadiReaderUtil.parseNumOfCities(br);
		int numOfItems = BonyadiReaderUtil.parseNumOfItems(br);

		ttp.setMaxWeight(BonyadiReaderUtil.parseMaximalWeight(br));
		ttp.setMaxSpeed(BonyadiReaderUtil.parseMaxVelocity(br));
		ttp.setMinSpeed(BonyadiReaderUtil.parseMinVelocity(br));
		ttp.setR(BonyadiReaderUtil.parseSingleObjectiveWeight(br));

		ttp.setMap(BonyadiReaderUtil.parseMap(br, numOfCities));
		ttp.setItems(BonyadiReaderUtil.parseItems(br, numOfItems, numOfCities));

		ttp.setName(BonyadiReaderUtil.parseName(pathToFile));

		br.close();

		return ttp;
	}

}
