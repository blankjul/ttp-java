package com.msu.thief.model.tour;

import com.msu.thief.problems.TravellingThiefProblem;

public interface ITourFactory {
	
	public Tour<?> create(TravellingThiefProblem p);

}
