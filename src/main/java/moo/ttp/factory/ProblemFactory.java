package moo.ttp.factory;

import moo.ttp.problems.TravellingThiefProblem;

public class ProblemFactory {

	public static TravellingThiefProblem create(String problem) {

		if (problem.equals("TTP"))
			//return App.example();
			return ThiefFactory.create(100, 1);
		else
			throw new RuntimeException("Problem not found!");
	}

}
