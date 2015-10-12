package com.msu.evolving.measures;

import java.util.List;

import com.msu.analyze.TourAverageDistanceToOpt;
import com.msu.evolving.tsp.SalesmanProblemVariable;
import com.msu.moo.model.AProblem;
import com.msu.problems.SalesmanProblem;
import com.msu.thief.model.CoordinateMap;

public class OneDominatingTour extends AProblem<SalesmanProblemVariable>{

	
	@Override
	public int getNumberOfObjectives() {
		return 1;
	}


	@Override
	protected void evaluate_(SalesmanProblemVariable var, List<Double> objectives, List<Double> constraintViolations) {
		SalesmanProblem tsp = new SalesmanProblem(new CoordinateMap(var.get()));
		objectives.add(new TourAverageDistanceToOpt().analyze(tsp));
	}


	

}
