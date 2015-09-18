package com.msu.thief.model.tour.factory;

import com.msu.moo.model.AVariableFactory;
import com.msu.thief.model.tour.Tour;
import com.msu.tsp.ICityProblem;

public abstract class ATourFactory<P extends ICityProblem> extends AVariableFactory<Tour<?>, P> {
}
