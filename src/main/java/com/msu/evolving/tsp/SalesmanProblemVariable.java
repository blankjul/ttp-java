package com.msu.evolving.tsp;

import java.awt.geom.Point2D;
import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.model.Variable;
import com.msu.util.Util;

public class SalesmanProblemVariable extends Variable<List<Point2D>> {


	public SalesmanProblemVariable(List<Point2D> obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new SalesmanProblemVariable((List<Point2D>) Util.cloneObject(obj));
	}



}
