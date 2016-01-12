package com.msu.thief.ea;

import org.junit.Before;

import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.util.MyRandom;

public abstract class Operator {
	
	protected SingleObjectiveThiefProblem thief;
	protected MyRandom rand;
	

	@Before
	public void setUp() {
		thief = (SingleObjectiveThiefProblem) new JsonThiefProblemReader()
				.read("resources/my_publication_coordinates.ttp");
		rand = new MyRandom();
	}

}
