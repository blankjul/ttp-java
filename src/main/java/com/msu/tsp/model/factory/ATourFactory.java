package com.msu.tsp.model.factory;

import com.msu.moo.model.AVariableFactory;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.model.Tour;

public abstract class ATourFactory<P extends ICityProblem> extends AVariableFactory<Tour<?>, P> {
}
