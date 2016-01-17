package com.msu.thief.io.thief.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.moo.util.io.AReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class ThiefSingleObjectiveReader extends AReader<SingleObjectiveThiefProblem> {

	static final Logger logger = Logger.getLogger(ThiefSingleObjectiveReader.class);

	protected SingleObjectiveThiefProblem read_(String pathToFile) throws IOException {

		BufferedReader br = createBufferedReader(pathToFile);

		SingleObjectiveThiefProblem p = new SingleObjectiveThiefProblem();

		int numOfCities = ThiefReaderUtil.parseNumOfCities(br.readLine());
		int numOfItems = ThiefReaderUtil.parseNumOfItems(br.readLine());

		p.setMaxWeight(ThiefReaderUtil.parseMaximalWeight(br.readLine()));
		p.setMaxSpeed(ThiefReaderUtil.parseMaxVelocity(br.readLine()));
		p.setMinSpeed(ThiefReaderUtil.parseMinVelocity(br.readLine()));
		p.setR(ThiefReaderUtil.parseSingleObjectiveWeight(br.readLine()));

		p.setMap(ThiefReaderUtil.parseMap(br, numOfCities));
		p.setItems(ThiefReaderUtil.parseItems(br, numOfItems, numOfCities));
		p.setName(new File(pathToFile).getName().split("\\.")[0]);

		return p;
	}

}
