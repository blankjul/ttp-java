package com.moo.ttp.model.tour;

import com.moo.ttp.variable.TravellingThiefProblem;

public interface ITourFactory {
	
	public Tour<?> create(TravellingThiefProblem p);

}
