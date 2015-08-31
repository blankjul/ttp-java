package com.msu.thief.experiment;

import com.msu.moo.model.interfaces.IProblem;

public abstract class AbstractExperiment<P extends IProblem> extends com.msu.moo.model.AbstractExperiment<P> {


	@Override
	public String getPathToEAF() {
		return  "../moo-java/vendor/aft-0.95/eaf";
	}

	@Override
	public String getPathToHV() {
		return "../moo-java/vendor/hv-1.3-src/hv";
	}

	
	
	
}
