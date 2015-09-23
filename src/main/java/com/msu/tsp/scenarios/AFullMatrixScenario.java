package com.msu.tsp.scenarios;

import com.msu.thief.model.SymmetricMap;
import com.msu.thief.scenarios.AScenario;
import com.msu.tsp.model.Tour;

public abstract class AFullMatrixScenario  extends AScenario<SymmetricMap, Tour<?>>{

	//! return the full matrix 
	public abstract double[][] getCosts();
	
	
	@Override
	public SymmetricMap getObject() {
		return getMap(getCosts());
	}


	public static SymmetricMap getMap(double[][] costs) {
		final double[][] fullMatrix = costs;
		final int length = fullMatrix.length;
		SymmetricMap map = new SymmetricMap(length);
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				map.set(i, j, fullMatrix[i][j]);
			}
		}
		return map;
	}
	
	
	
	

}
